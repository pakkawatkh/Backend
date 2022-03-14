package com.example.backend.process.service;

import com.example.backend.entity.Base.RandomString;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    public static String uploadDirectory = System.getProperty("user.dir");
    @Value("${app.img.size}")
    String imgSize;

    public String saveImg(MultipartFile file, String imageDir) throws BaseException {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());

        this.validateFile(file);
        String image_name = timeStamp + "-" + new RandomString().strImage() + ".png";

        Path fileNameAndPath = Paths.get(uploadDirectory + imageDir, image_name);

        try {
            Files.write(fileNameAndPath, file.getBytes());
        } catch (IOException e) {
            throw FileException.errorWrite();
        }
        try {
            file.getBytes();
        } catch (IOException e) {
            throw FileException.fileNull();
        }
        return image_name;
    }

    public void validateFile(MultipartFile file) throws BaseException {
        if (file.isEmpty()) throw FileException.fileNull();
        if (file.getSize() > 1048576 * 5) throw FileException.fileMaxSize();
        if (file.getContentType() == null) throw FileException.unsupported();
    }
}
