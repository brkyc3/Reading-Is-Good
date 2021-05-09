package com.readingisgood.orderservice.service;


import com.readingisgood.orderservice.exception.ErrorCode;
import com.readingisgood.orderservice.exception.NotFoundException;
import com.readingisgood.orderservice.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final Logger logger = LoggerFactory.getLogger( CustomerService.class);

    private final CustomerRepository customerRepository;

    public void checkCustomerExists(String customerId) {
        logger.info("checking if customer exists for customerId {}",customerId);
        if(customerRepository.findById(customerId).isEmpty())
            throw new NotFoundException(ErrorCode.RISGOOD_CUSTOMER_NOT_FOUND,String.format("Customer cannot be found with customerId '%s'",customerId));
    }
}
