package com.santanna.businessservice.infra.repo;

import com.santanna.businessservice.domain.entity.Appointment;
import com.santanna.businessservice.domain.entity.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByBusinessOwner(BusinessOwner businessOwner);
}