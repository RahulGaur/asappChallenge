package com.asapp.MessageSvc;

import java.time.Instant;

public class MessageResponse {
    int messageId;
    Instant timestamp;
    ErrorResponse errorResponse;

    public MessageResponse(){

    }

    public MessageResponse(int messageId, Instant sentAt){
        this.messageId = messageId;
        this.timestamp = sentAt;
    }

    public int getMessageId() {
        return messageId;
    }

    public void setMessageId(int messageId) {
        this.messageId = messageId;
    }

    public Instant getSentAt() {
        return timestamp;
    }

    public void setSentAt(Instant sentAt) {
        this.timestamp = sentAt;
    }

    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    public void setErrorResponse(ErrorResponse errorResponse) {
        this.errorResponse = errorResponse;
    }
}
