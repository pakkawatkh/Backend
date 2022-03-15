package com.example.backend.process.service;

import com.example.backend.entity.Base.RandomString;
import com.example.backend.exception.BaseException;
import com.example.backend.exception.FileException;
import com.example.backend.model.BaseUrlFile;
import com.example.backend.process.repository.NewsRepository;
import com.example.backend.process.repository.OrdersRepository;
import com.example.backend.process.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.Date;

@Service
public class FileService {
    public static String uploadDirectory = System.getProperty("user.dir");
    private final BaseUrlFile url = new BaseUrlFile();

    private final UserRepository userRepository;
    private final OrdersRepository ordersRepository;
    private final NewsRepository newsRepository;
    @Value("${app.img.size}")
    String imgSize;

    public FileService(UserRepository userRepository, OrdersRepository ordersRepository, NewsRepository newsRepository) {
        this.userRepository = userRepository;
        this.ordersRepository = ordersRepository;
        this.newsRepository = newsRepository;

    }

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

    //update File
    public void mainUpdate() {
        updatePictureProduct();
        updatePictureProfile();
        updatePictureNews();
    }

    public void updatePictureProduct() {
        String[] picture = ordersRepository.getAllPicture();
        String dir = uploadDirectory + url.getImageOrderUrl();
        File folder = new File(dir);
        this.checkPicture(folder, picture, dir);
    }

    public void updatePictureProfile() {
        String[] picture = userRepository.getAllPicture();
        String dir = uploadDirectory + url.getImageProfileUrl();
        File folder = new File(dir);
        this.checkPicture(folder, picture, dir);
    }

    public void updatePictureNews() {
        String[] picture = newsRepository.getAllPicture();
        String dir = uploadDirectory + url.getImageNewsUrl();
        File folder = new File(dir);

        for (File file : folder.listFiles()) {
            int count = 0;
            for (String pic : picture) {
                String[] split = pic.split(", ");
                for (String s : split)
                    if (file.getName().equals(s)){
                        count++;
                        break;
                    }
            }
            if (count == 0) this.deleteImage(dir, file.getName());
        }
    }

    private void checkPicture(File folder, String[] picture, String dir) {

        for (File file : folder.listFiles()) {
            int count = 0;

            for (String pic : picture)
                if (file.getName().equals(pic)){
                    count++;
                    break;
                }

            if (count == 0) this.deleteImage(dir, file.getName());
        }
    }

    private void deleteImage(String dir, String fileName) {
        try {
            File file = new File(dir + fileName);
            file.delete();
        } catch (Exception e) {
            System.out.println("Failed to Delete image !!");
        }
    }
}
