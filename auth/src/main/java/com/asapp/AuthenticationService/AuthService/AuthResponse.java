package com.asapp.AuthenticationService.AuthService;

import com.asapp.AuthenticationService.AuthSvc.ErrorResponse;

public class AuthResponse {
    String username;
    ErrorResponse errorResponse;

    public AuthResponse(String username) {
        this.username = username;
    }
    public AuthResponse(ErrorResponse errorResponse){
        this.errorResponse = errorResponse;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
