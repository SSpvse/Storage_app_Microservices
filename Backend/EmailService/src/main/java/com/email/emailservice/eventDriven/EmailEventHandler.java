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
        Thread.sleep(30000);



        if (dateItems == null || dateItems.isEmpty()) {
            System.out.println(" !!  EMPTY LIST IN THE EVENT HANDLER !! ");
        }else {
            System.out.println(" !!  LIST IN THE EVENT HANDLER !! ");
            for (DateDTO dateDTO : dateItems) {
                System.out.println(dateDTO.getName());

            }
        }
        System.out.println("INSIDE EVENT HANDLER, HERE ARE DATA ITEMS::::: " + dateItems.size());
        // log.info("handleItemDto: {}", dateItems);
       // emailService.addDateItemToRepo(dateItems);
       emailService.SendDateItemToEmail(dateItems);
    }




/*
    @RabbitListener(queues = "item.date.queue") // SpEL to reference the bean
    public void handleDateItems(String text) throws InterruptedException {
        Thread.sleep(10000);

        if (text == null) {
            System.out.println(" !!  EMPTY LIST IN THE EVENT HANDLER !! ");
        }
        System.out.println("INSIDE EVENT HANDLER, HERE ARE DATA ITEMS::::: " + text);
        // log.info("handleItemDto: {}", dateItems);
        //emailService.addDateItemToRepo(dateItems);
        //emailService.SendDateItemToEmail(dateItems);
    }

 */
}
