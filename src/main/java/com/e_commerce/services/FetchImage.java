package com.e_commerce.services;

import com.e_commerce._util.HelperUtils;
import lombok.SneakyThrows;
import lombok.experimental.UtilityClass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
@UtilityClass
public class FetchImage {
    @SneakyThrows
    public byte[] getFile(String fullFilePath){
        System.out.println("fullFilePath: "+fullFilePath);
        System.out.println("HelperUtils.LASTFOLDERPATH+fullFilePath: "+ HelperUtils.LASTFOLDERPATH+fullFilePath);
        InputStream inputStream = null;
        try {
            File file = new File(HelperUtils.LASTFOLDERPATH + "/"+ fullFilePath);
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
