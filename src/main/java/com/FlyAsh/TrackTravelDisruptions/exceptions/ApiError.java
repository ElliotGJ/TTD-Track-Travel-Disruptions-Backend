package com.FlyAsh.TrackTravelDisruptions.exceptions;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
public class ApiError {
    HttpStatus httpStatus;
    LocalDateTime timestamp;
    String message;

    private ApiError() {
        timestamp = LocalDateTime.now();
    }

    ApiError(HttpStatus httpStatus) {
        this();
        this.httpStatus = httpStatus;
    }

    public ApiError(HttpStatus httpStatus, String message) {
        this();
        this.message = message;
        this.httpStatus = httpStatus;
    }

    ApiError(HttpStatus httpStatus, Throwable ex) {
        this();
        this.httpStatus = httpStatus;
        this.message = ex.getMessage();
    }
}
