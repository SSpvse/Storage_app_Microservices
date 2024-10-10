package com.email.emailservice.eventDriven;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailEventHandler {

    private final EmailServiceImpl emailService;

    /*@Value("${amqp.queue.name}")
    private String queueName;



    @RabbitListener(queues = "item.date.queue") // SpEL to reference the bean
    public void handleDateItems(DateDTO dateItem) throws InterruptedException {
        Thread.sleep(3000);
        // log.info("handleItemDto: {}", dateItem);
        emailService.addDateItemToRepo(dateItem);
    }

     */
}
