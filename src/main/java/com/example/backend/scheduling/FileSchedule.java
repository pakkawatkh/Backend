package com.example.backend.scheduling;

import com.example.backend.process.service.FileService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class FileSchedule {
    private final FileService service;

    public FileSchedule(FileService service) {
        this.service = service;
    }

    // 1 => second
    // 2 => minute
    // 3 => hour
    // 4 => day
    // 5 => month
    // 6 => year

    @Scheduled(cron = "0 30 0 7 * *", zone = "Asia/Bangkok") //run every all 7 day Time => 00:30
    public void updateImage() {
    //service.mainUpdate();
    }
}
