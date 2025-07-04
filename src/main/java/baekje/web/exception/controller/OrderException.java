package baekje.web.exception.controller;

public class OrderException extends RuntimeException {

    public OrderException(String message) {
        super(message);
    }
}