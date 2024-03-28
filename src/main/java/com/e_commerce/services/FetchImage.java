package com.e_commerce.services;

import com.e_commerce._util.HelperUtils;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
@Service
@RequiredArgsConstructor
public class FetchImage {

    private final HelperUtils helperUtils;
    @SneakyThrows
    public byte[] getFile(String fullFilePath){
        System.out.println("fullFilePath: "+fullFilePath);
        System.out.println("HelperUtils.LASTFOLDERPATH+fullFilePath: "+ helperUtils.getPathForImage()+fullFilePath);
        InputStream inputStream = null;

        String currentDirectory = System.getProperty("user.dir");
        File currentDir = new File(currentDirectory);

        // Get the parent directory
        File parentDir = currentDir.getParentFile();
        String parentPath =null;
        if (parentDir != null) {
            // Get the absolute path of the parent directory
            parentPath = parentDir.getAbsolutePath();
            parentPath= parentPath+ "/images/";
            System.out.println("Parent directory: " + parentPath);
        }
        System.out.println(currentDirectory);
        try {
            File file = new File(parentPath+ fullFilePath);
//            File file = new File(HelperUtils.LASTFOLDERPATH + "/"+ fullFilePath);
            inputStream = new FileInputStream(file);


            return inputStream.readAllBytes();
        }
        finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
