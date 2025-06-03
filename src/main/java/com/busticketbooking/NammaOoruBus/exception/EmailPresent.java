package com.busticketbooking.NammaOoruBus.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class EmailPresent extends RuntimeException {

    public EmailPresent(String message) {
        super(message);
    }
}