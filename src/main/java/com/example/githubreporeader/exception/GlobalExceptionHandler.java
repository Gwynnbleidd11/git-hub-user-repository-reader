package com.example.githubreporeader.exception;

import com.example.githubreporeader.request_response.UserNotExistingResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<UserNotExistingResponse> handleHttpClientErrorException(HttpClientErrorException e) {
        UserNotExistingResponse response = UserNotExistingResponse.builder()
                .status(e.getStatusCode().value())
                .message("Provided user does not exist. Please ensure you are using correct username.")
                .build();
        return new ResponseEntity<>(response, HttpStatus.valueOf(e.getRawStatusCode()));
    }
}
