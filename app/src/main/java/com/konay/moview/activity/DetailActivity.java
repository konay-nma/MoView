package com.konay.moview.activity;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.VolleyError;
import com.konay.moview.R;
import com.konay.moview.adapters.MovieAdapter;
import com.konay.moview.models.Movie;
import com.konay.moview.services.ApiFetcher;
import com.konay.moview.utils.Constants;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class DetailActivity extends AppCompatActivity {
    private TextView textViewDetailTitle, textViewGenres, textViewDesc;
    private ImageView imageDetail, coverImage;
    private Toolbar toolbarDetail;
    private RecyclerView recyclerViewDetail;
    private MovieAdapter movieAdapter;
    private final List<Movie> movieList = new ArrayList<>();
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.detail_activity);
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please wait a sec.....");
        progressDialog.show();
        Intent intent = getIntent ();
        String movieTitle = intent.getStringExtra ("movieTitle");
        String movieImage = intent.getStringExtra ("movieImage");
        Log.d("image", "this is " + movieImage);
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

       // ArrayList<? extends Movie> movieList = intent.getExtras().getParcelableArrayList("ALLMOVIELIST");
       // movieAdapter = new MovieAdapter((List<Movie>) movieList, this);
        movieAdapter = new MovieAdapter(movieList, this);

        recyclerViewDetail.setAdapter (movieAdapter);

        textViewDetailTitle.setText (movieTitle);
        Picasso.get()
                .load(movieImage)
                .placeholder(R.drawable.ic_launcher_background)
                .into(coverImage);
        Picasso.get()
                .load(movieImage)
                .placeholder(R.drawable.ic_launcher_background)
                .into(imageDetail);

        // fetch api data
        getApiData();
    }

    private void getApiData() {
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + Constants.BLOG_ID +
                "/posts?" + "maxResults=" + Constants.MAX_RESUlT +
                "&key=" + Constants.KEY;
        new ApiFetcher(this).setOnResponseLister(new ApiFetcher.OnResponseListener() {
            @RequiresApi(api = Build.VERSION_CODES.KITKAT)
            @Override
            public void OnResponse(JSONObject response) throws JSONException {
                progressDialog.dismiss();
                Log.i("Response", ""+ response);
                JSONArray jsonArrayItems = response.getJSONArray("items");
                Log.i("items lenght", ""+jsonArrayItems.length());
                for (int i = 0; i < jsonArrayItems.length (); i++) {
                    JSONObject jsonObjectContent = jsonArrayItems.getJSONObject(i);
                    String htmlContents = jsonObjectContent.getString("content");
                    String contents = Html.fromHtml(htmlContents).toString();
                    //Log.i("contents", contents);
                    JSONObject jsonObjectMovies = new JSONObject(contents);
                    movieList.add(new Movie(jsonObjectMovies.getString("title"),
                            jsonObjectMovies.getString("image"),
                            jsonObjectMovies.getString("category"),
                            jsonObjectMovies.getJSONArray("source")));
                    for(int j = 3; j < movieList.size(); j ++) {
                        Random random = new Random();
                        int rdmIndex = random.nextInt(movieList.size());
                        movieList.remove(rdmIndex);
                    }
                }
                movieAdapter.notifyDataSetChanged();

            }
        }, new ApiFetcher.OnErrorListener() {
            @Override
            public void OnError(VolleyError error) {

            }
        }).fetch(url);
    }
}