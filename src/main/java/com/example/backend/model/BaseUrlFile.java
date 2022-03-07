package com.example.backend.model;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
public class BaseUrlFile {

    private final String domain = "https://f25f-2403-6200-8837-c1f-80b6-e999-cc84-3a18.ngrok.io";
//    private final String domain = "http://localhost:8080";

    private final String baseDir = "/uploads/image";

    private final String imageOrderUrl =  this.baseDir + "/product/";

    private final String imageProfileUrl = this.baseDir + "/profile/";

    private final String imageNewsUrl = this.baseDir + "/news/";

    public String ipAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
