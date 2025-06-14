package org.kfokam48.inscriptionenlignebackend.exception;

public class RessourceNotFoundException extends  RuntimeException {
    public RessourceNotFoundException(String message) {
        super(message);
    }
}
