package com.e_commerce.dao;

import com.e_commerce.entity.User;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserDao extends JpaRepository<User, Integer> {

//    List<User> findByUserName(String email);

    List<User> findByLiquorCardNumber(String cardnumber);
    List<User> findByGroceryCardNumber(String cardnumber);
    List<User> findByAdhaar(String adhaar);
    List<User> findByPan(String pan);

    List<User> findByLiquorCardNumberOrGroceryCardNumber(String cardNumber, String cardNumber1);

//    List<User> findByUserName(String email);

    @Transactional
    @Modifying
    @Query("UPDATE User c SET c.userPassword = :newPassword")
    int updatePassForAll(@Param("newPassword") String password);


}
