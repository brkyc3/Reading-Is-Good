package com.readingisgood.customerservice.service;

import com.readingisgood.customerservice.mapper.CustomerMapper;
import com.readingisgood.customerservice.repository.CustomerRepository;
import com.readingisgood.customerservice.repository.document.CustomerDocument;
import com.readingisgood.customerservice.service.jwt.JwtUtil;
import com.readingisgood.customerservice.service.model.Customer;
import com.readingisgood.customerservice.service.model.Login;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private static final Logger logger = LoggerFactory.getLogger(CustomerService.class);
    private final CustomerRepository repository;
    private final CustomerMapper mapper;
    private final PasswordEncoder encoder;
    private final JwtUtil jwtUtil;


    public Customer saveCustomer(Customer customer) {
        logger.info("saveCustomer service {}",customer.getEmail());
        customer.setPassword(encoder.encode(customer.getPassword()));
        if(repository.existsCustomerDocumentByEmail(customer.getEmail()))
            throw new IllegalArgumentException(String.format("Customer with given email address '%s' already exists",customer.getEmail()));

        logger.info("saveCustomer saving customer {}",customer.getEmail());
        CustomerDocument savedCustomer = repository.save(mapper.mapToCustomerDocument(customer));
        logger.info("saveCustomer customerSaved {}",customer.getEmail());

        return mapper.mapToCustomer(savedCustomer);
    }

    public String login(Login login) {
        CustomerDocument customer = repository.findCustomerDocumentByEmail(login.getEmail());
        if(encoder.matches(login.getPassword(),customer.getPassword()))
            return jwtUtil.createJWT(login.getEmail());
        else
            throw new IllegalArgumentException("Customer email or password not valid!");

    }
}
