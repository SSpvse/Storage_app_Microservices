package com.StorageApp.Notification_Service.eventdriven;

import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;


@Slf4j
@Service
public class ItemEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey = "routing_key_storage_prj"; // Your routing key


    public ItemEventPublisher(RabbitTemplate rabbitTemplate, @Value("${amqp.exchange.name}")String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }




    public ResponseEntity<DateDTO> testSendRabbit(@RequestBody DateDTO timeItemDto) {

        System.out.println("TESTING FOR testSendRabbit method::: " + timeItemDto);

        if (timeItemDto == null) {
            throw new IllegalArgumentException("TimeItemDto cannot be null");
        }
        // Send the message to RabbitMQ
        rabbitTemplate.convertAndSend(exchangeName, routingKey, timeItemDto);

        // Log and return the sent object for confirmation
        log.info("Sent message to RabbitMQ: " + timeItemDto);
        return ResponseEntity.ok(timeItemDto);
    }
}
