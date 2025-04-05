package com.santanna.businessservice.domain.dtos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOwnerDto {
    private Long id;
    private String cpf;
    private String name;
}
