package com.e_commerce.services;

import com.e_commerce._util.HelperUtils;
import com.e_commerce._util.JwtUtil;
import com.e_commerce.dao.CartDao;
import com.e_commerce.dao.OrderDetailDao;
import com.e_commerce.dao.ProductDao;
import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.*;
import com.e_commerce.request.OrderInput;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
    //        78878
//                315432833

    private final OrderDetailDao orderDetailDao;
    private final ProductDao productDao;
    private final UserDao userDao;
    private final JwtUtil jwtUtil;
    private final HelperUtils helperUtils;
    private final CartDao cartDao;



    private static final String ORDER_PLACED= "Placed";

    public List<OrderDetail> placeOrder(OrderInput orderInput, String authToken, Boolean isSingleProductCheckout) throws Exception {
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
        return detailList;
    }


    public List<OrderDetail> getOrderDetailsOfUser(String authToken) throws Exception {
        User dbUser = helperUtils.getUserFromAuthToken(authToken);
        List<OrderDetail> byUser = orderDetailDao.findByUser(dbUser);
        return byUser;

    }

    public List<OrderDetail> getAllOrderDetailsOfAdmin(String status) {

        if(status.equalsIgnoreCase("ALL"))
            return orderDetailDao.findAll();
        else {
            return orderDetailDao.findByOrderStatus(status);
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
}
