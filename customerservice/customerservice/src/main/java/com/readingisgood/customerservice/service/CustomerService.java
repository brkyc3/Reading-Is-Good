package com.readingisgood.customerservice.service;

import com.readingisgood.customerservice.mapper.CustomerMapper;
import com.readingisgood.customerservice.repository.CustomerRepository;
import com.readingisgood.customerservice.repository.document.CustomerDocument;
import com.readingisgood.customerservice.service.model.Customer;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository repository;
    private final CustomerMapper mapper;




    public Customer saveCustomer(Customer customer) {
        logger.info("saveCustomer service {}",customer.getEmail());

        if(repository.existsCustomerDocumentByEmail(customer.getEmail()))
            throw new IllegalArgumentException(String.format("Customer with given email address '%s' already exists",customer.getEmail()));

        logger.info("saveCustomer saving customer {}",customer.getEmail());
        CustomerDocument savedCustomer = repository.save(mapper.mapToCustomerDocument(customer));
        logger.info("saveCustomer customerSaved {}",customer.getEmail());

        return mapper.mapToCustomer(savedCustomer);
    }
}
