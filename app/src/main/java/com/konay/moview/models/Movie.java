package com.konay.moview.models;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;

public class Movie implements Parcelable {
    private String image;
    private String title;
    private String year;
    private String genres;
    private String category;
    private String description;
    private String type;

    public JSONArray getJsonArraySource() {
        return jsonArraySource;
    }

    public void setJsonArraySource(JSONArray jsonArraySource) {
        this.jsonArraySource = jsonArraySource;
    }

    private JSONArray jsonArraySource;

    public Movie(String imageUrl, String title, String year, String movieId, String genres, String category, String description, String type) {
        this.title = title;
        this.year = year;
        this.genres = genres;
        this.category = category;
        this.description = description;
        this.type = type;
    }


    public Movie(String image, String title) {
        this.image = image;
        this.title = title;
    }

    public Movie(String title, String imageUrl, String category, JSONArray jsonArraySource) {
        this.title = title;
        this.image = imageUrl;
        this.category = category;
        this.jsonArraySource = jsonArraySource;
    }

    protected Movie(Parcel in) {
        image = in.readString();
        title = in.readString();
        year = in.readString();
        genres = in.readString();
        category = in.readString();
        description = in.readString();
        type = in.readString();
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };


    public String getImage() {
        return image;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(image);
        parcel.writeString(title);
        parcel.writeString(year);
        parcel.writeString(genres);
        parcel.writeString(category);
        parcel.writeString(description);
        parcel.writeString(type);
    }
}
