package com.email.emailservice.repository;

import com.email.emailservice.model.DTO.DateDTO;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ReceiverRepository extends JpaRepository<DateDTO, Long> {


}
