package com.readingisgood.customerservice.repository.document;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@NoArgsConstructor
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
