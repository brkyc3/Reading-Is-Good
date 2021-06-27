package com.readingisgood.customerservice.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@NoArgsConstructor
public class LoginRequest {
    @NotNull
    private String email;
    @NotNull
    @ToString.Exclude
    private String password;
}
