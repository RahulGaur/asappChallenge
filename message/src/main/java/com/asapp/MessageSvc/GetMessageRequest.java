package com.asapp.MessageSvc;

public class GetMessageRequest {
    String recipient;
    int start;
    int limit;

    public GetMessageRequest(String recipient, int start, int limit) {
        this.recipient = recipient;
        this.start = start;
        this.limit = limit;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
