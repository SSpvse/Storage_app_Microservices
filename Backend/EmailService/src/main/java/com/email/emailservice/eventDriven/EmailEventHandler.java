package com.email.emailservice.eventDriven;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailEventHandler {

    private final EmailServiceImpl emailService;

    @Value("${amqp.queue.name}")
    private String queueName;



    @RabbitListener(queues = "item.date.queue") // SpEL to reference the bean
    public void handleDateItems(List<DateDTO> dateItems) throws InterruptedException {
        Thread.sleep(10000);
        // log.info("handleItemDto: {}", dateItems);
        emailService.addDateItemToRepo(dateItems);
        emailService.SendDateItemToEmail(dateItems);
    }


}
