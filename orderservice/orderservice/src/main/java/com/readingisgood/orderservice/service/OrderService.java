package com.readingisgood.orderservice.service;

import com.mongodb.client.result.UpdateResult;
import com.readingisgood.orderservice.controller.OrderController;
import com.readingisgood.orderservice.exception.ErrorCode;
import com.readingisgood.orderservice.exception.NotFoundException;
import com.readingisgood.orderservice.mapper.OrderMapper;
import com.readingisgood.orderservice.repository.CustomerRepository;
import com.readingisgood.orderservice.repository.OrderRepository;
import com.readingisgood.orderservice.repository.StockRepository;
import com.readingisgood.orderservice.repository.document.CustomerDocument;
import com.readingisgood.orderservice.repository.document.OrderDocument;
import com.readingisgood.orderservice.repository.document.OrderStatus;
import com.readingisgood.orderservice.repository.document.StockDocument;
import com.readingisgood.orderservice.service.model.Book;
import com.readingisgood.orderservice.service.model.Order;
import com.readingisgood.orderservice.service.model.OrderItem;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {
    private static final Logger logger = LoggerFactory.getLogger( OrderService.class);

    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final OrderMapper mapper;
    private final StockService stockService;

    public String saveOrder(Order order) {

        customerService.checkCustomerExists(order.getCustomerId());
        stockService.checkItemsExists(order);

        order.setTotalItemCount(order
                .getOrderItems()
                .stream()
                .map(OrderItem::getCount)
                .mapToInt(Integer::intValue)
                .sum());

        logger.info("saving initial order {}",order);
        OrderDocument savedOrder = orderRepository.save(mapper.mapToOrderDocument(order));
        logger.info("initial order saved with id {},{}",savedOrder.getOrderId(),order);

        stockService.updateStocks(order);

        savedOrder.setOrderStatus(OrderStatus.STOCK_OK);

        logger.info("updating order {} after stock update ",savedOrder.getOrderId());
        savedOrder = orderRepository.save(savedOrder);
        logger.info("order updated  {}",savedOrder.getOrderId());

        return savedOrder.getOrderId();
    }


    public List<Order> findAllOrdersByCustomerId(String customerId) {
        logger.info("retrieving customer orders for customer {}",customerId);

        CustomerDocument customerDocument = CustomerDocument.builder().id(customerId).build();

        List<Order> orders = mapper.mapToOrderList(orderRepository.findAllByCustomer(customerDocument));
        if(orders==null || orders.isEmpty())
            throw new NotFoundException(ErrorCode.RISGOOD_ORDER_NOT_FOUND,String.format("Cannot be found any order for customerId '%s'",customerId));
        logger.info("orders retrieved  {}",orders);

        return orders;
    }

    public Order getOrderDetails(String orderId) {
        logger.info("retrieving order details for orderId {}",orderId);
        Optional<OrderDocument> orderDocument = orderRepository.findById(orderId);
        if(orderDocument.isEmpty())
            throw new NotFoundException(ErrorCode.RISGOOD_ORDER_NOT_FOUND,String.format("Cannot be found any order with orderId '%s'",orderId));
        logger.info("orders details retrieved for orderId {}",orderId);
        return mapper.mapToOrder(orderDocument.get());
    }

}
