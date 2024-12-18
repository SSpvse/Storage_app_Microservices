package com.email.emailservice.eventDriven;

import com.email.emailservice.model.DTO.DateDTO;
import com.email.emailservice.service.EmailServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmailEventHandler {

    private final EmailServiceImpl emailService;


    @RabbitListener(queues = "item.date.queue") // SpEL to reference the bean
    public void handleDateItems(List<DateDTO> dateItems) throws InterruptedException {

        Thread.sleep(30000);

        // just to trouble shoot and get list printed for the logs:
        if (dateItems == null || dateItems.isEmpty()) {
            System.out.println(" X_X_X_X_X_(EmailEventHandler)!!  EMPTY LIST IN THE EVENT HANDLER !! ");
        }else {
            System.out.println(" X_X_X_X_X_(EmailEventHandler)!!  LIST IN THE EVENT HANDLER !! ");
            for (DateDTO dateDTO : dateItems) {
                System.out.println("_X_X_X_X_XX_(EmailEventHandler)X   DATEDTO GET ID: " + dateDTO.getUserID());
                System.out.println(dateDTO.getName());
            }
        }
        System.out.println("X_X_X_X_X_(EmailEventHandler)INSIDE EVENT HANDLER, dateItems.size()::::: " + dateItems.size());


        emailService.SendDateItemToEmail(dateItems);


    }



}
