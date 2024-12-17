package com.StorageApp.Notification_Service.repository;


import com.StorageApp.Notification_Service.model.DTO.DateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<DateDTO, Long> {

    List<DateDTO> findByDate(@Param("date") LocalDate date);
    void deleteById(Long id);


}
