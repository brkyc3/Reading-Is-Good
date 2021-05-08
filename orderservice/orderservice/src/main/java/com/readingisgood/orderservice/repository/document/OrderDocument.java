package com.readingisgood.orderservice.repository.document;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Document("orders")
public class OrderDocument {
    @Version
    private String version;
    @Id
    private String orderId;
    @DBRef(db = "readingisgood")
    private CustomerDocument customer;
    private List<OrderDocumentItem> orderItems;
    private int totalItemCount;
    private OrderStatus orderStatus=OrderStatus.STOCK_CONTROL;
    @LastModifiedDate
    private LocalDateTime updateDate;
}
