package com.konay.moview.models;

public class Slide {
    private int image;
    private String title;

    public int getImage() {
        return image;
    }
    public String getTitle() {
        return title;
    }

    public Slide(int image, String title) {
        this.image = image;
        this.title = title;
    }
}
