package com.e_commerce.dao;

import com.e_commerce.entity.Otp;
import com.e_commerce.entity.Product;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OtpDao  extends JpaRepository<Otp, String> {

    Optional<Otp> findByEmail(String email);


}
