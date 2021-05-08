package com.readingisgood.orderservice.mapper;

import com.readingisgood.orderservice.controller.model.*;
import com.readingisgood.orderservice.repository.document.OrderDocument;
import com.readingisgood.orderservice.repository.document.StockDocument;
import com.readingisgood.orderservice.service.model.Book;
import com.readingisgood.orderservice.service.model.Order;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper
public interface OrderMapper {
    Order mapToOrder(OrderRequest order);
    @InheritInverseConfiguration(name="mapToOrderDocument")
    Order mapToOrder(OrderDocument orderDocument);
    @Mapping(target = "customer.id" ,source = "customerId")
    @Mapping(target = "orderStatus", source="orderStatus",defaultValue = "STOCK_CONTROL")
    OrderDocument mapToOrderDocument(Order order);
    @Mapping(source = "customer.id" ,target = "customerId")
    List<Order> mapToOrderList(List<OrderDocument> orderDocuments);
    List<OrderListResponseItem> mapToOrderResponseList(List<Order> orders);
    OrderDetailsResponse mapToOrderDetailResponse(Order order);

    Book mapToBook(SaveStockRequest request);
    StockDocument mapToStockDocument(Book book);

    List<Book> mapToBookList(List<StockDocument> all);

    List<BookResponse> mapToBookResponseList(List<Book> allBooks);
}
