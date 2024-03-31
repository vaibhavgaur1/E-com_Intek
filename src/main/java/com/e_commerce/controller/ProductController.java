package com.e_commerce.controller;

import com.e_commerce.entity.ImageModel;
import com.e_commerce.entity.Product;
import com.e_commerce.request.AddProductRequest;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.response.AddProductResponse;
import com.e_commerce.services.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
@CrossOrigin
public class ProductController {

    private final ProductService productService;

//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping(value = "/add", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ApiResponse<Product>> saveProduct(
//            @RequestPart("product") Product product,
//            @RequestPart("imageFiles")MultipartFile[] files) throws Exception {
//        try{
//            product.setImage(multiPartToImageModel(files).get(0).getImageBytes());
//            return ResponseEntity.ok(productService.saveProduct(product));
//        }catch (IOException e){
//            e.printStackTrace();
//            throw new Exception("can not convert the files...");
//        }
//    }

    public List<ImageModel> multiPartToImageModel(MultipartFile[] files) throws IOException {

        List<ImageModel> imageModels= new ArrayList<>();
        for(MultipartFile file: files){
            imageModels.add(ImageModel.builder()
                    .name(file.getOriginalFilename())
                    .type(file.getContentType())
                    .imageBytes(file.getBytes())
                    .build()
            );
        }
        return imageModels;
    }


    //    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllProducts")
    public ApiResponse<List<Product>> getAllProducts(
//                                                     @RequestParam(defaultValue = "0")Integer pageNumber,
//                                                     @RequestParam(defaultValue = "6")Integer pageSize,
//                                                     @RequestParam(defaultValue = "")String searchKey
    ){
//        System.out.println(searchKey);
        return productService.getAllProducts();   //pageNumber, pageSize, searchKey
    }

    //    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getProductById/{productId}")
    public ApiResponse<Product> getProductById(@PathVariable Integer productId){
        return productService.getProductById(productId);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @DeleteMapping("/deleteProductDetails/{productId}")
    public void deleteProductDetails(@PathVariable Integer productId) {
        productService.deleteProductDetails(productId);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getProductDetails/{isSingleProductCheckout}/{productId}")
    public ApiResponse<List<Product>> getProductDetails(
            @PathVariable(name = "isSingleProductCheckout") Boolean isSingleProductCheckout,
            @PathVariable(name = "productId") Integer productId,
            @RequestHeader("Authorization") String authHeader
    ) throws Exception {
        return productService.getProductDetails(isSingleProductCheckout, productId, authHeader);
    }



//    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping(value = "/add-product", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
//    public ResponseEntity<ApiResponse<ProductDto>> addProduct(
//            @RequestPart("productDto") ProductDto productDto,
//            @RequestPart("imageFiles")MultipartFile[] files) throws IOException {
////        productDto.ProductImages(multiPartToImageModel(files));
//        productDto.setImage(multiPartToImageModel(files).get(0).getImageBytes());
//        return ResponseEntity.ok(productService.addProduct(productDto));
//    }

////    @PreAuthorize("hasAuthority('ADMIN')")
//    @PostMapping("/add-product")
//    public ResponseEntity<ApiResponse<ProductDto>> addProduct(@RequestPart ProductDto productDto) throws IOException {
//        return ResponseEntity.ok(productService.addProduct(productDto));
//    }
@PostMapping("/addProduct")
public ResponseEntity<ApiResponse<AddProductResponse>> getOldCdaDataForRebase(@RequestBody AddProductRequest req) {
    return new ResponseEntity<>(productService.addProduct(req), HttpStatus.OK);

    }

}
