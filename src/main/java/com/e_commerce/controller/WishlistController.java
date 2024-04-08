//package com.e_commerce.controller;
//
//public class WishlistController {
//}

package com.e_commerce.controller;

import com.e_commerce.entity.Wishlist;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.WishlistService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/wishlist")
@RequiredArgsConstructor
@CrossOrigin
public class WishlistController {

    private final WishlistService wishlistService;

    @GetMapping("/addToWishlist/{productId}")
//    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Wishlist> addToWishlist(@PathVariable Integer productId, @RequestHeader("Authorization") String authHeader) throws Exception {
        return wishlistService.addToWishlist(productId, authHeader);
    }

    @DeleteMapping("/deleteWishlistItem/{wishListId}")
//    @PreAuthorize("hasAuthority('USER')")
    public void deleteWishlistItem(@PathVariable Integer wishListId) throws Exception {
        wishlistService.deleteCartItem(wishListId);
    }


    @GetMapping("/WishlistDetailsOfUser")
//    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<List<Wishlist>> getWishlistDetailsOfUser(@RequestHeader("Authorization") String authHeader) throws Exception {
        return wishlistService.getWishlistDetailsOfUser(authHeader);
    }
}

