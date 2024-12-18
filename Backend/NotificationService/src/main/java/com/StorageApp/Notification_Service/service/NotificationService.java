package com.StorageApp.Notification_Service.service;


import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import com.StorageApp.Notification_Service.repository.NotificationRepository;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@NoArgsConstructor
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;


    @Transactional
    public DateDTO addTimeDto(DateDTO timeItemDto) {
        if (timeItemDto.getDate() == null) {
            System.out.println("No date on the item... nothing to store");
            return null;
        }else {
            if (timeItemDto.getId() == null) {
                System.out.println("No id on the item... nothing to store");
                return null;
            }else {
                System.out.println("Saving the item to the database : :: : : :"+ timeItemDto.getName() + "and" + timeItemDto.getDate());
                notificationRepository.save(timeItemDto);
                return timeItemDto;
            }
        }
    }

    @Transactional
    public List<DateDTO> getAllTimeDto() {

        List<DateDTO> timeItemDtoList = new ArrayList<>();
        notificationRepository.findAll().forEach(timeItemDto -> timeItemDtoList.add(timeItemDto));
        return timeItemDtoList;
    }


    @Transactional
    public void deleteTimeDto(Long id) {
        notificationRepository.deleteById(id);
    }
}
