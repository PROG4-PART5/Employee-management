package com.example.prog4.controller.mapper;

import com.example.prog4.model.Employee;
import com.example.prog4.model.exception.BadRequestException;
import com.example.prog4.repository.CnapsEmployee.CnapsEmployeeRepository;
import com.example.prog4.repository.CnapsEmployee.CnapsPhoneRepository;
import com.example.prog4.repository.CnapsEmployee.CnapsPositionRepository;
import com.example.prog4.repository.CnapsEmployee.entity.CnapsEmployee;
import com.example.prog4.repository.SimpleEmployee.PositionRepository;
import com.example.prog4.repository.SimpleEmployee.entity.Phone;
import com.example.prog4.repository.SimpleEmployee.entity.Position;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
@Transactional
public class EmployeeMapper {
    private PositionRepository positionRepository;
    private PhoneMapper phoneMapper;
    private CnapsPositionRepository cnapsPositionRepository;
    private CnapsEmployeeRepository cnapsEmployeeRepository;
    public CnapsEmployee toCnapsEmployee(com.example.prog4.repository.SimpleEmployee.entity.Employee employee) {
        try {
            List<com.example.prog4.repository.CnapsEmployee.entity.Position> positions = new ArrayList<>();

            List<Position> positionList = employee.getPositions();

            List<com.example.prog4.repository.CnapsEmployee.entity.Position> positionCnaps = positionList.stream().map(
                    position -> com.example.prog4.repository.CnapsEmployee.entity.Position.builder()
                            .id(position.getId())
                            .name(position.getName())
                            .build()
            ).toList();

            positionCnaps.forEach(position -> {
                Optional<com.example.prog4.repository.CnapsEmployee.entity.Position> position1 = cnapsPositionRepository.findPositionByNameEquals(position.getName());
                if (position1.isEmpty()) {
                    positions.add(cnapsPositionRepository.save(position));
                } else {
                    positions.add(position1.get());
                }
            });

            List<com.example.prog4.repository.CnapsEmployee.entity.Phone> phones = employee
                    .getPhones()
                    .stream()
                    .map(phone -> com.example.prog4.repository.CnapsEmployee.entity.Phone.builder()
                            .id(phone.getId())
                            .build()).collect(Collectors.toList());


            System.out.println(employee.getId());
            CnapsEmployee cnapsEmployee = CnapsEmployee.builder()
                    .id(employee.getId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .address(employee.getAddress())
                    .cin(employee.getCin())
//                    .cnaps(employee.getCnaps())
                    .registrationNumber(employee.getRegistrationNumber())
                    .childrenNumber(employee.getChildrenNumber())
                    // enums
                    .csp(employee.getCsp())
                    .sex(employee.getSex())
                    // emails
                    .professionalEmail(employee.getProfessionalEmail())
                    .personalEmail(employee.getPersonalEmail())
                    // dates
                    .birthDate(employee.getBirthDate())
                    .departureDate(employee.getDepartureDate())
                    .entranceDate(employee.getEntranceDate())
                    // lists
                    .phones(phones)
                    .positions(positions)
                    .endToEndId(employee.getId())
                    .build();
            return cnapsEmployee;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public com.example.prog4.repository.SimpleEmployee.entity.Employee toDomain(Employee employee) {
        try {
            List<Position> positions = new ArrayList<>();
            employee.getPositions().forEach(position -> {
                Optional<Position> position1 = positionRepository.findPositionByNameEquals(position.getName());
                if (position1.isEmpty()) {
                    positions.add(positionRepository.save(position));
                } else {
                    positions.add(position1.get());
                }
            });

            List<Phone> phones = employee.getPhones().stream().map((com.example.prog4.model.Phone fromView) -> phoneMapper.toDomain(fromView, employee.getId())).toList();

            com.example.prog4.repository.SimpleEmployee.entity.Employee domainEmployee = com.example.prog4.repository.SimpleEmployee.entity.Employee.builder()
                    .id(employee.getId())
                    .firstName(employee.getFirstName())
                    .lastName(employee.getLastName())
                    .address(employee.getAddress())
                    .cin(employee.getCin())
                    .cnaps(employee.getCnaps())
                    .registrationNumber(employee.getRegistrationNumber())
                    .childrenNumber(employee.getChildrenNumber())
                    // enums
                    .csp(employee.getCsp())
                    .sex(employee.getSex())
                    // emails
                    .professionalEmail(employee.getProfessionalEmail())
                    .personalEmail(employee.getPersonalEmail())
                    // dates
                    .birthDate(employee.getBirthDate())
                    .departureDate(employee.getDepartureDate())
                    .entranceDate(employee.getEntranceDate())
                    // lists
                    .phones(phones)
                    .positions(positions)
                    .build();
            MultipartFile imageFile = employee.getImage();
            if (imageFile != null && !imageFile.isEmpty()) {
                byte[] imageBytes = imageFile.getBytes();
                String base64Image = Base64.getEncoder().encodeToString(imageBytes);
                domainEmployee.setImage("data:image/jpeg;base64," + base64Image);
            }
            return domainEmployee;
        } catch (Exception e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public Employee toView(com.example.prog4.repository.SimpleEmployee.entity.Employee employee) {
        CnapsEmployee cnapsEmployee = cnapsEmployeeRepository.findByEndToEndId(employee.getId());

        return Employee.builder()
                .id(employee.getId())
                .firstName(employee.getFirstName())
                .lastName(employee.getLastName())
                .address(employee.getAddress())
                .cin(employee.getCin())
                .cnaps(cnapsEmployee != null ? cnapsEmployee.getCnaps() : null)
                .registrationNumber(employee.getRegistrationNumber())
                .childrenNumber(employee.getChildrenNumber())
                // enums
                .csp(employee.getCsp())
                .sex(employee.getSex())
                .stringImage(employee.getImage())
                // emails
                .professionalEmail(employee.getProfessionalEmail())
                .personalEmail(employee.getPersonalEmail())
                // dates
                .birthDate(employee.getBirthDate())
                .departureDate(employee.getDepartureDate())
                .entranceDate(employee.getEntranceDate())
                // lists
                .phones(employee.getPhones().stream().map(phoneMapper::toView).toList())
                .positions(employee.getPositions())
                .build();
    }
}
