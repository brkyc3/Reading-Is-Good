package com.readingisgood.orderservice.controller.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OrderListResponseItem {
    private String orderId;
    @JsonFormat(pattern="dd-MM-yyyy hh:mm:ss")
    private LocalDateTime updateDate;
    private int totalItemCount;
}
