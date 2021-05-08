package com.readingisgood.orderservice.service.model;


import lombok.Data;


@Data
public class Book {
    private String id;
    private String bookName;
    private int currentCount;
}
