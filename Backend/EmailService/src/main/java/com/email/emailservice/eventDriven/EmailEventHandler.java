package com.email.emailservice.eventDriven;

import com.email.emailservice.dto.NotificationDTO;
import com.email.emailservice.service.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailEventHandler {

    // private final EmailService emailService;

    // Listener for Notification event
    @RabbitListener(queues = "${amqp.queue.name}")
    public void handleNotificationEvent(NotificationDTO notificationDTO) {
        log.info("Received notification with date event: {}", notificationDTO);
        // emailService.handleExpiredItem(notificationDTO);
    }
}







