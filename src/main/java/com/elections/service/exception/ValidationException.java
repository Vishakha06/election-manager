package com.elections.service.exception;

public class ValidationException extends Exception {

    private String errorMessage;

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public ValidationException(String errorMessage){
        super(errorMessage);
        this.errorMessage = errorMessage;
    }


}
