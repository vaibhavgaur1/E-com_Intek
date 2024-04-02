package com.e_commerce.dao;

import com.e_commerce.entity.OrderDetail;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderDetailDao extends JpaRepository<OrderDetail, Integer> {
//    @EntityGraph(attributePaths = {"userOrders"})
    public List<OrderDetail> findByUser(User user);
    public List<OrderDetail> findByOrderStatus(String status);

    Optional<OrderDetail> findByOrderId(String orderId);

    @Transactional
    @Modifying
    @Query( "update OrderDetail u set u.pdfUrl = :pdfUrl where u.id = :id")
    int setPdfUrl(@Param("id") Integer id,
                         @Param("pdfUrl") String pdfUrl);
}
