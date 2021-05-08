package com.readingisgood.orderservice.repository.document;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.Objects;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("customers")
public class CustomerDocument {
    @Id
    private String id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private LocalDate birthDate;
}
