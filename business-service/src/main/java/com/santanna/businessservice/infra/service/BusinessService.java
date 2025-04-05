package com.santanna.businessservice.infra.service;

import com.santanna.businessservice.domain.dtos.AppointmentDto;
import com.santanna.businessservice.domain.dtos.AvailabilityDto;
import com.santanna.businessservice.domain.dtos.BusinessOwnerCreateDto;
import com.santanna.businessservice.domain.dtos.BusinessOwnerDto;
import com.santanna.businessservice.domain.entity.Appointment;
import com.santanna.businessservice.domain.entity.Availability;
import com.santanna.businessservice.domain.entity.BusinessOwner;
import com.santanna.businessservice.domain.enums.AppointmentStatus;
import com.santanna.businessservice.infra.repo.AppointmentRepository;
import com.santanna.businessservice.infra.repo.AvailabilityRepository;
import com.santanna.businessservice.infra.repo.BusinessOwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BusinessService {

    private final BusinessOwnerRepository businessOwnerRepository;
    private final AppointmentRepository appointmentRepository;
    private final AvailabilityRepository availabilityRepository;

    @Autowired
    public BusinessService(BusinessOwnerRepository businessOwnerRepository, AppointmentRepository appointmentRepository, AvailabilityRepository availabilityRepository) {
        this.businessOwnerRepository = businessOwnerRepository;
        this.appointmentRepository = appointmentRepository;
        this.availabilityRepository = availabilityRepository;
    }

    /**
     * Cria um novo BusinessOwner a partir do DTO de criação.
     *
     * @param createDto DTO contendo CPF, nome e senha.
     * @return BusinessOwnerDto com os dados do proprietário criado.
     * @throws Exception se já existir um proprietário com o mesmo CPF.
     */
    public BusinessOwnerDto createBusinessOwner(BusinessOwnerCreateDto createDto) throws Exception {
        var owner = businessOwnerRepository.findByCpf(createDto.getCpf());
        if (owner.isPresent()) throw new Exception("CPF já cadastrado no sistema");

        var businessOwner = new BusinessOwner();
        businessOwner.setCpf(createDto.getCpf());
        businessOwner.setName(createDto.getName());
        businessOwner.setPassword(createDto.getPassword()); // Em produção, a senha deve ser criptografada!

        BusinessOwner savedOwner = businessOwnerRepository.save(businessOwner);
        return convertBusinessOwnerToDto(savedOwner);
    }

    /**
     * Valida o login do proprietário utilizando CPF e senha.
     *
     * @param cpf      CPF do proprietário.
     * @param password Senha informada.
     * @return BusinessOwner autenticado.
     * @throws Exception se CPF não for encontrado ou senha for inválida.
     */

    public BusinessOwnerDto login(String cpf, String password) throws Exception {
        var businessOwner = businessOwnerRepository.findByCpf(cpf);
        if (businessOwner.isPresent()) {
            var owner = businessOwner.get();
            if (owner.getPassword().equals(password)) {
                return convertBusinessOwnerToDto(owner);
            } else {
                throw new Exception("Usuario ou senha inválidos");
            }
        } else {
            throw new Exception("Cpf não cadastrado no sistema");
        }
    }

    public List<AvailabilityDto> setAvailableDates(Long businessOwnerId, List<AvailabilityDto> availabilitiesDto) throws Exception {
        var businessOwner = businessOwnerRepository.findById(businessOwnerId).orElseThrow(() -> new Exception("Proprietário não encontrado"));

        List<Availability> availabilities = availabilitiesDto.stream().map(dto -> {
            var availability = new Availability();
            availability.setAvailableDateTime(dto.getAvailableDateTime());
            availability.setActive(dto.isActive());
            availability.setBusinessOwner(businessOwner);
            return availability;
        }).toList();

        List<Availability> savedAvailabilities = availabilityRepository.saveAll(availabilities);
        return savedAvailabilities.stream().map(this::convertAvailabilityToDto).collect(Collectors.toList());
    }

    public AppointmentDto cancelAppointment(Long appointmentId) throws Exception {
        var appointmentOpt = appointmentRepository.findById(appointmentId);
        if (appointmentOpt.isEmpty()) {
            throw new Exception("Agendamento não encontrado");
        }
        var appointment = appointmentOpt.get();
        appointment.setStatus(AppointmentStatus.CANCELADO);
        var updatedAppointment = appointmentRepository.save(appointment);
        return convertAppointmentToDto(updatedAppointment);
    }

    // Métodos de conversão para DTOs

    private BusinessOwnerDto convertBusinessOwnerToDto(BusinessOwner owner) {
        BusinessOwnerDto dto = new BusinessOwnerDto();
        dto.setId(owner.getId());
        dto.setCpf(owner.getCpf());
        dto.setName(owner.getName());
        return dto;
    }

    private AppointmentDto convertAppointmentToDto(Appointment appointment) {
        AppointmentDto dto = new AppointmentDto();
        dto.setId(appointment.getId());
        dto.setBusinessOwnerId(appointment.getBusinessOwner().getId());
        dto.setAppointmentDateTime(appointment.getAppointmentDateTime());
        dto.setClientName(appointment.getClientName());
        dto.setClientPhone(appointment.getClientPhone());
        dto.setStatus(appointment.getStatus());
        return dto;
    }

    private AvailabilityDto convertAvailabilityToDto(Availability availability) {
        AvailabilityDto dto = new AvailabilityDto();
        dto.setId(availability.getId());
        dto.setBusinessOwnerId(availability.getBusinessOwner().getId());
        dto.setAvailableDateTime(availability.getAvailableDateTime());
        dto.setActive(availability.isActive());
        return dto;
    }
}
