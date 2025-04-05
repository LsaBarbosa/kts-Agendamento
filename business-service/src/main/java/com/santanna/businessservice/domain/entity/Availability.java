package com.santanna.businessservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "availability")
@NoArgsConstructor
@AllArgsConstructor
public class Availability {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Associação com o proprietário do negócio
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "business_owner_id", nullable = false)
    private BusinessOwner businessOwner;

    // Data e hora disponíveis
    @Column(nullable = false)
    private LocalDateTime availableDateTime;

    // Flag para indicar se o horário está ativo (disponível) ou bloqueado
    @Column(nullable = false)
    private boolean active;
}
