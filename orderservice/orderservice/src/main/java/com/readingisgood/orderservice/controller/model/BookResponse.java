package com.readingisgood.orderservice.controller.model;

import lombok.Data;


@Data
public class BookResponse {
    private String id;
    private String bookName;
    private int currentCount;
}
