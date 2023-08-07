package com.abdulchakam.movieservice.exception;

import com.abdulchakam.movieservice.dto.BaseResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@RestControllerAdvice
public class CustomExceptionHandler extends ResponseEntityExceptionHandler {

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        Map<String, String> map = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(fieldError -> {
           map.put(fieldError.getField(), fieldError.getDefaultMessage());
        });
        BaseResponse body = new BaseResponse();
        body.setStatus(ex.getStatusCode().value());
        body.setMessage("Error validation");
        body.setErrors(map);
        return new ResponseEntity<>(body, new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DataNotFoundException.class)
    public ResponseEntity<Object> handleClientException(DataNotFoundException ex) {
        logger.error("DataNotFound Exception : ", ex);
        BaseResponse body = new BaseResponse();
        body.setStatus(ex.getHttpStatus().value());
        body.setMessage(ex.getHttpStatus().getReasonPhrase());
        body.setErrors(ex.getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(InternalServerException.class)
    public ResponseEntity<Object> handleClientException(InternalServerException ex) {
        logger.error("InternalServerError Exception : ", ex);
        BaseResponse body = new BaseResponse();
        body.setStatus(ex.getHttpStatus().value());
        body.setMessage(ex.getHttpStatus().getReasonPhrase());
        body.setErrors(ex.getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }

    @ExceptionHandler(DataAlreadyExistException.class)
    public ResponseEntity<Object> handleClientException(DataAlreadyExistException ex) {
        logger.error("DataAlreadyExistException Exception : ", ex);
        BaseResponse body = new BaseResponse();
        body.setStatus(ex.getHttpStatus().value());
        body.setMessage(ex.getHttpStatus().getReasonPhrase());
        body.setErrors(ex.getMessage());
        return new ResponseEntity<>(body, ex.getHttpStatus());
    }
}

