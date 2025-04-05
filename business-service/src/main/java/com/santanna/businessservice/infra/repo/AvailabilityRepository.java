package com.santanna.businessservice.infra.repo;

import com.santanna.businessservice.domain.entity.Availability;
import com.santanna.businessservice.domain.entity.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface AvailabilityRepository extends JpaRepository<Availability, Long> {
    List<Availability> findByBusinessOwnerAndActiveTrue(BusinessOwner businessOwner);
}