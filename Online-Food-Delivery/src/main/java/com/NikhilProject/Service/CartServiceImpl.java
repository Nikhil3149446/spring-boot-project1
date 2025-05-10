package com.NikhilProject.Service;

import com.NikhilProject.Model.Cart;
import com.NikhilProject.Model.CartItem;
import com.NikhilProject.Model.Food;
import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.CartItemRepository;
import com.NikhilProject.Repository.CartRepository;
import com.NikhilProject.Repository.UserRepository;
import com.NikhilProject.request.AddCartItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class CartServiceImpl implements CartService{
    private static final Logger log = LoggerFactory.getLogger(CartServiceImpl.class);
    @Autowired
    CartRepository cartRepository;
    @Autowired
    UserService userService;
    @Autowired
    FoodService foodService;
    @Autowired
    CartItemRepository cartItemRepository;
    @Autowired
    UserRepository userRepository;
    @Override
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception {
        User user=userService.findUserByJsonToken(jwt);
        Cart cart=cartRepository.findUserById(user.getId());
        Food food=foodService.findFoodById((Long) req.getFoodId());
        CartItem cartItem=new CartItem();
        cartItem.setCart(cart);
        cartItem.setFood(food);
        cartItem.setQuantity(req.getQuantity());
        cartItem.setIngredients(req.getIngredients());
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public CartItem updateCartItemQuantity(Long cartItemId, int quantity) throws Exception {
        Optional<CartItem> fetchedCartItem=cartItemRepository.findById(cartItemId);
        if(fetchedCartItem.isEmpty())
            throw new Exception("Couldn't find the CartItem");
        CartItem cartItem=fetchedCartItem.get();
        cartItem.setQuantity(quantity);
        cartItemRepository.save(cartItem);
        return cartItem;
    }

    @Override
    public Cart removeItemFromCart(Long cartItemId, String jwt) throws Exception {
//        Optional<CartItem> cartItem=cartItemRepository.findById(cartItemId);
//        if(cartItem.isEmpty())
//            throw new Exception("Couldn't find the Cart Item");
        User user=userService.findUserByJsonToken(jwt);
        Cart cart=cartRepository.findUserById(user.getId());
        List<CartItem> cartItem=cart.getItem().stream().filter(c->c.getId()!=cartItemId).toList();
        Cart updatedCart=new Cart();
        updatedCart.setItem(cartItem);
        cartRepository.deleteById(cart.getId());
        cartRepository.save(updatedCart);
        return updatedCart;
    }

    @Override
    public Long calculateCartTotals(Cart cart) throws Exception {
        Long quantity=0L;
        List<CartItem> cartItems=cart.getItem();
        for(CartItem item:cartItems)
            quantity+=item.getQuantity();
        return quantity;
    }

    @Override
    public Cart findCartById(Long cartId) throws Exception {
        Cart cart=cartRepository.findUserById(cartId);
        log.info("The value of the cart is {}",cart);
        if(cart==null)
            throw new Exception("Cannot find the Cart");

        return cart;
    }

    @Override
    public Cart findCartByUserId(Long userId) throws Exception {
        Optional<User> user=userRepository.findById(userId);
        if(user.isEmpty())
            throw new Exception("Cannot find the User");
        Cart cart=cartRepository.findUserById(user.get().getId());
        return cart;
    }

    @Override
    public Cart clearCart(Long id) {
        cartRepository.deleteAll();
        return new Cart();
    }
}
