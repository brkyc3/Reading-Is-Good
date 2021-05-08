package com.readingisgood.orderservice.repository.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
public class OrderDocumentItem {
    private String bookId;
    private String bookName;
    private int count;
}
