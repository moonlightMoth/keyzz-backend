package ru.moonlightmoth.keyzz_backend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import ru.moonlightmoth.keyzz_backend.exception.*;
import ru.moonlightmoth.keyzz_backend.model.response.ErrorResponse;
import ru.moonlightmoth.keyzz_backend.model.response.Status;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AuthFailException.class)
    public ResponseEntity<ErrorResponse> handleException(AuthFailException e)
    {
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .status(Status.FAIL)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(NoSuchRecordException.class)
    public ResponseEntity<ErrorResponse> handleException(NoSuchRecordException e)
    {
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .status(Status.FAIL)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(NoSuchUserRecordedException.class)
    public ResponseEntity<ErrorResponse> handleException(NoSuchUserRecordedException e)
    {
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .status(Status.FAIL)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(UnauthorisedPatchException.class)
    public ResponseEntity<ErrorResponse> handleException(UnauthorisedPatchException e)
    {
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .status(Status.FAIL)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(MissingRequestHeaderException.class)
    public ResponseEntity<ErrorResponse> handleException(MissingRequestHeaderException e)
    {
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .status(Status.FAIL)
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(NoSuchAddressException.class)
    public ResponseEntity<ErrorResponse> handleException(NoSuchAddressException e)
    {
        return ResponseEntity.badRequest().body(ErrorResponse
                .builder()
                .status(Status.FAIL)
                .message(e.getMessage())
                .build());
    }
}
