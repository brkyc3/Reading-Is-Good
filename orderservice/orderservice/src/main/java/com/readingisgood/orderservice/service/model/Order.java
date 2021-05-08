package com.readingisgood.orderservice.service.model;

import com.readingisgood.orderservice.repository.document.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    private String orderId;
    private String customerId;
    private List<OrderItem> orderItems;
    private LocalDateTime updateDate;
    private int totalItemCount;
    private OrderStatus orderStatus;
}
