package com.readingisgood.orderservice.exception;


import com.readingisgood.orderservice.exception.erroresponse.NotFoundResponse;

import java.time.LocalDateTime;

public class NotFoundException extends BusinessException {

    public NotFoundException(ErrorCode code, String message) {
        super(NotFoundResponse.builder()
                        .code(code)
                        .errorMessage(message)
                        .time(LocalDateTime.now())
                        .build()
                , message);
    }
}
