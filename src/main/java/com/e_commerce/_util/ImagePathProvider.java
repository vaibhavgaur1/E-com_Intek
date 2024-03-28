package com.e_commerce._util;

import jakarta.annotation.PostConstruct;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
public class ImagePathProvider {

    String imagesPath;

    private final ResourceLoader resourceLoader;

    public ImagePathProvider(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }

    @PostConstruct
    public void init() throws IOException {
        // Get the resource representing the images directory
        Resource resource = resourceLoader.getResource("classpath:static/pdf/logo.png");

        // Get the absolute path of the images directory
        File imagesDirectory = resource.getFile();
        this.imagesPath = imagesDirectory.getAbsolutePath();

        // Use the images path as needed
        System.out.println("Images directory path: " + imagesPath);
    }
}
