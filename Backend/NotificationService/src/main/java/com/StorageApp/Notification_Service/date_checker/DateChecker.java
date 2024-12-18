package com.StorageApp.Notification_Service.date_checker;


import com.StorageApp.Notification_Service.eventdriven.ItemEventPublisher;
import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.repository.NotificationRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;


@Service
public class DateChecker {

    private final ItemEventPublisher itemEventPublisher;
    private final NotificationRepository notificationRepository;

    @Autowired
    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;


    public DateChecker(ItemEventPublisher itemEventPublisher, NotificationRepository notificationRepository, RabbitTemplate rabbitTemplate, @Value("${amqp.exchange.name}") String exchangeName) {
        this.itemEventPublisher = itemEventPublisher;
        this.notificationRepository = notificationRepository;
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    // this method should run every 24 hours
    // but for testing i made it 60 seconds

    @Scheduled(fixedRate = 60000)
    public void checkItems() {

        System.out.println("CHECK IF CHECKS (ItemEventPublisher, checkItems()  ) .............");
        LocalDate tomorrowLocalDate = LocalDate.now().plusDays(1);

        System.out.println("HERE IS THE Tomorrow Date: " + tomorrowLocalDate);

        List<DateDTO> itemsToSend = notificationRepository.findByDate(tomorrowLocalDate);

        for (DateDTO dateDTO : itemsToSend) {
            System.out.println("X_X_X_X_X_XX_(DateChecker)X   DATEDTO GET ID: " + dateDTO.getUserID());
            System.out.println("X__X_X_X_X getname: "+dateDTO.getName());
        }

        System.out.println("CHECKING TOMORROW DATE AND ITEMS TO SEND list.size()LENGTH==== " + itemsToSend.size());
        System.out.println("AND DATATYPE IS : ~ : ~ :" + itemsToSend.getClass().getName());

        itemEventPublisher.sendItemListToRabbit(itemsToSend);

    }

}
