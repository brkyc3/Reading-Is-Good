package com.readingisgood.orderservice.repository;

import com.readingisgood.orderservice.repository.document.CustomerDocument;
import com.readingisgood.orderservice.repository.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface OrderRepository extends MongoRepository<OrderDocument,String> {
    List<OrderDocument> findAllByCustomer(CustomerDocument customer);
}
