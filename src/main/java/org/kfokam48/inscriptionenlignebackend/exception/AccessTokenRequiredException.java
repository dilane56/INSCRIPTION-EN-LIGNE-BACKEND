package org.kfokam48.inscriptionenlignebackend.exception;

public class AccessTokenRequiredException extends RuntimeException{
    public AccessTokenRequiredException(String message) {
        super(message);
    }

}
