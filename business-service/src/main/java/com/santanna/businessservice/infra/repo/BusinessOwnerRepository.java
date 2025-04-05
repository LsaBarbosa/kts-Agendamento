package com.santanna.businessservice.infra.repo;

import com.santanna.businessservice.domain.entity.BusinessOwner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BusinessOwnerRepository extends JpaRepository<BusinessOwner, Long> {
    Optional<BusinessOwner> findByCpf(String cpf);
}