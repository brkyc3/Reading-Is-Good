package com.readingisgood.orderservice.exception.erroresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.readingisgood.orderservice.exception.ErrorCode;
import com.readingisgood.orderservice.exception.NotFoundException;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "code", "name", "errorMessage","time" })
public class InternalErrorResponse extends ErrorResponse{
    private String name= InternalErrorResponse.class.getSimpleName();
    @Builder
    public InternalErrorResponse(ErrorCode code, String errorMessage, LocalDateTime time) {
        super(code, errorMessage, time);
    }
}
