package com.konay.moview.models;

public class Movie {
    private int image;
    private String title;

    public Movie(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }
}
