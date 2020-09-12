package com.konay.moview.models;

import java.util.List;

public class VerticalMovie {
    private String category;
    private List<Movie> movieList;

    public VerticalMovie(String category, List<Movie> movieList) {
        this.category = category;
        this.movieList = movieList;
    }

    public String getCategory() {
        return category;
    }

    public List<Movie> getMovieList() {
        return movieList;
    }
}
