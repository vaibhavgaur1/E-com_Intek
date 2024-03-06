package com.e_commerce.controller;

import com.e_commerce.entity.OrderDetail;
import com.e_commerce.request.OrderInput;
import com.e_commerce.services.OrderDetailService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/order")
@RequiredArgsConstructor
@CrossOrigin
public class OrderDetailController {

    private final OrderDetailService orderDetailService;

    @PreAuthorize("hasAuthority('USER')")
    @PostMapping("/placeOrder/{isSingleProductCheckout}")
    public List<OrderDetail> placeOrder(
            @PathVariable Boolean isSingleProductCheckout,
            @RequestBody OrderInput orderInput,
            @RequestHeader("Authorization") String authToken) throws Exception {

        System.out.println("isSingleProductCheckout: "+isSingleProductCheckout);
        return  orderDetailService.placeOrder(orderInput, authToken, isSingleProductCheckout);
    }

    @PreAuthorize("hasAuthority('USER')")
    @GetMapping("/getOrderDetailsOfUser")
    public List<OrderDetail> getOrderDetailsOfUser(
            @RequestHeader("Authorization") String authToken) throws Exception
    {
        return orderDetailService.getOrderDetailsOfUser(authToken);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/getAllOrderDetailsOfAdmin/{status}")
    public List<OrderDetail> getAllOrderDetailsOfAdmin(@PathVariable String status) {
        System.out.println(status);
        return orderDetailService.getAllOrderDetailsOfAdmin(status);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/markOrderAsDelivered/{orderId}")
    public void markOrderAsDelivered(@PathVariable Integer orderId) throws Exception {
        orderDetailService.markOrderAsDelivered(orderId);
    }
    @PreAuthorize("hasAuthority('ADMIN')")
    @GetMapping("/markOrderAsNotDelivered/{orderId}")
    public void markOrderAsNotDelivered(@PathVariable Integer orderId) throws Exception {
        orderDetailService.markOrderAsNotDelivered(orderId);
    }
}
