package com.readingisgood.orderservice.repository;

import com.readingisgood.orderservice.repository.document.StockDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface StockRepository extends MongoRepository<StockDocument,String> {
}
