package com.email.emailservice.service;

import com.email.emailservice.model.DTO.DateDTO;

import java.util.List;

interface EmailService {


    void SendDateItemToEmail(List<DateDTO> dateDTOS);
}
