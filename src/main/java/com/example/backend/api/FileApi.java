package com.example.backend.api;

import com.example.backend.process.business.FileBusiness;
import com.example.backend.entity.Base.RandomString;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.model.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    private static final String uploadDirectory = System.getProperty("user.dir");

    private final FileBusiness business;

    public FileApi(FileBusiness business) {
        this.business = business;
    }

    @PostMapping("/image")
    public Object uploadProfilePicture(@RequestParam("file") MultipartFile file) throws IOException {

        String timeStamp = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss").format(new Date());
        String imgName = timeStamp + new RandomString().strImage() + ".png";

        //validate file
        if (file.isEmpty()) throw FileException.fileNull();
        //validate size
        if (file.getSize() > 1048576 * 5) throw FileException.fileMaxSize();

        String contentType = file.getContentType();
        if (contentType == null) throw FileException.unsupported();

        StringBuilder fileNames = new StringBuilder();

        Path fileNameAndPath = Paths.get(uploadDirectory + new BaseUrlFile().getImageOrderUrl(), imgName);
        fileNames.append(file.getOriginalFilename());
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
        BaseUrlFile baseUrlFile = new BaseUrlFile();
        Map<Object, Object> img = new HashMap<>();
        img.put("url", baseUrlFile.getDomain() + baseUrlFile.getImageOrderUrl() + imgName);
        img.put("name", imgName);

        return new Response().ok("upload success", "img", img);
    }

    @PostMapping("/image-order")
    public ResponseEntity<Object> uploadOrder(@RequestParam("file") MultipartFile file) throws BaseException {
        return ResponseEntity.ok(business.saveImgOrder(file));
    }

    @PostMapping("/image-profile")
    public ResponseEntity<Object> uploadProfile(@RequestParam("file") MultipartFile file) throws BaseException {
        return ResponseEntity.ok(business.saveImgProfile(file));
    }

    @PostMapping("/image-news")
    public ResponseEntity<Object> uploadNews(@RequestParam("file") MultipartFile file) throws BaseException {
        return ResponseEntity.ok(business.saveImgNews(file));
    }
}
