package com.readingisgood.orderservice.config.security;

import com.readingisgood.orderservice.repository.CustomerRepository;
import com.readingisgood.orderservice.repository.document.CustomerDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoUserDetailService implements UserDetailsService
{
    private final CustomerRepository repository;
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<CustomerDocument> customer = repository.findCustomerDocumentByEmail(email);
        if(customer.isEmpty()){
            throw new UsernameNotFoundException("User not found");
        }

        return UserDetailsImpl.builder()
                .email(customer.get().getEmail())
                .password(customer.get().getPassword())
                .build();
    }
}
