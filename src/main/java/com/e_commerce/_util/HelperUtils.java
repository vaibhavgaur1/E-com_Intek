package com.e_commerce._util;

import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class HelperUtils {

    private final UserDao userDao;
    private final JwtUtil jwtUtil;

    public final User getUserFromAuthToken(String authToken) throws Exception {
        if(authToken == null || !authToken.startsWith("Bearer ")){
            throw new Exception("token is invalid!");
        }
        String jwtToken= authToken.substring(7);
        String username = jwtUtil.extractUsername(jwtToken);
        List<User> dbUserList = userDao.findByUserName(username);
        if(dbUserList.isEmpty()){
            throw new Exception("you are not registered!");
        }
        User user = dbUserList.get(0);
        if(user == null){
            throw new Exception("user not found!");
        }
        return user;
    }
}
