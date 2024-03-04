package com.e_commerce.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor@NoArgsConstructor
@Builder
public class OrderDetail {

    @Id
    @GeneratedValue
    private Integer orderId;
    private String orderFullName;
    private String orderFullOrder;

    private String orderContactNumber;
    private String orderAlternateContactNumber;
    private String orderStatus;
    private Double orderAmount;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private Product product;
    @ManyToOne(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    private User user;

}
