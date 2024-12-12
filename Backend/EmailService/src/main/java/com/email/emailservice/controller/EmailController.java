package com.email.emailservice.controller;


import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.service.EmailServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/email")
public class EmailController {


    @Autowired
    EmailServiceImpl emailService;

    @PostMapping
    public void sendMail(@RequestBody DateDTO text) {

        // emailService.SendDateItemToEmail(text);

    }


}
