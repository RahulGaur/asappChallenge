package com.asapp.AuthenticationService.AuthService;

import java.util.Optional;

import com.asapp.AuthenticationService.AuthSvc.ErrorResponse;

public class LoginResponse {
    String userName;
    String authToken;
    int userId;
    ErrorResponse errorResponse;

    public LoginResponse(int userId, String userName, String authToken)  {
        this.userId = userId;
        this.authToken = authToken;
        this.userName = userName;
    }

    public LoginResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
