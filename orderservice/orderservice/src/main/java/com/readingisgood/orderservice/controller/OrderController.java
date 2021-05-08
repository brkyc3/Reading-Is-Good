package com.readingisgood.orderservice.controller;


import com.readingisgood.orderservice.controller.model.*;
import com.readingisgood.orderservice.mapper.OrderMapper;
import com.readingisgood.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private static final Logger logger = LoggerFactory.getLogger( OrderController.class);
    private final OrderService service;
    private final OrderMapper mapper;

    @PostMapping
    public OrderResponse saveOrder(@RequestBody @Valid OrderRequest request){
        logger.info("saveOrder request {}",request);
        try {
            return new OrderResponse(service.saveOrder(mapper.mapToOrder(request)));
        }catch (Exception e){
            logger.error("Error occurred while saving order {} ",request,e);
            throw e;
        }
    }

    @PostMapping("/stock")
    public SaveStockResponse saveStock(@RequestBody @Valid SaveStockRequest request){
        logger.info("saveStock request {}",request);
        try {
            return new SaveStockResponse(service.saveStock(mapper.mapToBook(request)));
        }catch (Exception e){
            logger.error("Error occurred while saving stock {} ",request,e);
            throw e;
        }
    }

    @GetMapping("/customer/{customerId}")
    public List<OrderListResponseItem> listOrdersOfCustomer(@PathVariable("customerId") String customerId){
        logger.info("listOrdersOfCustomer request {}",customerId);
        try {
            return mapper.mapToOrderResponseList(service.findAllOrdersByCustomerId(customerId));
        }catch (Exception e){
            logger.error("Error occurred while listing orders for customer  {} ",customerId,e);
            throw e;
        }
    }

    @GetMapping("/{orderId}")
    public OrderDetailsResponse getOrderDetails(@PathVariable("orderId") String orderId){
        logger.info("getOrderDetails request {}",orderId);
        try {
            OrderDetailsResponse orderDetails = mapper.mapToOrderDetailResponse(service.getOrderDetails(orderId));
            logger.info("getOrderDetails retrieved {}",orderDetails);
            return orderDetails;
        }catch (Exception e){
            logger.error("Error occurred while listing orders details for orderId  {} ",orderId,e);
            throw e;
        }
    }

    @GetMapping("/stock")
    public List<BookResponse> getAllBooks(){
        logger.info("getAllBooks request");
        try {
            List<BookResponse> allBooks = mapper.mapToBookResponseList(service.findAllBooks());
            logger.info("getOrderDetails retrieved {}",allBooks);
            return allBooks;
        }catch (Exception e){
            logger.error("Error occurred while getting all books",e);
            throw e;
        }
    }

}
