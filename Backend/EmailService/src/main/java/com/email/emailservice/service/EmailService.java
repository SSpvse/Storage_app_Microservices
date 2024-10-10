package com.email.emailservice.service;

import com.email.emailservice.model.DTO.DateDTO;
import org.springframework.http.HttpStatus;

interface EmailService {


    HttpStatus SendDateItemToEmail(DateDTO dateDTO);
}
