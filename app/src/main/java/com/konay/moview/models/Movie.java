package com.konay.moview.models;

public class Movie {
    private String imageUrl;
    private int image;
    private String title;
    private String year;
    private String movieId;
    private String genres;
    private String category;
    private String description;
    private String type;

    public Movie(String imageUrl, String title, String year, String movieId, String genres, String category, String description, String type) {
        this.imageUrl = imageUrl;
        this.title = title;
        this.year = year;
        this.movieId = movieId;
        this.genres = genres;
        this.category = category;
        this.description = description;
        this.type = type;
    }


    public Movie(int image, String title) {
        this.image = image;
        this.title = title;
    }

    public Movie(String title, String imageUrl, String category) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.category = category;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getMovieId() {
        return movieId;
    }

    public String getGenres() {
        return genres;
    }

    public String getCategory() {
        return category;
    }

    public String getDescription() {
        return description;
    }

    public String getType() {
        return type;
    }
}
