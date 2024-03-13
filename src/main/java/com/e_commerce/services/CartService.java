package com.e_commerce.services;

import com.e_commerce.entity.Cart;
import com.e_commerce.response.ApiResponse;

import java.util.List;

public interface CartService {

    ApiResponse<Cart> addToCart(Integer productId, String authHeader) throws Exception;

    ApiResponse<List<Cart>> getCartDetailsOfUser(String authHeader) throws Exception;
    void deleteCartItem(Integer cartId);
}
