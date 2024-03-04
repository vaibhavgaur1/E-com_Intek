package com.e_commerce.dao;

import com.e_commerce.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

    List<User> findByUserName(String email);

    List<User> findByLiquorCardNumber(String cardnumber);
    List<User> findByGroceryCardNumber(String cardnumber);
    List<User> findByAdhaar(String adhaar);
    List<User> findByPan(String pan);

    List<User> findByLiquorCardNumberOrGroceryCardNumber(String cardNumber, String cardNumber1);

//    List<User> findByUserName(String email);

}
