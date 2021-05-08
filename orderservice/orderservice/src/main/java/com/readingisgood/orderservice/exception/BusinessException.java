package com.readingisgood.orderservice.exception;

import com.readingisgood.orderservice.exception.erroresponse.ErrorResponse;
import lombok.Data;

@Data
public abstract class BusinessException extends RuntimeException{
    private ErrorResponse response;
    public BusinessException(ErrorResponse response, String message) {
        super(message);
        this.response = response;
    }
}
