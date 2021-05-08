package com.readingisgood.customerservice.controller.model;

import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
public class SaveCustomerResponse {
    private String id;
    private String name;
    private String surname;

}
