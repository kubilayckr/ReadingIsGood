package com.kubilay.readingisgood.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class BusinessException extends RuntimeException{

    private final HttpStatus status;

    public BusinessException(String message,HttpStatus status) {
        super(message);
        this.status = status;
    }

    public BusinessException(String message, Throwable cause,HttpStatus status) {
        super(message, cause);
        this.status = status;
    }

    @Getter
    @AllArgsConstructor
    public enum ServiceException {
        USER_NOT_FOUND("exception.service.user.not.found", HttpStatus.BAD_REQUEST),
        CUSTOMER_NOT_FOUND("exception.service.customer.not.found", HttpStatus.BAD_REQUEST),
        CUSTOMER_EMAIL_CANNOT_CHANGE("exception.service.customer.email.cannot.change", HttpStatus.BAD_REQUEST),
        ORDER_NOT_FOUND("exception.service.order.not.found", HttpStatus.BAD_REQUEST),
        ORDER_START_DATE_GREATER_THAN_END_DATE("exception.service.order.startDate.greater.than.endDate", HttpStatus.BAD_REQUEST),
        ORDER_DETAIL_NOT_FOUND("exception.service.orderDetail.not.found", HttpStatus.BAD_REQUEST),
        BOOK_NOT_FOUND("exception.service.book.not.found", HttpStatus.BAD_REQUEST),
        BOOK_QUANTITY_BELOW_ZERO("exception.service.book.quantity.on.hand.below.zero", HttpStatus.BAD_REQUEST),
        ORDERED_BOOKS_NOT_EXIST("exception.service.book.ordered.books.not.exist", HttpStatus.BAD_REQUEST);

        private String key;
        private HttpStatus status;
    }
}
