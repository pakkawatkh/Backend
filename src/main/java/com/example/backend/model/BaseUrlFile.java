package com.example.backend.model;

public class BaseUrlFile {
    private String baseDir = "/src/main/resources/static/uploads";
    public String imageUrl(){
        return baseDir+"/image/product";
    }
}
