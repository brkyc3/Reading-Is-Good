package com.readingisgood.orderservice.authentication;

import com.readingisgood.orderservice.repository.CustomerRepository;
import com.readingisgood.orderservice.repository.document.CustomerDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class MongoAuthenticationProvider implements AuthenticationProvider {
    private final CustomerRepository repository;
    private final PasswordEncoder encoder;
    @Override
    public Authentication authenticate(Authentication auth){

        String email = auth.getName();
        String password = auth.getCredentials().toString();
        Optional<CustomerDocument> customer = repository.findCustomerDocumentByEmail(email);
        if (customer.isPresent() &&  encoder.matches(password,customer.get().getPassword())) {
            return new UsernamePasswordAuthenticationToken
                    (email, password, Collections.emptyList());
        } else {
            throw new
                    BadCredentialsException("Authentication failed");
        }
    }

    @Override
    public boolean supports(Class<?> auth) {
        return auth.equals(UsernamePasswordAuthenticationToken.class);
    }
}