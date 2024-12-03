package com.journey_back.infra.exception;

public class ValidationError extends RuntimeException{

    // Exception
    public ValidationError(String mensagem) {
        super(mensagem);
    }
}

