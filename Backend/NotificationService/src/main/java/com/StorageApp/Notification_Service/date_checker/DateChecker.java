package com.StorageApp.Notification_Service.date_checker;


import com.StorageApp.Notification_Service.eventdriven.ItemEventPublisher;
import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class DateChecker {

    private final ItemEventPublisher itemEventPublisher;
    private final NotificationRepository notificationRepository;
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;


    public DateChecker(ItemEventPublisher itemEventPublisher, NotificationRepository notificationRepository, RabbitTemplate rabbitTemplate, @Value("${amqp.exchange.name}") String exchangeName) {
        this.itemEventPublisher = itemEventPublisher;
        this.notificationRepository = notificationRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    // this method runs every 24 hours

    @Scheduled(fixedRate = 300000)
    public void checkItems() {

        System.out.println("CHECK IF CHECKS .............");
        LocalDate tomorrowLocalDate = LocalDate.now().plusDays(1);

        List<DateDTO> itemsToSend = notificationRepository.findByDate(tomorrowLocalDate);

        System.out.println("CHECKING TOMORROW DATE AND ITEMSTO SEND LENGTH==== " + itemsToSend.size());
        for (DateDTO item : itemsToSend) {
            itemEventPublisher.testSendRabbit(item);
        }

        /*
        List<TimeItemDto> itemsToSend = notificationRepository.findByName("Rabbit");

        for (TimeItemDto item : itemsToSend) {
            itemEventPublisher.testSendRabbit(item);
        }
         */

    }

}