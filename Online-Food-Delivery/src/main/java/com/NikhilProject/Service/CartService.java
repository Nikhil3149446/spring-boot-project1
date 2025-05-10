package com.NikhilProject.Service;

import com.NikhilProject.Model.Cart;
import com.NikhilProject.Model.CartItem;
import com.NikhilProject.request.AddCartItemRequest;
import org.springframework.stereotype.Service;

public interface CartService {
    public CartItem addItemToCart(AddCartItemRequest req, String jwt) throws Exception;
    public CartItem updateCartItemQuantity(Long cartItemId,int quantity ) throws Exception;
    public Cart removeItemFromCart(Long cartItemId,String jwt) throws Exception;
    public Long calculateCartTotals(Cart cart) throws Exception;
    public Cart findCartById(Long cartId) throws Exception;
    public Cart findCartByUserId(Long userId) throws Exception;
    public Cart clearCart(Long id);
}
