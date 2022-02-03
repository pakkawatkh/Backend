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
public class BackendApplication  {


    public static void main(String[] args)  {

//        System.out.println(InetAddress.getLocalHost().getHostAddress());
        SpringApplication.run(BackendApplication.class, args);
    }



}
