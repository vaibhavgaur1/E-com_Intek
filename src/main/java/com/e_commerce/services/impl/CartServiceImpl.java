package com.e_commerce.services.impl;

import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.CartService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CartServiceImpl implements CartService {

    private final CartDao cartDao;
    private final ProductDao productDao;
    private final HelperUtils helperUtils;
    public ApiResponse<Cart> addToCart(Integer productId, String authHeader) throws Exception {
        Product dbProduct = productDao.findById(productId).orElseThrow(()-> new Exception("no product found"));
        User dbUser = helperUtils.getUserFromAuthToken(authHeader);

        List<Cart> dbListCartOfUser = cartDao.findByUser(dbUser);
        List<Cart> listSameProductInUser = dbListCartOfUser.stream()
                .filter(x -> x.getProduct().getProductId() == productId)
                .toList();
        if(!listSameProductInUser.isEmpty()){
            throw new Exception(" product is already present in the cart");
        }

        if(dbProduct!= null && dbUser!= null){
            Cart cart = Cart.builder()
                    .user(dbUser)
                    .product(dbProduct)
                    .build();
            return ResponseUtils.createSuccessResponse(cartDao.save(cart), new TypeReference<Cart>() {});
        }
        return null;
    }

    public ApiResponse<List<Cart>> getCartDetailsOfUser(String authHeader) throws Exception {
        User dbUser = helperUtils.getUserFromAuthToken(authHeader);

        return ResponseUtils.createSuccessResponse(cartDao.findByUser(dbUser), new TypeReference<List<Cart>>() {});
    }

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }
}
