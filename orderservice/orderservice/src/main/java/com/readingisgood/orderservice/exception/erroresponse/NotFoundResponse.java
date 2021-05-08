package com.readingisgood.orderservice.exception.erroresponse;


import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.readingisgood.orderservice.exception.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "code", "name", "errorMessage","time" })
public class NotFoundResponse extends ErrorResponse{
    private String name= NotFoundResponse.class.getSimpleName();
    @Builder
    NotFoundResponse(ErrorCode code, String errorMessage, LocalDateTime time) {
        super(code, errorMessage, time);
    }
}
