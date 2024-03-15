package com.e_commerce.services.impl;

import com.e_commerce.Dto.FileUpload;
import com.e_commerce.Dto.ProductDto;
import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.CategoryRepository;
import com.e_commerce.dao.FileUploadRepository;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Category;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import com.e_commerce.request.AddProductRequest;
import com.e_commerce.response.AddProductResponse;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.FetchImage;
import com.e_commerce.services.ProductService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Service@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductDao productDao;
    private final HelperUtils helperUtils;
    private final CartDao cartDao;
    private final CategoryRepository categoryRepository;
    private final FileUploadRepository fileUploadRepository;
    @Autowired
    private ProductDao product;
    public ApiResponse<Product> saveProduct(Product product) {

        return ResponseUtils.createSuccessResponse(productDao.save(product), new TypeReference<Product>() {});
    }

    public ApiResponse<List<Product>> getAllProducts(Integer pageNumber, Integer pageSize, String searchKey) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);

        if(searchKey== null || searchKey.isEmpty() || searchKey.isBlank()){
            List<Product> dbProducts =(List<Product>) productDao.findAll(pageable);
            System.out.println(dbProducts.size());
            List<Product> withImage= new ArrayList<>();
            dbProducts.forEach(dbProduct->{
                FileUpload dbFileUploadForProduct = null;
                try {
                    dbFileUploadForProduct = fileUploadRepository.findById(dbProduct.getUploadId())
                            .orElseThrow(()->new Exception("no image url found"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                byte[] file = FetchImage.getFile(dbFileUploadForProduct.getPathURL());
                dbProduct.setImage(file);

//                withImage.add(dbProduct);

            });

            return ResponseUtils.createSuccessResponse(dbProducts, new TypeReference<List<Product>>() {});
        }else{
            List<Product> dbProducts = productDao
                    .findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                            searchKey, searchKey, pageable
                    );
            System.out.println(dbProducts.size());
            List<Product> withImage= new ArrayList<>();
            dbProducts.forEach(dbProduct->{
                FileUpload dbFileUploadForProduct = null;
                try {
                    dbFileUploadForProduct = fileUploadRepository.findById(dbProduct.getUploadId())
                            .orElseThrow(()->new Exception("no image url found"));
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }

                byte[] file = FetchImage.getFile(dbFileUploadForProduct.getPathURL());
                dbProduct.setImage(file);

//                withImage.add(dbProduct);

            });



            return ResponseUtils.createSuccessResponse(dbProducts, new TypeReference<List<Product>>() {});
        }
    }

    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    @SneakyThrows
    public ApiResponse<Product> getProductById(Integer productId) {
        Product dbProduct = productDao.findById(productId)
                .orElseThrow(() -> new Exception("Product Not Found"));
        FileUpload dbFileUploadForProduct = fileUploadRepository.findById(dbProduct.getUploadId())
                .orElseThrow(()->new Exception("no image url found"));

        byte[] file = FetchImage.getFile(dbFileUploadForProduct.getPathURL());
        dbProduct.setImage(file);

        return ResponseUtils.createSuccessResponse(dbProduct, new TypeReference<Product>() {});
    }

    public ApiResponse<List<Product>> getProductDetails(Boolean isSingleProductCheckout, Integer productId, String authHeader) throws Exception {

        if(isSingleProductCheckout && productId!= 0){
            //we are going to buy a single product

            List<Product> productList= new ArrayList<>();
            Product product = productDao.findById(productId).orElseThrow(()-> new Exception("Product Not Found"));
            productList.add(product);
            return ResponseUtils.createSuccessResponse(productList, new TypeReference<List<Product>>() {});

        }else{
            //checkout entire cart
            User dbUser = helperUtils.getUserFromAuthToken(authHeader);
            List<Cart> dbListCart = cartDao.findByUser(dbUser);

            return ResponseUtils.createSuccessResponse(dbListCart.stream().map(Cart::getProduct).toList(), new TypeReference<List<Product>>() {});
        }
    }

    @SneakyThrows
    public ApiResponse<AddProductResponse> addProduct(AddProductRequest productDto) {
        AddProductResponse responce= new AddProductResponse();
        if(productDto.getCategoryId()== null){
            throw new Exception("please ensure valid category");
        }
        Category category = categoryRepository.findById(productDto.getCategoryId())
                .orElseThrow(() -> new Exception("category doesn't exists"));

        Product productToSave = Product.builder()
                .productName(productDto.getProductName())
                .productDescription(productDto.getProductDescription())
                .productDiscountedPrice(productDto.getProductDiscountedPrice())
                .productActualPrice(productDto.getProductActualPrice())

//                .image(productDto.getImage())
//                .productImages(productDto.getProductImages())

                .uploadId(productDto.getUploadId())

                .category(category)
                .build();
                product.save(productToSave);
            responce.setMessage("Success");
        return ResponseUtils.createSuccessResponse(responce, new TypeReference<AddProductResponse>() {
        });

    }


}
