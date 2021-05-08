package com.readingisgood.orderservice.repository;

import com.readingisgood.orderservice.repository.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CustomerRepository extends MongoRepository<CustomerDocument,String> {
    Optional<CustomerDocument> findCustomerDocumentByEmail(String email);
}
