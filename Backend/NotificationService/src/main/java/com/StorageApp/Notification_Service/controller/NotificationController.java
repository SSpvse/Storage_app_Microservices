package com.StorageApp.Notification_Service.controller;

import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.service.NotificationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notification")
@Slf4j
public class NotificationController {

    @Autowired
    NotificationService notificationService;
    @Autowired
    AmqpTemplate amqpTemplate; // Inject the RabbitMQ template


    // ---- CREATE

// CHAT GTP ---------------VVVVVV
    // test , add to rabbitMQ --- TESTING ------VVV
    private final String exchange = "amqp.exchange.name";
    // Your exchange name
    private final String routingKey = "item.date.bindkey";

    @PostMapping("/test")
    public void testRabbit() {
        String text = "Hello from RabbitMQ!";
        // Send the message to RabbitMQ
        amqpTemplate.convertAndSend(exchange, routingKey, text);

        // Log and return the sent object for confirmation
        log.info("Sent message to RabbitMQ: " + text);
    }
// Your routing key


    @PostMapping("/test1")
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
    @PostMapping("/add")
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
