package org.kfokam48.inscriptionenlignebackend.exception;

public class AuthenticationFailedException extends RuntimeException{
    public AuthenticationFailedException(String message) {
        super(message);
    }

}
