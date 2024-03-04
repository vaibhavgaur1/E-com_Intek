package com.e_commerce.services;

import com.e_commerce.Dto.OtpDto;
import com.e_commerce.Dto.RegisterDto;
import com.e_commerce._util.EmailService;
import com.e_commerce._util.OTPGenerator;
import com.e_commerce.dao.RoleDao;
import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.Role;
import com.e_commerce.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserDao userDao;
    private final RoleDao roleDao;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public User registerNewUser(RegisterDto registerDto) throws Exception {

        List<User> dbListUser = userDao.findByUserName(registerDto.getName());
        if(!dbListUser.isEmpty())
            throw new Exception("User with id: "+registerDto.getName()+" is already present");

        List<User> userCard1 = userDao.findByLiquorCardNumber(registerDto.getLiquorCardNumber());
        if(!userCard1.isEmpty())
            throw new Exception("User with cardNo.: "+registerDto.getLiquorCardNumber()+" is already present");

        List<User> userCard2 = userDao.findByGroceryCardNumber(registerDto.getGroceryCardNumber());
        if(!userCard2.isEmpty())
            throw new Exception("User with cardNo.: "+registerDto.getGroceryCardNumber()+" is already present");

        List<User> userAdhaar = userDao.findByAdhaar(registerDto.getAdhaar());
        if(!userAdhaar.isEmpty())
            throw new Exception("User with adhaar: "+registerDto.getAdhaar()+" is already present");

        List<User> userPan = userDao.findByPan(registerDto.getPan());
        if(!userPan.isEmpty())
            throw new Exception("User with pan: "+registerDto.getPan()+" is already present");


        Role dbRole = roleDao.findById("USER").orElseThrow(()-> new Exception(""));
        Set<Role> roleSet= new HashSet<>();
        roleSet.add(dbRole);

        User user = User.builder()
                .contactNumber(registerDto.getContactNumber())
                .adhaar(registerDto.getAdhaar())
                .liquorCardNumber(registerDto.getLiquorCardNumber())
                .dob(registerDto.getDob())
                .email(registerDto.getEmail())
                .pan(registerDto.getPan())
                .groceryCardNumber(registerDto.getGroceryCardNumber())
                .userPassword(passwordEncoder.encode(registerDto.getPassword()))
                .roles(roleSet)
                .otp(OTPGenerator.generateOtp())
                .isVerified(false)
                .build();

        User save = userDao.save(user);
        emailService.sendOtpMail(user.getOtp());
        return save;
    }


//    public void initRoleAndUser() {
//
//        if(!roleDao.findAll().isEmpty() && !userDao.findAll().isEmpty() )
//            return ;
//
//        Role adminRole = Role.builder()
//                .roleName("ADMIN")
//                .roleDescription("ADMIN ROLE")
//                .build();
//        Role userRole = Role.builder()
//                .roleName("USER")
//                .roleDescription("USER ROLE")
//                .build();
//
//        roleDao.save(adminRole);
//        roleDao.save(userRole);
//
//        Set<Role> adminRoles = Set.of(adminRole);
//        Set<Role> userRoles = Set.of(userRole);
//
//        User user = User.builder()
//                .userName("user@gmail.com")
//                .firstName("user")
//                .lastName("user")
//                .userPassword(passwordEncoder.encode("user"))
//                .roles(userRoles)
//                .build();
//
//        User admin = User.builder()
//                .userName("admin@gmail.com")
//                .firstName("admin")
//                .lastName("admin")
//                .userPassword(passwordEncoder.encode("admin"))
//                .roles(adminRoles)
//                .build();
//        userDao.save(admin);
//        userDao.save(user);
//    }

    public boolean verifyOtp(OtpDto otpDto) {
        User user =null;
        List<User> dbUserList= null;
        if (otpDto.getCardType().equalsIgnoreCase("liqour"))
            dbUserList= userDao.findByLiquorCardNumber(otpDto.getCardNumber());
        else
            dbUserList = userDao.findByGroceryCardNumber(otpDto.getCardNumber());

        Long otp = user.getOtp();

        if(otp.equals(otpDto.getOtp())){
            user.setVerified(true);
            userDao.save(user);
            return true;
        }
        return false;
    }
}
