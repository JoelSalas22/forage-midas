package com.jpmc.midascore.handler;

import jakarta.transaction.InvalidTransactionException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionExceptionHandler {

    @ExceptionHandler
    public String handleInvalidTransactionException(InvalidTransactionException exception) {

        return exception.getMessage();
    }
}
