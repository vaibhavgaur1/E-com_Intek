package com.e_commerce.request;

import com.e_commerce.entity.OrderProductQuantity;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderInput {

    private String fullName;
    private String fullAddress;
    private String contactNumber;
    private String alternateContactNumber;
    private String selectedStore;
    private Double totalAmount;

    private List<OrderProductQuantity>  orderProductQuantityList;
    private String cardType;
}
