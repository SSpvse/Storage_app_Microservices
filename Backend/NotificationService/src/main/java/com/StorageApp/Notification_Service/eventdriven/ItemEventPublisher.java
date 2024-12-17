package com.StorageApp.Notification_Service.eventdriven;

import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.model.DTO.RabbitDateDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Slf4j
@Service
public class ItemEventPublisher {

    private final RabbitTemplate rabbitTemplate;
    private final String exchangeName;
    private final String routingKey = "item.date.bindkey"; // Your routing key


    public ItemEventPublisher(RabbitTemplate rabbitTemplate, @Value("${amqp.exchange.name}")String exchangeName) {
        this.rabbitTemplate = rabbitTemplate;
        this.exchangeName = exchangeName;
    }


//    public void sendItemListToRabbit(@RequestBody List<DateDTO> dateDTOList) {
//
//        System.out.println("TESTING FOR testSendRabbit method::: " + dateDTOList);
//
//        if (dateDTOList == null||dateDTOList.isEmpty()) {
//            throw new IllegalArgumentException(" : ~ : ~ : testSendRabbit list is null or empty! ");
//        }
//        List<RabbitDateDTO> rabbitList = new ArrayList<>();
//       //  RabbitDateDTO dto;
//        for (DateDTO dateDTO : dateDTOList) {
//            rabbitList.add(new RabbitDateDTO(dateDTO.getId(), dateDTO.getName(), dateDTO.getDate().toString(),dateDTO.getUnitID()));
//            System.out.println("PRINTING DTO IN ITEMEVENTPUBLISHER : ~ : ~ :" +dateDTO.getName() + dateDTO.getDate().toString());
//        }
//        // Send the message to RabbitMQ
//        rabbitTemplate.convertAndSend(exchangeName, routingKey, rabbitList);
//
//        // Log and return the sent object for confirmation
//        log.info(": ~  ~ : Sent message to RabbitMQ: " + dateDTOList);
//        ResponseEntity.ok(dateDTOList);
//    }




    public void sendItemListToRabbit(@RequestBody List<DateDTO> dateDTOList) {

        System.out.println("TESTING FOR testSendRabbit method::: " + dateDTOList);

        if (dateDTOList == null||dateDTOList.isEmpty()) {
            throw new IllegalArgumentException(" : ~ : ~ : testSendRabbit list is null or empty! ");
        }
        List<RabbitDateDTO> rabbitList = new ArrayList<>();


        //  RabbitDateDTO dto;
        for (DateDTO dateDTO : dateDTOList) {
            rabbitList.add(new RabbitDateDTO(dateDTO.getId(), dateDTO.getName(), dateDTO.getDate().toString(),dateDTO.getUnitID()));
            System.out.println("PRINTING DTO IN ITEMEVENTPUBLISHER : ~ : ~ :" +dateDTO.getName() + dateDTO.getDate().toString());
        }
            Map<Long, List<RabbitDateDTO>> groupedItems = groupListByUserID(rabbitList);

        // Iterate through each group and send it as a separate message
        for (Map.Entry<Long, List<RabbitDateDTO>> entry : groupedItems.entrySet()) {
            Long userID = entry.getKey();
            List<RabbitDateDTO> userItems = entry.getValue();

            System.out.println("Sending items for userID: " + userID + " -> Items: " + userItems);

            // Send grouped list to RabbitMQ
            rabbitTemplate.convertAndSend(exchangeName, routingKey, userItems);
        }

        // Log and return the sent object for confirmation
        log.info(": ~  ~ : Sent message to RabbitMQ: " + dateDTOList);
        ResponseEntity.ok(dateDTOList);
    }


    private Map<Long, List<RabbitDateDTO>> groupListByUserID(List<RabbitDateDTO> rabbitList) {

        Map <Long, List<RabbitDateDTO>> groupedItems = rabbitList.stream()
                .collect(Collectors.groupingBy(RabbitDateDTO::getUnitID)); // Group by unitID (userID)
        return groupedItems;
    }

}
