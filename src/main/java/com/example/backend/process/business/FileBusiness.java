package com.example.backend.process.business;

import com.example.backend.exception.BaseException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.Response;
import com.example.backend.process.service.FileService;
import com.example.backend.process.service.token.TokenService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

@Service
public class FileBusiness {

    private final String MS = "success";
    private final FileService service;
    private final TokenService tokenService;
    private String address = new BaseUrlFile().ipAddress();
    private BaseUrlFile baseUrlFile = new BaseUrlFile();

    public FileBusiness(FileService service, TokenService tokenService) throws UnknownHostException {
        this.service = service;
        this.tokenService = tokenService;
    }

    public Object saveImgOrder(MultipartFile file) throws BaseException {
        tokenService.checkUserByToken();

        String imageDir = this.baseUrlFile.getImageOrderUrl();
        String imgName = service.saveImg(file, imageDir);
        return setResponse(imageDir, imgName);
    }

    public Object saveImgProfile(MultipartFile file) throws BaseException {
        tokenService.checkUserByToken();

        String imageDir = this.baseUrlFile.getImageProfileUrl();
        String imgName = service.saveImg(file, imageDir);
        return setResponse(imageDir, imgName);
    }

    public Object saveImgNews(MultipartFile file) throws BaseException {
        tokenService.checkUserByToken();

        String imageDir = this.baseUrlFile.getImageNewsUrl();
        String imgName = service.saveImg(file, imageDir);
        return setResponse(imageDir, imgName);
    }

    public Object setResponse(String imageDir, String imgName) {
        Map<Object, Object> img = new HashMap<>();
        img.put("url", this.address + imageDir + imgName);
        img.put("name", imgName);
        return new Response().ok(MS, "img", img);
    }


}
