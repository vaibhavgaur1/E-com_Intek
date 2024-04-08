

package com.e_commerce.services;

import com.e_commerce.entity.Wishlist;
import com.e_commerce.response.ApiResponse;

import java.util.List;

public interface WishlistService {

    ApiResponse<Wishlist> addToWishlist(Integer productId, String authHeader) throws Exception;

    ApiResponse<List<Wishlist>> getWishlistDetailsOfUser(String authHeader) throws Exception;
    void deleteCartItem(Integer WishlistId);
}


