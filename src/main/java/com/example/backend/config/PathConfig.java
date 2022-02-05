package com.example.backend.config;

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

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {

        registry.addResourceHandler("/**").
                addResourceLocations("file:" + uploadDirectory + "/").
                setCacheControl(CacheControl.maxAge(2, TimeUnit.HOURS).cachePublic());

    }


}
