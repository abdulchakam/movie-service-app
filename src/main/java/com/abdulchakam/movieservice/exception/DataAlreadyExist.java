package com.abdulchakam.movieservice.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DataAlreadyExist extends RuntimeException {

    private final HttpStatus httpStatus;

    public DataAlreadyExist(String message) {
        super(message);
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }
}
