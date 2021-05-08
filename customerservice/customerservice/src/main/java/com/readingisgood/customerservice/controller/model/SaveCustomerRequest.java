package com.readingisgood.customerservice.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
public class SaveCustomerRequest {
    @NotBlank
    @NotNull
    @Size(max = 20)
    private String name;
    @NotBlank
    @NotNull
    @Size(max = 20)
    private String surname;
    @NotBlank
    @NotNull
    @Email
    private String email;
    @Size(min = 6,max = 12)
    @NotNull
    @NotBlank
    @ToString.Exclude
    private String password;
    @Past
    @NotNull
    @JsonFormat(pattern="dd-MM-yyyy")
    private LocalDate birthDate;

}
