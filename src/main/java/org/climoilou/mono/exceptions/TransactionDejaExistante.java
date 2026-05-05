package org.climoilou.mono.exceptions;

public class TransactionDejaExistante extends RuntimeException {
    public TransactionDejaExistante(String message) {
        super(message);
    }
}
