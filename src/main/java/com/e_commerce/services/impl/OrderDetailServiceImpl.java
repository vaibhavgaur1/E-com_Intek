package com.e_commerce.services.impl;

import com.e_commerce._util.Bill;
import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.JwtUtil;
import com.e_commerce._util.ResponseUtils;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.OrderDetailDao;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.*;
import com.e_commerce.request.OrderInput;
import com.e_commerce.response.ApiResponse;
import com.e_commerce.services.OrderDetailService;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderDetailServiceImpl implements OrderDetailService {

    private final OrderDetailDao orderDetailDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final HelperUtils helperUtils;
    private final CartDao cartDao;
    private final Bill billGenerator;



    private static final String ORDER_PLACED= "Placed";

    public ApiResponse<List<OrderDetail>> placeOrder(OrderInput orderInput, String authToken, Boolean isSingleProductCheckout) throws Exception {
        List<OrderProductQuantity> productQuantityList = orderInput.getOrderProductQuantityList();
        User dbUser = helperUtils.getUserFromAuthToken(authToken);
        List<OrderDetail> detailList= new ArrayList<>();

        productQuantityList.forEach(orderProductQuantity -> {

            Product dbProduct = productDao.
                    findById(orderProductQuantity.getProductId()).get();

            OrderDetail orderDetail = OrderDetail.builder()
                    .orderFullName(orderInput.getFullName())
                    .orderFullOrder(orderInput.getFullAddress())
                    .orderContactNumber(orderInput.getContactNumber())
                    .orderAlternateContactNumber(orderInput.getAlternateContactNumber())
                    .orderStatus(ORDER_PLACED)
                    .orderAmount(dbProduct.getProductDiscountedPrice() * orderProductQuantity.getQuantity())
                    .product(dbProduct)
                    .user(dbUser)
                    .build();

            List<Cart> dbListCart = cartDao.findByUser(dbUser);
            if(!isSingleProductCheckout){
                dbListCart.forEach(cartDao::delete);
            }
            else{
                dbListCart.stream().filter(cart -> cart.getProduct().getProductId() == dbProduct.getProductId()).forEach(cartDao::delete);
            }

            detailList.add(orderDetailDao.save(orderDetail));
        });
        return ResponseUtils.createSuccessResponse(detailList, new TypeReference<List<OrderDetail>>() {});
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
