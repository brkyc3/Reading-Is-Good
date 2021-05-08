package com.readingisgood.orderservice.repository.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Data
@Document("books")
public class StockDocument {
    @Id
    private String id;
    private String bookName;
    private int currentCount;
    @LastModifiedDate
    private LocalDateTime updateDate;
}
