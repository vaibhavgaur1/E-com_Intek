package com.e_commerce.dao;

import com.e_commerce.entity.Cart;
import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDao extends JpaRepository<Cart, Integer> {

    public List<Cart> findByUser(User user);
}
