package com.readingisgood.customerservice.controller;

import com.readingisgood.customerservice.controller.model.LoginRequest;
import com.readingisgood.customerservice.controller.model.LoginResponse;
import com.readingisgood.customerservice.service.model.Customer;
import com.readingisgood.customerservice.controller.model.SaveCustomerRequest;
import com.readingisgood.customerservice.controller.model.SaveCustomerResponse;
import com.readingisgood.customerservice.mapper.CustomerMapper;
import com.readingisgood.customerservice.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/customers")
@RequiredArgsConstructor
public class CustomerController {

    private static final Logger logger = LoggerFactory.getLogger(CustomerController.class);
    private final CustomerService service;
    private final CustomerMapper mapper;


    @PostMapping("/signup")
    public SaveCustomerResponse saveCustomer(@RequestBody @Validated SaveCustomerRequest customer){
        logger.info("saveCustomerRequest : {} ", customer);
        try {
            logger.info("saveCustomerRequest  password encoded {}",customer.getEmail());
            Customer savedCustomer = service.saveCustomer(mapper.mapToCustomer(customer));
            return mapper.mapToSaveCustomerResponse(savedCustomer);
        }catch (Exception e){
            logger.error("Exception occurred while saving customer ",e);
            throw e;
        }
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody @Validated LoginRequest loginRequest){
        logger.info("loginRequest : {} ", loginRequest);
        try {
            String token = service.login(mapper.mapToLogin(loginRequest));
            return new LoginResponse(token);
        }catch (Exception e){
            logger.error("Exception occurred while login ",e);
            throw e;
        }
    }

}
