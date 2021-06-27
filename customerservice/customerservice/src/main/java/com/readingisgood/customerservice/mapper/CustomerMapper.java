package com.readingisgood.customerservice.mapper;

import com.readingisgood.customerservice.controller.model.LoginRequest;
import com.readingisgood.customerservice.repository.document.CustomerDocument;
import com.readingisgood.customerservice.service.model.Customer;
import com.readingisgood.customerservice.controller.model.SaveCustomerRequest;
import com.readingisgood.customerservice.controller.model.SaveCustomerResponse;
import com.readingisgood.customerservice.service.model.Login;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CustomerMapper {

   
    Customer mapToCustomer(SaveCustomerRequest customer);

    SaveCustomerResponse mapToSaveCustomerResponse(Customer savedCustomer);
    
    CustomerDocument mapToCustomerDocument(Customer customer);

    Customer mapToCustomer(CustomerDocument savedCustomer);

    Login mapToLogin(LoginRequest request);

    CustomerDocument mapToCustomerDocument(Login login);

}
