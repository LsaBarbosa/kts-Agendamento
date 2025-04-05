package com.santanna.businessservice.controller;

import com.santanna.businessservice.domain.dtos.AppointmentDto;
import com.santanna.businessservice.domain.dtos.AvailabilityDto;
import com.santanna.businessservice.domain.dtos.BusinessOwnerCreateDto;
import com.santanna.businessservice.domain.dtos.BusinessOwnerDto;
import com.santanna.businessservice.infra.service.BusinessService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business")
public class BusinessController {

    private final BusinessService businessService;

    @Autowired
    public BusinessController(BusinessService businessService) {
        this.businessService = businessService;
    }

    /**
     * Endpoint para registrar um novo BusinessOwner.
     *
     * Exemplo de chamada: POST /api/v1/business/register
     * Corpo da requisição (JSON):
     * {
     *   "cpf": "12345678900",
     *   "name": "Nome do Proprietário",
     *   "password": "senha123"
     * }
     */
    @PostMapping("/register")
    public BusinessOwnerDto register(@RequestBody BusinessOwnerCreateDto createDto) throws Exception {
        return businessService.createBusinessOwner(createDto);
    }

    /**
     * Endpoint para login do proprietário utilizando CPF e senha.
     *
     * Exemplo de chamada: POST /api/v1/business/login?cpf=12345678900&password=senha123
     */
    @PostMapping("/login")
    public BusinessOwnerDto login(@RequestParam String cpf, @RequestParam String password) throws Exception {
        return businessService.login(cpf, password);
    }

    /**
     * Endpoint para definir as datas disponíveis para agendamento.
     *
     * Exemplo de chamada: POST /api/v1/business/availability?businessOwnerId=1
     * Corpo da requisição: JSON representando uma lista de AvailabilityDto.
     */
    @PostMapping("/availability")
    public List<AvailabilityDto> setAvailability(@RequestParam Long businessOwnerId,
                                                 @RequestBody List<AvailabilityDto> availabilityDtos) throws Exception {
        return businessService.setAvailableDates(businessOwnerId, availabilityDtos);
    }

    /**
     * Endpoint para cancelar um agendamento.
     *
     * Exemplo de chamada: PUT /api/v1/business/appointments/10/cancel
     */
    @PutMapping("/appointments/{appointmentId}/cancel")
    public AppointmentDto cancelAppointment(@PathVariable Long appointmentId) throws Exception {
        return businessService.cancelAppointment(appointmentId);
    }
}
