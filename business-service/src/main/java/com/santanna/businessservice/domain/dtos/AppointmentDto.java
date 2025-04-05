package com.santanna.businessservice.domain.dtos;

import com.santanna.businessservice.domain.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDto {
    private Long id;
    private Long businessOwnerId;
    private LocalDateTime appointmentDateTime;
    private String clientName;
    private String clientPhone;
    private AppointmentStatus status;
}
