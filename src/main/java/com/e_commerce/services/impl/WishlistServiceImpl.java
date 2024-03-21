
package com.e_commerce.services.impl;

import com.e_commerce.Dto.FileUpload;
import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.FileUploadRepository;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.dao.WishlistDao;
//import com.e_commerce.dto.FileUpload;
import com.e_commerce.Dto.FileUpload;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.entity.Wishlist;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.FetchImage;
import com.e_commerce.services.WishlistService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl implements WishlistService {

    private final WishlistDao wishlistDao;
    private final ProductDao productDao;
    private final HelperUtils helperUtils;
    private final FileUploadRepository fileUploRepository;
    public ApiResponse<Wishlist> addToWishlist(Integer productId, String authHeader) throws Exception {

        System.out.println("productId: "+productId);
        System.out.println(authHeader);
        Product dbProduct = productDao.findById(productId).orElseThrow(()-> new Exception("no product found"));


        User dbUser = helperUtils.getUserFromAuthToken(authHeader);
        System.out.println(dbUser);
//        List<Cart> dbListCartOfUser = cartDao.findByUser(dbUser);

        List<Wishlist> dbwishListOfUser = wishlistDao.findByUser(dbUser);
        List<Wishlist> listSameProductInUser = dbwishListOfUser.stream()
                .filter(x -> x.getProduct().getProductId() == productId)
                .toList();
//        System.out.println("listSameProductInUser.get(0).getProduct().getProductId(): "+listSameProductInUser.get(0).getProduct().getProductId());
        if(!listSameProductInUser.isEmpty()){
            throw new Exception(" product is already present in the cart");
        }

        if(dbProduct!= null && dbUser!= null){
            Wishlist wishlist = Wishlist.builder()
                    .user(dbUser)
                    .product(dbProduct)
                    .build();
            Wishlist savedWishlist = wishlistDao.save(wishlist);

            FileUpload dbFileUploadForProduct = fileUploRepository.findById(dbProduct.getUploadId())
                    .orElseThrow(()->new Exception("no image url found"));

            byte[] file = FetchImage.getFile(dbFileUploadForProduct.getPathURL());
            dbProduct.setImage(file);
            return ResponseUtils.createSuccessResponse(savedWishlist, new TypeReference<Wishlist>() {});
        }
        return null;
    }

    public ApiResponse<List<Wishlist>> getWishListOfUser(String authHeader) throws Exception {
        User dbUser = helperUtils.getUserFromAuthToken(authHeader);
        List<Wishlist> dbWishList = wishlistDao.findByUser(dbUser);
        dbWishList.forEach(dbWish-> {
            FileUpload dbFileUploadForProduct = null;
            try {
                dbFileUploadForProduct = fileUploRepository.findById(dbWish.getProduct().getUploadId())
                        .orElseThrow(()->new Exception("no image url found"));
                byte[] file = FetchImage.getFile(dbFileUploadForProduct.getPathURL());
                dbWish.getProduct().setImage(file);
            } catch (Exception e) {
                throw new RuntimeException("no image url found");
            }
        });
        return ResponseUtils.createSuccessResponse(dbWishList, new TypeReference<List<Wishlist>>() {});
    }

    public ApiResponse<List<Wishlist>> getWishlistDetailsOfUser(String authHeader) throws Exception {
        User dbUser = helperUtils.getUserFromAuthToken(authHeader);
        List<Wishlist> dbWishList = wishlistDao.findByUser(dbUser);
        dbWishList.forEach(dbWish-> {
            FileUpload dbFileUploadForProduct = null;
            try {
                dbFileUploadForProduct = fileUploRepository.findById(dbWish.getProduct().getUploadId())
                        .orElseThrow(()->new Exception("no image url found"));
                byte[] file = FetchImage.getFile(dbFileUploadForProduct.getPathURL());
                dbWish.getProduct().setImage(file);
            } catch (Exception e) {
                throw new RuntimeException("no image url found");
            }
        });
        return ResponseUtils.createSuccessResponse(dbWishList, new TypeReference<List<Wishlist>>() {});
    }

//    public void deleteCartItem(Integer wishListId) {
//        wishlistDao.deleteById(wishListId);
//    }
}

