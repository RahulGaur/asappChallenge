package com.asapp.AuthenticationService.AuthSvc;

public enum ErrorResponse {
    INCORRECT_REQUEST ("Incorrect Authentication Request"),
    UNABLE_TO_LOGIN ("Unable to login with these credentials"),
    UNABLE_TO_SIGN_UP ("Unable to create a new user");
    private final String error;

    ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
