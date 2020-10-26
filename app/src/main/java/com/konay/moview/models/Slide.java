package com.konay.moview.models;

public class Slide {
    //private int image;
    private String title;
    private String image;

    public Slide(String title, String image) {
        this.title = title;
        this.image = image;
    }

    public String getTitle() {
        return title;
    }

    public String getImage() {
        return image;
    }
}
