package com.e_commerce._util;

import com.e_commerce.dao.UserDao;
import com.e_commerce.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
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
        List<User> dbUserList = userDao.findByLiquorCardNumberOrGroceryCardNumber(username,username);
        if(dbUserList.isEmpty()){
            throw new Exception("you are not registered!");
        }
        User user = dbUserList.get(0);
        if(user == null){
            throw new Exception("user not found!");
        }
        return user;
    }
//    public static String LASTFOLDERPATH = "C:/Program Files/Apache Software Foundation/Tomcat 9.0/webapps/images";
//    public static String LASTFOLDERPATH ="C:\\Program Files\\Apache Software Foundation\\Tomcat 10.1_Tomcat1.0\\webapps\\images";
    public static String LASTFOLDERPATH =System.getProperty("user.dir");

     public static String FILEPATH = "/";
    private final ResourceLoader resourceLoader;

    public static String generateOrderId() {
        return "order_" + ConverterUtils.getRandomTimeStamp();
    }

    @SneakyThrows
    public  String getPathForImage()  {

        File currentDir = new File(LASTFOLDERPATH);
        String imagesDir= currentDir.getAbsolutePath()+"/webapps/images/";
        return imagesDir;
    }

    @SneakyThrows
    public  String getCompleteImage()  {

        File currentDir = new File(LASTFOLDERPATH);
        String imagesDir= "http://13.200.245.74:8083/images/";
        return imagesDir;
    }


    public  String getPathForPdf()  {

        File currentDir = new File(LASTFOLDERPATH);
//        String pdfDir= currentDir.getAbsolutePath()+"\\src\\main\\resources\\static\\pdf\\";
        String pdfDir= currentDir.getAbsolutePath()+"/webapps/pdf/";

        File mainFilePath = new File(pdfDir);

        if (!mainFilePath.exists()) {
//            System.out.println("path ban raha he-------------------------------------------------------------------");
            mainFilePath.mkdirs();
//            System.out.println("path ban gaya he-------------------------------------------------------------------");

        }

        return pdfDir;
    }
    public  String getCompletePdf()  {

        File currentDir = new File(LASTFOLDERPATH);
//        String pdfDir= currentDir.getAbsolutePath()+"\\src\\main\\resources\\static\\pdf\\";
        String pdfDir="http://13.200.245.74:8083/pdf/" ;   //currentDir.getAbsolutePath()+"/webapps/pdf/"

        File mainFilePath = new File(pdfDir);

        if (!mainFilePath.exists()) {
//            System.out.println("path ban raha he-------------------------------------------------------------------");
            mainFilePath.mkdirs();
//            System.out.println("path ban gaya he-------------------------------------------------------------------");

        }

        return pdfDir;
    }




    public static String getDocumentId() {
        return "BM_DOC" + ConverterUtils.getRandomTimeStamp();
    }
}
