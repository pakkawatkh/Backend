package com.example.backend.scheduling;

import com.example.backend.entity.User;
import com.example.backend.process.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Log4j2
public class UserSchedule {

    private final UserService userService;

    public UserSchedule(UserService userService) {
        this.userService = userService;
    }

    // 1 => second
    // 2 => minute
    // 3 => hour
    // 4 => day
    // 5 => month
    // 6 => day of week

    @Scheduled(cron = "0 0 0 * * MON", zone = "Asia/Bangkok") //run every monday Time => 00:00
    public void deleteUser() {
        List<User> user = userService.fileAll();
        for (User u : user) {
            if (!u.getRegister()) userService.deleteByUser(u);
        }
        //delete all user is not activate

    }
}
