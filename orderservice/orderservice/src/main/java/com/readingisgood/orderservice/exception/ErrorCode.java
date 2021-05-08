package com.readingisgood.orderservice.exception;

import org.springframework.http.HttpStatus;


public enum ErrorCode {
    RISGOOD_STOCK_NOT_ENOUGH(HttpStatus.NOT_FOUND),
    RISGOOD_CUSTOMER_NOT_FOUND(HttpStatus.NOT_FOUND),
    RISGOOD_BOOK_NOT_FOUND(HttpStatus.NOT_FOUND),
    RISGOOD_ORDER_NOT_FOUND(HttpStatus.NOT_FOUND),
    RISGOOD_SYSTEM(HttpStatus.INTERNAL_SERVER_ERROR),
    RISGOOD_VALIDATION(HttpStatus.BAD_REQUEST);
    private final HttpStatus httpStatus;
     ErrorCode(HttpStatus status){
        this.httpStatus=status;
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
