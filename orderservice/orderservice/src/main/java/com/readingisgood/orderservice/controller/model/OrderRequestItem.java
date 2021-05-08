package com.readingisgood.orderservice.controller.model;

import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class OrderRequestItem {
    @NotNull
    private String bookId;
    private String bookName;
    @Min(1)
    @Max(10)
    @NotNull
    private int count;
}
