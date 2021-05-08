package com.readingisgood.orderservice.controller.model;


import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class OrderRequest {
    @NotNull(message = "Customer id cannot be null!")
    private String customerId;
    @NotNull(message = "OrderItems cannot be null!")
    @Valid
    private List<OrderRequestItem> orderItems;

}
