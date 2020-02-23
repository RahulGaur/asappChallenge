package com.asapp.content;

public enum MediaType {

    IMAGE ("image"),
    TEXT ("text"),
    VIDEO ("video");
    private final String type;

    MediaType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
