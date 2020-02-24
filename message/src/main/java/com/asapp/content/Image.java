package com.asapp.content;

public class Image {

    int imageId;
    String url;
    double height;
    double width;

    public Image() {

    }
    public Image(String url, double height, double width) {
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public Image(int imageId, String url, double height, double width) {
        this.imageId = imageId;
        this.url = url;
        this.height = height;
        this.width = width;
    }

    public Image(int imageId) {
        this.imageId = imageId;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(int imageId) {
        this.imageId = imageId;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
