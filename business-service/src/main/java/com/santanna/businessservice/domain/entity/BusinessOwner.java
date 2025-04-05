package com.santanna.businessservice.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Table(name = "business_owner")
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOwner {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String password; // Em produção, deve ser armazenada de forma criptografada

    @Column(nullable = false)
    private String name;
}
