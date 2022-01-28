package com.readingisgood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
@AllArgsConstructor
public enum ErrorCodeEnum {

    INTERNAL_SERVER_ERROR(1000, "Internal server error.", HttpStatus.INTERNAL_SERVER_ERROR),
    FIELD_VALIDATION_ERROR(1001, "Field validation error.", HttpStatus.BAD_REQUEST),
    CUSTOMER_ILLEGAL_ARGUMENT_ERROR(1002, "Customer dto has Illegal argument.", HttpStatus.BAD_REQUEST),
    CUSTOMER_NOT_FOUND(1003, "Customer not found", HttpStatus.BAD_REQUEST),
    BOOK_NOT_FOUND(1003, "Book not found", HttpStatus.BAD_REQUEST),
    BOOK_NOT_CREATED(1004, "Book not created", HttpStatus.BAD_REQUEST),
    BOOK_ID_MUST_NOT_BLANK(1005, "Book id must not blank", HttpStatus.BAD_REQUEST),
    BOOK_NOT_AVAILABLE(1006, "Book not available", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND(1007, "User not found", HttpStatus.BAD_REQUEST),
    ORDER_NOT_FOUND(1008, "Order not found", HttpStatus.BAD_REQUEST),
    STATISTICS_ERROR(1008, "Unexpected error", HttpStatus.BAD_REQUEST);


    private int code;
    private String message;
    private HttpStatus httpStatus;
}
