package com.readingisgood.orderservice.service;


import com.mongodb.client.result.UpdateResult;
import com.readingisgood.orderservice.controller.OrderController;
import com.readingisgood.orderservice.exception.ErrorCode;
import com.readingisgood.orderservice.exception.NotFoundException;
import com.readingisgood.orderservice.mapper.OrderMapper;
import com.readingisgood.orderservice.repository.StockRepository;
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
public class StockService {
    private static final Logger logger = LoggerFactory.getLogger( StockService.class);

    private final StockRepository stockRepository;
    private final MongoTemplate mongoTemplate;
    private final TransactionTemplate transactionTemplate;
    private final OrderMapper mapper;

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
    public void checkItemsExists(Order order) {
        order.getOrderItems().forEach(this::checkItemExist);
    }
    private void checkItemExist(OrderItem item){
        logger.info("checking if book exists with bookId {}",item.getBookId());
        Optional<StockDocument> foundItem = stockRepository.findById(item.getBookId());
        if(foundItem.isEmpty())
            throw new NotFoundException(ErrorCode.RISGOOD_BOOK_NOT_FOUND,String.format("Book cannot be found with bookId '%s'",item.getBookId()));
        else{
            item.setBookName(foundItem.get().getBookName());
        }
    }
}
