package com.asapp.content;

public class Content {
    int contentId;
    String type;
    Text text;
    Image image;
    Video video;


    public Content() {

    }

    public Content(String type, Text text){
        this.type = type;
        this.text = text;
    }
    public Content(int contentId){
        this.contentId = contentId;
    }

    public Content (String type, Image image) {
        this.type = type;
        this.image = image;
    }

    public Content (String type, Video video) {
        this.type = type;
        this.video = video;
    }

    public Content(int contentId, String type, Text text, Image image, Video video) {
        this.contentId = contentId;
        this.text = text;
        this.image = image;
        this.video = video;
        this.type = type;
    }
    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public Text getText() {
        return text;
    }

    public void setText(Text text) {
        this.text = text;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Video getVideo() {
        return video;
    }

    public void setVideo(Video video) {
        this.video = video;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
