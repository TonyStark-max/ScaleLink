package com.URL.ScaleLink.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrRes> handleValidException(
            MethodArgumentNotValidException ex
    ){
        String message=ex.getBindingResult()
                .getFieldErrors()
                .get(0)
                .getDefaultMessage();
        return new ResponseEntity<>(
                new ErrRes(HttpStatus.BAD_REQUEST.value(),message),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrRes> handleRunTimeErrors(
            RuntimeException ex
    ){

        return new ResponseEntity<>(
                new ErrRes(HttpStatus.NOT_FOUND.value(),ex.getMessage()),
                HttpStatus.NOT_FOUND
        );
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrRes> handleRuntime(RuntimeException ex){
        return new ResponseEntity<>(
                new ErrRes(HttpStatus.TOO_MANY_REQUESTS.value(), ex.getMessage()),
                HttpStatus.TOO_MANY_REQUESTS
        );
    }
}
