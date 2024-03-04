package com.e_commerce.services;

import com.e_commerce._util.HelperUtils;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.entity.Cart;
import com.e_commerce.entity.Product;
import com.e_commerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service@RequiredArgsConstructor
public class ProductService {

    private final ProductDao productDao;
    private final HelperUtils helperUtils;
    private final CartDao cartDao;


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
}
