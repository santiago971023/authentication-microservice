package com.auth_service.domain.exception;

public enum ErrorMessageBusiness {

    USER_ALREADY_EXISTS_EXCEPTION("The user already exists.");

    private final String message;

    ErrorMessageBusiness(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
