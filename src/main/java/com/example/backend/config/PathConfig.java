package com.example.backend.config;

import com.example.backend.BackendApplication;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.CacheControl;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.concurrent.TimeUnit;


@EnableWebMvc
@Configuration
public class PathConfig implements WebMvcConfigurer {
    public static String uploadDirectory = System.getProperty("user.dir");

    private static Logger logger = LoggerFactory.getLogger(BackendApplication.class.getName());

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {


        registry.addResourceHandler("/**").addResourceLocations("file:" + uploadDirectory + "/").setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());
    }
}
