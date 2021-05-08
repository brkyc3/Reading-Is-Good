package com.readingisgood.orderservice.controller.model;


import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Data
public class SaveStockRequest {
    private String id;
    @NotNull
    private String bookName;
    @Min(1)
    @NotNull
    private int currentCount;
}
