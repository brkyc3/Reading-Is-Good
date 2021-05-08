package com.readingisgood.orderservice.controller.model;


import com.readingisgood.orderservice.repository.document.OrderStatus;
import com.readingisgood.orderservice.service.model.OrderItem;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class OrderDetailsResponse {
    private String orderId;
    private String customerId;
    private List<OrderItem> orderItems;
    private LocalDateTime updateDate;
    private int totalItemCount;
    private OrderStatus orderStatus;
}
