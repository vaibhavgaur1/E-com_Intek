package com.e_commerce.dao;

import com.e_commerce.entity.User;
import com.e_commerce.entity.Wishlist;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WishlistDao extends JpaRepository<Wishlist, Integer> {

    public List<Wishlist> findByUser(User user);
}
