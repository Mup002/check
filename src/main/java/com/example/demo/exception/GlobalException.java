package com.example.demo.exception;

import com.example.demo.utils.BaseResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class GlobalException extends ResponseEntityExceptionHandler {
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<BaseResponse> handleResourceNotFoundException(ResourceNotFoundException e){
        BaseResponse response = BaseResponse.builder()
                .message(e.getMessage())
                .code(e.getCode())
                .build();
        return ResponseEntity.ok(response);
    }

    @ExceptionHandler(BaseException.class)
    public ResponseEntity<BaseResponse> handleBaseException(BaseException e){
        BaseResponse response = BaseResponse.builder()
                .message(e.getMessage())
                .code(e.getCode())
                .build();
        return ResponseEntity.ok(response);
    }
}
