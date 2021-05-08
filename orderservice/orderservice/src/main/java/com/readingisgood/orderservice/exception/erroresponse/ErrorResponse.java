package com.readingisgood.orderservice.exception.erroresponse;

import com.readingisgood.orderservice.exception.ErrorCode;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    protected ErrorCode code;
    protected String errorMessage;
    protected LocalDateTime time;
}
