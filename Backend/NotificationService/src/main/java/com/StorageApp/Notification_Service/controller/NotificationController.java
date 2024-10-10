package com.StorageApp.Notification_Service.controller;

import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notification")
@Slf4j
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    AmqpTemplate amqpTemplate; // Inject the RabbitMQ template


    // ---- CREATE

// CHAT GTP ---------------VVVVVV
    // test , add to rabbitMQ --- TESTING ------VVV
    private final String exchange = "storage_prj_exchange"; // Your exchange name
    private final String routingKey = "routing_key_storage_prj"; // Your routing key


    @PostMapping("/test")
    public ResponseEntity<DateDTO> testSendRabbit(@RequestBody DateDTO timeItemDto) {

        System.out.println("øøøøøøøøøøøøøøøøøøøø iID ::::" + timeItemDto.getId());

        // Send the message to RabbitMQ
        amqpTemplate.convertAndSend(exchange, routingKey, timeItemDto);

        // Log and return the sent object for confirmation
        log.info("Sent message to RabbitMQ: " + timeItemDto);
        return ResponseEntity.ok(timeItemDto);
    }
// --------------------------------AA

    // add timeDto
    @PostMapping("/addDto")
    public ResponseEntity<DateDTO> addTimeDto(@RequestBody DateDTO timeDto) {

        DateDTO dto = notificationService.addTimeDto(timeDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<DateDTO>> getAllTimeDto() {
        List<DateDTO> dtos = notificationService.getAllTimeDto();
        return new ResponseEntity<>(dtos, HttpStatus.OK);
    }

}
