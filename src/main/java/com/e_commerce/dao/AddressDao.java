package com.e_commerce.dao;

import com.e_commerce.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AddressDao extends JpaRepository<Address,Long> {

    List<Address> findByUserId(Integer userId);
}
