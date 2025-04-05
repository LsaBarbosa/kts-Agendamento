package com.santanna.businessservice.domain.entity;

import com.santanna.businessservice.domain.enums.AppointmentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "appointment")
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associação com o proprietário do negócio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_owner_id", nullable = false)
    private BusinessOwner businessOwner;

    // Data e hora do agendamento
    @Column(nullable = false)
    private LocalDateTime appointmentDateTime;

    // Informações do cliente (nome e telefone)
    @Column(nullable = false)
    private String clientName;

    @Column(nullable = false)
    private String clientPhone;

    // Status do agendamento (ex.: AGENDADO ou CANCELADO)
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AppointmentStatus status;
}
