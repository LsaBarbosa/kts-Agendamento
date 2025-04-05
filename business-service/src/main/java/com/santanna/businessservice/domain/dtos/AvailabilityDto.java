package com.santanna.businessservice.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AvailabilityDto {
    private Long id;
    private Long businessOwnerId;
    private LocalDateTime availableDateTime;
    private boolean active;
}
