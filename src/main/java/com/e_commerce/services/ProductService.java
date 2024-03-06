package com.e_commerce.services;

import com.e_commerce.Dto.ProductDto;
import com.e_commerce._util.HelperUtils;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.CategoryRepository;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Category;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final HelperUtils helperUtils;
    private final CartDao cartDao;
    private final CategoryRepository categoryRepository;


    public Product saveProduct(Product product) {

        return productDao.save(product);
    }

    public List<Product> getAllProducts(Integer pageNumber, Integer pageSize, String searchKey) {
        Pageable pageable= PageRequest.of(pageNumber, pageSize);

        if(searchKey== null || searchKey.isEmpty() || searchKey.isBlank()){
            List<Product> dbProducts =(List<Product>) productDao.findAll(pageable);
            System.out.println(dbProducts.size());
            return dbProducts;
        }else{
            List<Product> dbProducts = productDao
                    .findByProductNameContainingIgnoreCaseOrProductDescriptionContainingIgnoreCase(
                            searchKey, searchKey, pageable
                    );
            System.out.println(dbProducts.size());
            return dbProducts;
        }
    }

    public void deleteProductDetails(Integer productId) {
        productDao.deleteById(productId);
    }

    public Product getProductById(Integer productId) {
        return productDao.findById(productId).get();
    }

    public List<Product> getProductDetails(Boolean isSingleProductCheckout, Integer productId, String authHeader) throws Exception {

        if(isSingleProductCheckout && productId!= 0){
            //we are going to buy a single product

            List<Product> productList= new ArrayList<>();
            Product product = productDao.findById(productId).get();
            productList.add(product);
            return productList;

        }else{
            //checkout entire cart
            User dbUser = helperUtils.getUserFromAuthToken(authHeader);
            List<Cart> dbListCart = cartDao.findByUser(dbUser);

            return dbListCart.stream().map(Cart::getProduct).toList();
        }
    }

    @SneakyThrows
    public ProductDto addProduct(ProductDto productDto) {

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
                .productImages(productDto.getProductImages())
                .category(category)
                .build();

        return productDao.save(productToSave).getDto();
    }
}
