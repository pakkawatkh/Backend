package com.example.backend.model;

import lombok.Data;

import java.net.InetAddress;
import java.net.UnknownHostException;

@Data
public class BaseUrlFile {

    private final String domain = "http://74ff-2403-6200-8837-c1f-cc34-dc0e-781e-42f3.ngrok.io";
//    private final String domain = "http://localhost:8080";

    private final String baseDir = "/uploads/image";

    private final String imageOrderUrl =  this.baseDir + "/product/";

    private final String imageProfileUrl = this.baseDir + "/profile/";

    private final String imageNewsUrl = this.baseDir + "/news/";

    public String ipAddress() throws UnknownHostException {
        return InetAddress.getLocalHost().getHostAddress();
    }
}
