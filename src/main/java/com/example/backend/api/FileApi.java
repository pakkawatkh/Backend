package com.example.backend.api;

import com.example.backend.entity.Base.RandomString;
import com.example.backend.exception.FileException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.Response;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/upload")
public class FileApi {

    public static String uploadDirectory = System.getProperty("user.dir");

    @PostMapping("/image")
    public Object uploadProfilePicture(MultipartFile file) throws IOException {

        String dir = new BaseUrlFile().getImageProductUrl();
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String imgName = timeStamp + new RandomString().strImage() + ".png";

        //validate file
        if (file == null) {
            //throw error
            throw FileException.fileNull();
        }

        //validate size
        if (file.getSize() > 1048576 * 5) {
            //throw error
            throw FileException.fileMaxSize();
        }
        String contentType = file.getContentType();
        if (contentType == null) {
            //throw  error
            throw FileException.unsupported();
        }

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(uploadDirectory + dir, imgName);
        fileNames.append(file.getOriginalFilename() + " ");
        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            byte[] bytes = file.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Map<Object, Object> img = new HashMap<>();
        img.put("url",new BaseUrlFile().ipAddress()+dir+imgName);
        img.put("name",imgName);

        return new Response().ok("upload success", "img", img);

    }
}
