package com.StorageApp.Notification_Service.eventdriven;

import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.model.DTO.RabbitDateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ItemEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey = "item.date.bindkey"; // routing key

    public ItemEventPublisher(RabbitTemplate rabbitTemplate, @Value("${amqp.exchange.name}") String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }

    public void sendItemListToRabbit(List<DateDTO> dateDTOList) {
        // Log the incoming list of DateDTO objects
        log.info("Received item list for publishing: {}", dateDTOList);

        if (dateDTOList == null || dateDTOList.isEmpty()) {
            log.error("The list of DateDTO is null or empty!");
            throw new IllegalArgumentException("The list is null or empty.");
        }

        List<RabbitDateDTO> rabbitList = new ArrayList<>();

        // Transform DateDTO to RabbitDateDTO and log the transformation
        for (DateDTO dateDTO : dateDTOList) {

            RabbitDateDTO rabbitDateDTO = new RabbitDateDTO();
            rabbitDateDTO.setName(dateDTO.getName());
            rabbitDateDTO.setDate(dateDTO.getDate().toString());
            rabbitDateDTO.setUnitID(dateDTO.getUnitID());
            rabbitDateDTO.setUserID(dateDTO.getUserID());


            System.out.println("X_X_X_X_X_X__X_X_X INSIDE THE FORLOOOP RABBIT NAME/USERID/ dateDTO USER ID BEFORE  " + rabbitDateDTO.getName() + " / " + rabbitDateDTO.getUserID() + " / " + dateDTO.getUnitID());
            rabbitList.add(rabbitDateDTO);
            log.debug("Transformed DateDTO to RabbitDateDTO: {}", rabbitDateDTO);
        }

        // Group the RabbitDateDTOs by unitID (userID)
        Map<Long, List<RabbitDateDTO>> groupedItems = groupListByUserID(rabbitList);

        // Log the grouped items before sending to RabbitMQ
        log.info("Grouped items by userID: {}", groupedItems);

        // Iterate through each group and send it as a separate message
        for (Map.Entry<Long, List<RabbitDateDTO>> user_id : groupedItems.entrySet()) {
            Long userID = user_id.getKey();
            List<RabbitDateDTO> userItems = user_id.getValue();

            // Log the user-specific items before sending them
            log.info("Sending items for userID {}: {}", userID, userItems);

            // Send grouped list to RabbitMQ
            rabbitTemplate.convertAndSend(exchangeName, routingKey, userItems);
        }

        // Log the completion of the message sending process
        log.info("Successfully sent grouped messages to RabbitMQ for user-specific groups.");
        ResponseEntity.ok(dateDTOList);
    }

    private Map<Long, List<RabbitDateDTO>> groupListByUserID(List<RabbitDateDTO> rabbitList) {
        // Log before grouping
        log.debug("Grouping RabbitDateDTO list by userID...");

        Map<Long, List<RabbitDateDTO>> groupedItems = rabbitList.stream()
                .collect(Collectors.groupingBy(RabbitDateDTO::getUnitID)); // Group by unitID (userID)

        // Log after grouping
        log.debug("Grouped RabbitDateDTOs by userID: {}", groupedItems);

        return groupedItems;
    }
}
