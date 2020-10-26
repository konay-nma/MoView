package com.konay.moview.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.konay.moview.R;
import com.konay.moview.adapters.MovieAdapter;
import com.konay.moview.models.Movie;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity {
    private TextView textViewDetailTitle, textViewGenres, textViewDesc;
    private ImageView imageDetail, coverImage;
    private Toolbar toolbarDetail;
    private RecyclerView recyclerViewDetail;
    private MovieAdapter movieAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.detail_activity);
        Intent intent = getIntent ();
        String movieTitle = intent.getStringExtra ("movieTitle");
        int movieImage = intent.getIntExtra ("movieImage", 0);
        toolbarDetail = findViewById (R.id.toolbar_detail);
        setSupportActionBar (toolbarDetail);
        getSupportActionBar ().setTitle (movieTitle);

        textViewDetailTitle = findViewById (R.id.text_view_detail_title);
        textViewGenres = findViewById (R.id.text_view_genres);
        textViewDesc = findViewById (R.id.text_view_desc);
        imageDetail = findViewById (R.id.image_detail);
        coverImage = findViewById (R.id.cover_image);
        recyclerViewDetail = findViewById (R.id.recycler_view_detail);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false);
        recyclerViewDetail.setLayoutManager (layoutManager);

        List<Movie> movieList = new ArrayList<> ();
        movieList.add (new Movie ( R.drawable.moana, "Moiana"));
        movieList.add (new Movie (R.drawable.mov2, "Moiana"));
        movieList.add (new Movie (R.drawable.slide2, "Moiana"));
        movieList.add (new Movie (R.drawable.slide1, "Moiana"));
        movieAdapter = new MovieAdapter (movieList, this);
        recyclerViewDetail.setAdapter (movieAdapter);

        textViewDetailTitle.setText (movieTitle);
        imageDetail.setImageResource (movieImage);
        coverImage.setImageResource (movieImage);

    }
}