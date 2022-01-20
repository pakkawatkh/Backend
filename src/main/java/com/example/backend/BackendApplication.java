package com.example.backend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.concurrent.TimeUnit;


@SpringBootApplication
public class BackendApplication implements WebMvcConfigurer {
    public static String uploadDirectory = System.getProperty("user.dir");

    private static Logger logger = LoggerFactory.getLogger(BackendApplication.class.getName());

    public static void main(String[] args) throws UnknownHostException {

        System.out.println(InetAddress.getLocalHost().getHostAddress());
        SpringApplication.run(BackendApplication.class, args);
    }


    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").addResourceLocations("file:" + uploadDirectory + "/")
                .setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }
}
