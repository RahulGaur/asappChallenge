package com.asapp.content;

public class Video {
    int videoId;
    String type;
    String url;
    String source;

    public Video() {

    }
    public Video(String type, String url, String source){
        this.type = type;
        this.url = url;
        this.source = source;
    }

    public Video(int videoId, String url, String source) {
        this.videoId = videoId;
        this.url = url;
        this.source = source;
    }

    public Video(int videoId) {
        this.videoId = videoId;
    }

    public int getVideoId() {
        return videoId;
    }

    public void setVideoId(int videoId) {
        this.videoId = videoId;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}
