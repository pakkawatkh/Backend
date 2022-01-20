package com.example.backend.model;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
public class BaseUrlFile {


    private String domain = "http://localhost:8080";

    private String baseDir = "/uploads";

    private String imageProductUrl = this.domain+this.baseDir+"/image/product/";

    private String imageProfileUrl = this.baseDir+"/image/profile/";


    public String ipAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
