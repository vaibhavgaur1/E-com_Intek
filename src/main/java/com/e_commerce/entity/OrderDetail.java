package com.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.BatchSize;

import java.util.List;

@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue
    private Integer id;
    private String orderId;
    private String orderByName;
    private String deliveryAddress;
    private String selectedStore;

    private String contactNo;
    private String alternateContactNumber;
    private String orderStatus;
    private Double totalOrderAmount;

    @ManyToOne( fetch = FetchType.LAZY)
    private User user;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "orderDetail")
    @BatchSize(size = 40)
    private List<UserOrders> userOrders;

}
