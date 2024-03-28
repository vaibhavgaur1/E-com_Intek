package com.e_commerce.dao;

import com.e_commerce.entity.OrderDetail;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer> {
//    @EntityGraph(attributePaths = {"userOrders"})
    public List<OrderDetail> findByUser(User user);
    public List<OrderDetail> findByOrderStatus(String status);
}
