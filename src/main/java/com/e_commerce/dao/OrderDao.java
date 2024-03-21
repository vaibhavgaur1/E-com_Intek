package com.e_commerce.dao;

import com.e_commerce.entity.UserOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderDao extends JpaRepository<UserOrders, Integer> {
}
