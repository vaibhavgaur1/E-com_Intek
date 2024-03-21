package com.e_commerce.services.impl;

import com.e_commerce._util.Bill;
import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.JwtUtil;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.*;
import com.e_commerce.entity.*;
import com.e_commerce.request.OrderInput;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.OrderDetailService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDao orderDetailDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final HelperUtils helperUtils;
    private final CartDao cartDao;
    private final UserOrderDao userOrderDao;
    private final Bill billGenerator;



    private static final String ORDER_PLACED= "Placed";

    public ApiResponse<OrderDetail> placeOrder(OrderInput orderInput, String authToken, Boolean isSingleProductCheckout) throws Exception {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        User dbUser = helperUtils.getUserFromAuthToken(authToken);
//        List<OrderDetail> detailList= new ArrayList<>();

        AtomicBoolean allGood = new AtomicBoolean(true);
        productQuantityList.forEach(productQuantity-> {
            boolean b = productDao.existsById(productQuantity.getProductId());
            if(b){
                allGood.set(true);
                return;
            }
        });
        if(allGood.get() == false){
            throw new Exception("product not found");
        }

        OrderDetail orderDetail = OrderDetail.builder()
                .orderId(HelperUtils.generateOrderId())
                .orderByName(orderInput.getFullName())
                .deliveryAddress(orderInput.getFullAddress())
                .selectedStore(orderInput.getSelectedStore())
                .contactNo(orderInput.getContactNumber())
                .alternateContactNumber(orderInput.getAlternateContactNumber())
                .orderStatus(ORDER_PLACED)
                .totalOrderAmount(orderInput.getTotalAmount())
                .build();

        OrderDetail savedOrderDetail = orderDetailDao.save(orderDetail);

        productQuantityList.forEach(orderProductQuantity -> {

            Product dbProduct;
            try {
                dbProduct = productDao.
                        findById(orderProductQuantity.getProductId()).get();
            } catch (Exception e) {
                throw new RuntimeException("no product found");
            }
            UserOrders userOrders = UserOrders.builder()
                    .product(dbProduct)
                    .quantity(orderProductQuantity.getQuantity())
                    .productTotalAmount(orderProductQuantity.getTotalProductAmount())
                    .orderDetail(savedOrderDetail)
                    .build();

            userOrderDao.save(userOrders);




//            OrderDetail orderDetail = OrderDetail.builder()
//                    .orderId(HelperUtils.generateOrderId())
//                    .orderByName(orderInput.getFullName())
//                    .deliveryAddress(orderInput.getFullAddress())
//                    .selectedStore(orderInput.getSelectedStore())
//                    .contactNo(orderInput.getContactNumber())
//                    .alternateContactNumber(orderInput.getAlternateContactNumber())
//                    .orderStatus(ORDER_PLACED)
//                    .totalOrderAmount(dbProduct.getProductDiscountedPrice() * orderProductQuantity.getQuantity())
////                    .product(dbProduct)
//                    .user(dbUser)
//                    .build();

            List<Cart> dbListCart = cartDao.findByUser(dbUser);
            if(!isSingleProductCheckout){
                dbListCart.forEach(cartDao::delete);
            }
            else{
                dbListCart.stream().filter(cart -> cart.getProduct().getProductId() == dbProduct.getProductId()).forEach(cartDao::delete);
            }

//            detailList.add(orderDetailDao.save(orderDetail));
        });
        return ResponseUtils.createSuccessResponse(orderDetail, new TypeReference<OrderDetail>() {});
    }


    public ApiResponse<List<OrderDetail>> getOrderDetailsOfUser(String authToken) throws Exception {
        User dbUser = helperUtils.getUserFromAuthToken(authToken);
        List<OrderDetail> byUser = orderDetailDao.findByUser(dbUser);
        return ResponseUtils.createSuccessResponse(byUser, new TypeReference<List<OrderDetail>>() {});

    }

    public ApiResponse<List<OrderDetail>> getAllOrderDetailsOfAdmin(String status) {

        if(status.equalsIgnoreCase("ALL"))
            return ResponseUtils.createSuccessResponse(orderDetailDao.findAll(), new TypeReference<List<OrderDetail>>() {});
        else {
            return ResponseUtils.createSuccessResponse(orderDetailDao.findByOrderStatus(status), new TypeReference<List<OrderDetail>>() {});
        }
    }

    public void markOrderAsDelivered(Integer orderId) throws Exception {
        OrderDetail dbOrderDetail = orderDetailDao.findById(orderId)
                .orElseThrow(() -> new Exception("order not found!!"));

        dbOrderDetail.setOrderStatus("DELIVERED");
        orderDetailDao.save(dbOrderDetail);
    }
    public void markOrderAsNotDelivered(Integer orderId) throws Exception {
        OrderDetail dbOrderDetail = orderDetailDao.findById(orderId)
                .orElseThrow(() -> new Exception("order not found!!"));

        dbOrderDetail.setOrderStatus("NOT_DELIVERED");
        orderDetailDao.save(dbOrderDetail);
    }
    public Map<String, Object> getPdf() {

        Map<String, Object> response = new HashMap<>();



        byte[] pdfBytes = billGenerator.generateBillByteArray();
        response.put("pdfBytes", pdfBytes);
        response.put("message", "PDF generated");
        return response;
    }
}
