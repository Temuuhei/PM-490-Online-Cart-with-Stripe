package com.pm490.PM490.controller;

import com.pm490.PM490.dto.EmailDto;
import com.pm490.PM490.service.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.MessagingException;
import java.io.IOException;


@RestController
@RequestMapping("/api/email")
public class EmailController {
    @Autowired
    private EmailService emailService;

    @PostMapping(value = "/sendemail")
    public String sendEmail(@RequestBody EmailDto email) throws MessagingException, IOException {
        emailService.sendEmail(email);
        return "Email sent successfully";
    }

}


