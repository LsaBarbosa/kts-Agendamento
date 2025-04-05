package com.santanna.businessservice.domain.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BusinessOwnerCreateDto {
    private String cpf;
    private String name;
    private String password;
}
