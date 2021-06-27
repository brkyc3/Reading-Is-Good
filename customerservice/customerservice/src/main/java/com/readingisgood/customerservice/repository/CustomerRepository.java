package com.readingisgood.customerservice.repository;

import com.readingisgood.customerservice.repository.document.CustomerDocument;
import com.readingisgood.customerservice.service.model.Customer;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CustomerRepository extends MongoRepository<CustomerDocument,String> {
    boolean existsCustomerDocumentByEmail(String email);
    CustomerDocument findCustomerDocumentByEmail(String email);


}
