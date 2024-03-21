package com.e_commerce.services;

import com.e_commerce.entity.OrderDetail;
import com.e_commerce.request.OrderInput;
import com.e_commerce.response.ApiResponse;

import java.util.List;
import java.util.Map;

public interface OrderDetailService {

    ApiResponse<OrderDetail> placeOrder(OrderInput orderInput,
                                              String authToken,
                                              Boolean isSingleProductCheckout) throws Exception;

    ApiResponse<List<OrderDetail>> getOrderDetailsOfUser(String authToken) throws Exception;

    ApiResponse<List<OrderDetail>> getAllOrderDetailsOfAdmin(String status);

    void markOrderAsDelivered(Integer orderId) throws Exception;

    void markOrderAsNotDelivered(Integer orderId) throws Exception;

    Map<String, Object> getPdf();
}
