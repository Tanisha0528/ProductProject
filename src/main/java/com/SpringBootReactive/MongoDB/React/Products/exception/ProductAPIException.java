package com.SpringBootReactive.MongoDB.React.Products.exception;

public class ProductAPIException extends Exception{

    public ProductAPIException(String message) {
        super(message);
    }
}/*
class ProductAPIException extends WebClientException {
    private final HttpStatus status;
    private final ErrorDetails details;

    CustomClientException(HttpStatus status, ErrorDetails details) {
        super(status.getReasonPhrase());
        this.status = status;
        this.details = details;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public ErrorDetails getDetails() {
        return details;
    }
}*/