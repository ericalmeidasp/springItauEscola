package com.itau.escolaItauSpring.exception;

public class ItemNaoExistenteException extends RuntimeException {
    public ItemNaoExistenteException() {
    }

    public ItemNaoExistenteException(String message) {
        super(message);
    }
}
