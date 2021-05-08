package com.readingisgood.orderservice.exception.erroresponse;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.readingisgood.orderservice.exception.ErrorCode;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonPropertyOrder({ "code", "name", "errorMessage","time" })
public class InputNotValidResponse extends ErrorResponse{
    private String name= InputNotValidResponse.class.getSimpleName();
    @Builder
    public InputNotValidResponse(ErrorCode code, String errorMessage, LocalDateTime time) {
        super(code, errorMessage, time);
    }
}
