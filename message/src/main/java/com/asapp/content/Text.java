package com.asapp.content;

public class Text {
    int textId;
    String text;

    public Text(String text){
        this.text = text;
    }

    public Text(int textId){
        this.textId = textId;
    }

    public Text(int textId, String text) {
        this.textId = textId;
        this.text = text;
    }

    public int getTextId() {
        return textId;
    }

    public void setTextId(int textId) {
        this.textId = textId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
