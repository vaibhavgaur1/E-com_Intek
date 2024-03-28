package com.e_commerce.services.impl;

import com.e_commerce.Dto.FileUpload;
import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.FileUploadRepository;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.CartService;
import com.e_commerce.services.FetchImage;
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
    private final FetchImage fetchImage;
    private final FileUploadRepository fileUploadRepository;
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
                    .productQuantity(1l)
                    .build();
            Cart savedCart = cartDao.save(cart);
            double totalAmount = calculateTotalAmount(dbListCartOfUser);

            FileUpload dbFileUploadForProduct = fileUploadRepository.findById(dbProduct.getUploadId())
                    .orElseThrow(()->new Exception("no image url found"));

            byte[] file = fetchImage.getFile(dbFileUploadForProduct.getPathURL());
            dbProduct.setImage(file);
            return ResponseUtils.createSuccessResponse(savedCart, new TypeReference<Cart>() {});
//            return null;
        }
        return null;

    }

    private double calculateTotalAmount(List<Cart> dbListCartOfUser){

        double totalAmount = 0.0;
        for (Cart cartItem : dbListCartOfUser) {
            Product product = cartItem.getProduct();
            // Assuming each product has a price attribute
            double productPrice = product.getProductDiscountedPrice();
            long productQuantity = cartItem.getProductQuantity();
            totalAmount += productPrice * productQuantity;
        }
        return totalAmount;
    }

    public ApiResponse<List<Cart>> getCartDetailsOfUser(String authHeader) throws Exception {
        User dbUser = helperUtils.getUserFromAuthToken(authHeader);
        List<Cart> dbCartList = cartDao.findByUser(dbUser);
        dbCartList.forEach(dbCart-> {
            FileUpload dbFileUploadForProduct = null;
            try {
                dbFileUploadForProduct = fileUploadRepository.findById(dbCart.getProduct().getUploadId())
                    .orElseThrow(()->new Exception("no image url found"));
                byte[] file = fetchImage.getFile(dbFileUploadForProduct.getPathURL());
                dbCart.getProduct().setImage(file);
            } catch (Exception e) {
                throw new RuntimeException("no image url found");
            }
        });
        return ResponseUtils.createSuccessResponse(dbCartList, new TypeReference<List<Cart>>() {});
    }

    public void deleteCartItem(Integer cartId) {
        cartDao.deleteById(cartId);
    }
}
