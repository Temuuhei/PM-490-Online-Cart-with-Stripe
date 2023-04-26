package com.pm490.PM490.controller;

import com.pm490.PM490.dto.EmailDto;
import com.pm490.PM490.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;


@RestController
@RequestMapping("/api/email")
public class EmailController {

    JavaMailSender sender;
    @Autowired
    private EmailService emailService;

    public EmailController(JavaMailSender sender) {
        this.sender = sender;
    }

    @GetMapping(value = "/sendmail")
    public String sendEmail() throws MessagingException, IOException {
//        emailService.sendEmail(email);
        MimeMessage message = sender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);
        helper.setSubject("Welcome to FiveLayer E commerce");
        helper.setFrom("***********@gmail.com", "Five-Layers Shop");
        helper.setTo("*********@gmail.com");
        sender.send(message);
        return "Email sent successfully";
    }

}


