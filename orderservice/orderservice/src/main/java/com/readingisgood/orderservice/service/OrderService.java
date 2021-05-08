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
    private static final Logger logger = LoggerFactory.getLogger( OrderController.class);

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final StockRepository stockRepository;
    private final OrderMapper mapper;
    private final MongoTemplate mongoTemplate;
    private final TransactionTemplate transactionTemplate;

    public String saveOrder(Order order) {

        checkCustomerExists(order.getCustomerId());
        checkItemsExists(order);

        order.setTotalItemCount(order
                .getOrderItems()
                .stream()
                .map(OrderItem::getCount)
                .mapToInt(Integer::intValue)
                .sum());
        logger.info("saving initial order {}",order);
        OrderDocument savedOrder = orderRepository.save(mapper.mapToOrderDocument(order));
        logger.info("initial order saved with id {},{}",savedOrder.getOrderId(),order);

        updateStocks(order);

        savedOrder.setOrderStatus(OrderStatus.STOCK_OK);
        logger.info("updating order {} after stock update ",savedOrder.getOrderId());
        savedOrder = orderRepository.save(savedOrder);
        logger.info("order updated  {}",savedOrder.getOrderId());

        return savedOrder.getOrderId();
    }

    private void checkCustomerExists(String customerId) {
        logger.info("saveOrder checking if customer exists for customerId {}",customerId);
        if(customerRepository.findById(customerId).isEmpty())
            throw new NotFoundException(ErrorCode.RISGOOD_CUSTOMER_NOT_FOUND,String.format("Customer cannot be found with customerId '%s'",customerId));
    }

    private void checkItemsExists(Order order) {
        order.getOrderItems().forEach(this::checkItemExist);
    }
    private void checkItemExist(OrderItem item){
        logger.info("saveOrder checking if book exists for bookId {}",item.getBookId());
        Optional<StockDocument> foundItem = stockRepository.findById(item.getBookId());
        if(foundItem.isEmpty())
            throw new NotFoundException(ErrorCode.RISGOOD_BOOK_NOT_FOUND,String.format("Book cannot be found with bookId '%s'",item.getBookId()));
        else{
            item.setBookName(foundItem.get().getBookName());
        }
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
        System.out.println(orderDocument.get());
        return mapper.mapToOrder(orderDocument.get());
    }

    public void updateStocks(Order order) {
        transactionTemplate.execute(
                transactionStatus -> {
                    order.getOrderItems().forEach(this::updateStock);
                    return true;
                }
        );
    }

    private void updateStock(OrderItem item) {
        logger.info("updating stocks for book {} , -{}",item.getBookId(),item.getCount());
        Query query = new Query();
        query.addCriteria(Criteria.where("_id").is(item.getBookId()));
        query.addCriteria(Criteria.where("currentCount").gte(item.getCount()));
        Update update = new Update();
        update.inc("currentCount", -item.getCount());
        UpdateResult result = mongoTemplate.updateMulti(query, update, StockDocument.class);
        if(result.getMatchedCount()==0)
            throw new NotFoundException(ErrorCode.RISGOOD_STOCK_NOT_ENOUGH,String.format("Not enough stock for bookId %s",item.getBookId()));
    }


    public String saveStock(Book book) {
        logger.info("saving book {}",book);
        String id = stockRepository.save(mapper.mapToStockDocument(book)).getId();
        logger.info("book saved with id  {}",id);
        return id;
    }

    public List<Book> findAllBooks() {
        logger.info("retrieving all books");
        List<StockDocument> allBooks = stockRepository.findAll();
        logger.info("{} number of books retrieved ",allBooks.size());
        return mapper.mapToBookList(allBooks);
    }
}
