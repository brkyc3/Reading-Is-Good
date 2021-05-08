package com.readingisgood.orderservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;
import org.springframework.transaction.support.TransactionTemplate;

@Configuration
public class Config   {
    @Value("${encoder.secretKey}")
    private String secretKey;

    @Bean
    MongoTransactionManager transactionManager( MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }
    @Bean
    TransactionTemplate transactionTemplate(MongoTransactionManager transactionManager){
        return new TransactionTemplate(transactionManager);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new Pbkdf2PasswordEncoder(secretKey);
    }


}
