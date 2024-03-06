package com.e_commerce._configuration;

import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Configuration
public class UserDetailService {

    private final UserDao userDao;

    public UserDetailsService userDetailsService(){
        return cardNumber -> {
//            String[] split = cardNumber.split("-");
//            cardNumber= split[0];
//            String cardType= split[1];

//            System.out.println(Arrays.toString(split));

            List<User> users = null;
            System.out.println(cardNumber);

            users= userDao.findByLiquorCardNumberOrGroceryCardNumber(cardNumber, cardNumber);

//            if(cardType.equalsIgnoreCase("liquor")){
//                users =  userDao.findByLiquorCardNumber(cardNumber);
//            }else
//                users =  userDao.findByGroceryCardNumber(cardNumber);
//            users = userDao.findByLiquorCardNumberOrGroceryCardNumber(cardNumber, cardNumber);
            if(users.isEmpty()){
                throw new UsernameNotFoundException("user not found");
            }
            User user= users.get(0);
            if(user == null){
                throw new UsernameNotFoundException("user is null");
            }
//            System.out.println(users.get(0));
//            System.out.println("userDetailsService()::: AUTHORIZED");
            return user;
        };
    }

//    private Set<GrantedAuthority> getAuthorities(User user) {
//        Set<GrantedAuthority> authorities= new HashSet<>();
//        user.getRoles().forEach(role -> {
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+role));
//        });
//        return authorities;
//    }


}
