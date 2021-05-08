package com.readingisgood.customerservice.repository;

import com.readingisgood.customerservice.repository.document.CustomerDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerDocument,String> {
    boolean existsCustomerDocumentByEmail(String email);

}
