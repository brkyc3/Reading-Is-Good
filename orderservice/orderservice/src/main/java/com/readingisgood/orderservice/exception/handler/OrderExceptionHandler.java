package com.readingisgood.orderservice.exception.handler;

import com.readingisgood.orderservice.exception.BusinessException;
import com.readingisgood.orderservice.exception.ErrorCode;
import com.readingisgood.orderservice.exception.NotFoundException;
import com.readingisgood.orderservice.exception.erroresponse.InputNotValidResponse;
import com.readingisgood.orderservice.exception.erroresponse.ErrorResponse;
import com.readingisgood.orderservice.exception.erroresponse.InternalErrorResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@ControllerAdvice(basePackages = "com.readingisgood.orderservice.controller")
public class OrderExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<?> handleBusinessException(
            BusinessException ex) {
        ErrorResponse response = ex.getResponse();
        return new ResponseEntity<>(response, response.getCode().getHttpStatus());
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<?> handleSystemException(
            Exception ex) {
        InternalErrorResponse response = InternalErrorResponse.builder()
                .code(ErrorCode.RISGOOD_SYSTEM)
                .errorMessage(ex.getMessage())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, response.getCode().getHttpStatus());
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<String> details = new ArrayList<>();
        for (ObjectError error : ex.getBindingResult().getAllErrors()) {
            Optional<String> first = Arrays.stream(error.toString().split(";")).findFirst();
            details.add(first.orElse(error.getDefaultMessage()));
        }
        InputNotValidResponse response = InputNotValidResponse.builder()
                .code(ErrorCode.RISGOOD_VALIDATION)
                .errorMessage(details.toString())
                .time(LocalDateTime.now())
                .build();
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }
}
