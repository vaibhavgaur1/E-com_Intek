
package com.e_commerce.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Wishlist {

    @Id
    @GeneratedValue
    private Integer WishlistId;
    @ManyToOne(cascade = CascadeType.DETACH)
    private Product product;
    @ManyToOne(cascade = CascadeType.DETACH)
    private User user;
}