package com.readingisgood.orderservice.service.model;

import lombok.Data;

@Data
public class OrderItem {
    private String bookId;
    private String bookName;
    private int count;
}
