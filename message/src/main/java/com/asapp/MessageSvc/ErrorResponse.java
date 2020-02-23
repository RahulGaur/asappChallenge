package com.asapp.MessageSvc;

public enum ErrorResponse {
    INCORRECT_REQUEST ("Incorrect Message Request"),
    UNABLE_TO_SEND_MESSAGE ("Unable to send message"),
    INCORRECT_AUTH_TOKEN ("Missing or incorrect Auth token");
    private final String error;

    ErrorResponse(String error) {
        this.error = error;
    }

    public String getError() {
        return error;
    }
}
