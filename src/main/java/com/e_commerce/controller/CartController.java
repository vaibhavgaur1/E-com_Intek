package com.e_commerce.controller;

import com.e_commerce.entity.Cart;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.CartService;
import com.e_commerce.services.impl.CartServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
@CrossOrigin
public class CartController {

    private final CartService cartService;

    @GetMapping("/addToCart/{productId}")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<Cart> addToCart(@PathVariable Integer productId, @RequestHeader("Authorization") String authHeader) throws Exception {
        return cartService.addToCart(productId, authHeader);
    }

    @DeleteMapping("/deleteCartItem/{cartId}")
    @PreAuthorize("hasAuthority('USER')")
    public void deleteCartItem(@PathVariable Integer cartId) throws Exception {
        cartService.deleteCartItem(cartId);
    }


    @GetMapping("/getCartDetailsOfUser")
    @PreAuthorize("hasAuthority('USER')")
    public ApiResponse<List<Cart>> getCartDetailsOfUser(@RequestHeader("Authorization") String authHeader) throws Exception {
        return cartService.getCartDetailsOfUser(authHeader);
    }
}
