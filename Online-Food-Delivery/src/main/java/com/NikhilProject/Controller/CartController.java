package com.NikhilProject.Controller;

import com.NikhilProject.Model.Cart;
import com.NikhilProject.Model.CartItem;
import com.NikhilProject.Model.User;
import com.NikhilProject.Repository.CartItemRepository;
import com.NikhilProject.Response.CartItemResponse;
import com.NikhilProject.Service.CartService;
import com.NikhilProject.Service.UserService;
import com.NikhilProject.request.AddCartItemRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api")
public class CartController{
    private static final Logger log = LoggerFactory.getLogger(CartController.class);
    @Autowired
    CartService cartService;
    @Autowired
    UserService userService;
    @PostMapping("/cart/add")
    public ResponseEntity<CartItem> addItemToCart(
            @RequestBody AddCartItemRequest req,@RequestHeader("Authorization") String jwt
            ) throws Exception {
        log.info("Entered the addItemToCart method {} Nikhil {} ",req,jwt);
        CartItem cartItem=cartService.addItemToCart(req,jwt);
        return new ResponseEntity<>(cartItem, HttpStatus.OK);
    }
    @PutMapping("/cart-item/update")
    public ResponseEntity<CartItem> updateCartItemQuantity(
            @RequestBody CartItemResponse response, @RequestHeader("Authorization") String jwt
    ) throws Exception {
        CartItem cartItem=cartService.updateCartItemQuantity(response.getCartItemId(),response.getQuantity());
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }
    @PutMapping("/cart-item/{id}/remove")
    public ResponseEntity<Cart> removeCartItem(
            @RequestBody CartItemResponse response, @RequestHeader("Authorization") String jwt
    ) throws Exception {
        Cart cartItem=cartService.removeItemFromCart(response.getCartItemId(),jwt);
        return new ResponseEntity<>(cartItem,HttpStatus.OK);
    }
    @PutMapping("/cart/clear")
    public ResponseEntity<Cart> clearCart(
            @RequestHeader("Authorization") String jwt
    ){
        User user=userService.findUserByJsonToken(jwt);
        Cart cart=cartService.clearCart(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }
    @GetMapping("/cart")
    public ResponseEntity<Cart> findUserCart(
            @RequestHeader("Authorization") String jwt
    ) throws Exception {
        log.info("Entered the findUserCart");
        User user=userService.findUserByJsonToken(jwt);
        Cart cart=cartService.findCartById(user.getId());
        return new ResponseEntity<>(cart,HttpStatus.OK);
    }

}
