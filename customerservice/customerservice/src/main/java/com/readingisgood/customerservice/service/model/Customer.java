package com.readingisgood.customerservice.service.model;

import lombok.*;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Customer {
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate birthDate;

}
