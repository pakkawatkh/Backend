package com.example.backend.process.business;

import com.example.backend.exception.BaseException;
import com.example.backend.exception.EmailException;
import com.example.backend.process.service.EmailService;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

@Service
public class EmailBusiness {

    private final EmailService service;

    public EmailBusiness(EmailService service) {
        this.service = service;
    }

    public void sendActivateUserEmail(String email, String name, String token) throws BaseException {
        //(HTML)
        String html;
        try {
            html = readEmailTemplate("email-activate-user.html");
        } catch (IOException e) {
            throw EmailException.templateNotFound();
        }

        String finalLink= "http://localhost:4200/activate/"+token;
        html = html.replace("${P_NAME}",name);
        html = html.replace("${LINK}",finalLink);

        String subject = "Please activate your account";

        service.send(email, subject, html);
    }

    private String readEmailTemplate(String fileName) throws IOException {
        File file = ResourceUtils.getFile(("classpath:email/" + fileName));
        return FileCopyUtils.copyToString(new FileReader(file));
    }
}
