package com.konay.moview.activity;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.tabs.TabLayout;
import com.konay.moview.R;
import com.konay.moview.adapters.SliderPagerAdapter;
import com.konay.moview.adapters.VerticalMovieAdapter;
import com.konay.moview.models.Movie;
import com.konay.moview.models.Slide;
import com.konay.moview.models.VerticalMovie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    private ViewPager sliderPager;
    private final List<Slide> listSlides = new ArrayList<> ();
    private Timer timer;
    private final List<VerticalMovie> verticalMovieList = new ArrayList<> ();
    private ProgressBar progressBar;
    private VerticalMovieAdapter verticalMovieAdapter;
    SliderPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate (savedInstanceState);
        setContentView (R.layout.activity_main);
        Toolbar toolbar = findViewById (R.id.toolbar);
        setSupportActionBar (toolbar);
        sliderPager = findViewById (R.id.slider_pager);
        TabLayout indicator = findViewById (R.id.indicator);
        progressBar = findViewById (R.id.progress_circular);
        // local declaration
        RecyclerView recyclerViewVerticalMovie = findViewById (R.id.vertical_movie_recycler_view);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager (this, LinearLayoutManager.VERTICAL, false);
        recyclerViewVerticalMovie.setLayoutManager (layoutManager);
        recyclerViewVerticalMovie.setHasFixedSize (true);
        //recyclerViewMovie.setLayoutManager (new LinearLayoutManager (this, LinearLayoutManager.HORIZONTAL, false));

        loadMovieApi();
        /**
        listSlides.add(new Slide (R.drawable.slide1, "Slide title 1"));
        listSlides.add(new Slide (R.drawable.slide2, "Slide title 2"));
        listSlides.add(new Slide (R.drawable.slide1, "Slide title 1"));
        listSlides.add(new Slide (R.drawable.slide2, "Slide title 2"));
        **/
        adapter = new SliderPagerAdapter (this, listSlides);
        //set up adapter
        sliderPager.setAdapter (adapter);
        //set up timer
//        timer = new Timer ();
//        timer.scheduleAtFixedRate (new MainActivity.SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager (sliderPager, true);

        // setting up the movie item to adapter and set up adapter
        /**
        movieList.add (new Movie (R.drawable.moana, "Moiana"));
        movieList.add (new Movie (R.drawable.mov2, "Moiana"));
        movieList.add (new Movie (R.drawable.slide2, "Moiana"));
        movieList.add (new Movie (R.drawable.slide1, "Moiana")); **/
       // MovieAdapter movieAdapter = new MovieAdapter (movieList);
        //set recyclerview with the movie adapter
       // recyclerViewMovie.setAdapter (movieAdapter);

        // setting up the vertical movie adapter and data list handling
        /**
        verticalMovieList.add (new VerticalMovie ("First Category", movieList));
        verticalMovieList.add (new VerticalMovie ("Second Category", movieList));
        verticalMovieList.add (new VerticalMovie ("Third Category", movieList));
        verticalMovieList.add (new VerticalMovie ("Third Category", movieList)); **/
        verticalMovieAdapter = new VerticalMovieAdapter (verticalMovieList, this);
        recyclerViewVerticalMovie.setAdapter (verticalMovieAdapter);
        recyclerViewVerticalMovie.addOnScrollListener (new RecyclerView.OnScrollListener () {
            @Override
            public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {

                Log.d("DY", ""+dx + dy);
                super.onScrolled (recyclerView, dx, dy);
            }

            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                Log.d("state", ""+newState);
                super.onScrollStateChanged (recyclerView, newState);
            }
        });

        FloatingActionButton fab = findViewById (R.id.fab);
        fab.setOnClickListener (new View.OnClickListener () {
            @Override
            public void onClick(View view) {
                Snackbar.make (view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction ("Action", null).show ();
            }
        });

        //setting up collapsing toolbar title
        CollapsingToolbarLayout collapsingToolbarLayout = findViewById (R.id.collapsing_toolbar);
        collapsingToolbarLayout.setExpandedTitleColor (Color.TRANSPARENT);
    }

    private void loadMovieApi() {
        String url = "https://us-central1-nodemcu-thesis-db.cloudfunctions.net/app/moviesapi";
        RequestQueue requestQueue = Volley.newRequestQueue (getApplicationContext ());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest (Request.Method.GET, url, null, new Response.Listener<JSONArray> () {
            @Override
            public void onResponse(JSONArray response) {
                progressBar.setVisibility (View.INVISIBLE);
                try {
                    List<Movie> allList = new ArrayList<> ();
                    JSONArray categoryArr = response.getJSONObject (0).getJSONArray ("categories");
                    JSONArray movies =response.getJSONObject (1).getJSONArray ("movies");

                    for (int i = 0; i < movies.length (); i++) {
                        JSONObject movie = movies.getJSONObject (i);
                        listSlides.add (new Slide ((movie.getString ("title")), movie.getString ("image")));
                        // random slide list movie
                        for(int j = 6; j < listSlides.size (); j++) {
                            Random random = new Random ();
                            int randomIndex = random.nextInt (listSlides.size ());
                            listSlides.remove (randomIndex);
                        }
                        allList.add(new Movie (movie.getString ("title"), movie.getString ("image"), movie.getString ("category")));
                    }

                    adapter.notifyDataSetChanged ();

                    verticalMovieList.add (new VerticalMovie ("Recent Added"+" ("+ allList.size ()+")", allList));

                    for (int j = 0; j < categoryArr.length (); j++) {
                        List<Movie> movieList = new ArrayList<> ();
                        for(int i = 0; i < movies.length (); i++) {
                            JSONObject movie = movies.getJSONObject (i);
                            String title = movie.getString ("title");
                            String image = movie.getString ("image");
                            String category = movie.getString ("category");
                            if(categoryArr.getString(j).equals (movie.getString ("category"))) {
                                movieList.add(new Movie (title, image, category));
                            }

                            Log.d("movies", ""+movie);
                        }
                        // matching the category and movie list
                        verticalMovieList.add (new VerticalMovie (categoryArr.getString (j)+" ("+movieList.size ()+")", movieList));
                        Log.d ("verList", ""+ verticalMovieList.size ());
                    }
                    verticalMovieAdapter.notifyDataSetChanged ();
                } catch (JSONException e) {
                    e.printStackTrace ();
                }
            }
        }, new Response.ErrorListener () {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressBar.setVisibility (View.INVISIBLE);
            }
        });
        requestQueue.add (jsonArrayRequest);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater ().inflate (R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId ();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected (item);
    }

    @Override
    protected void onResume() {
        super.onResume ();
        Log.d("OnResume", "this is on resume");
        timer = new Timer();
        timer.scheduleAtFixedRate (new MainActivity.SliderTimer(), 6000, 8000);
    }

    @Override
    protected void onPause() {
        super.onPause ();
        Log.d("OnStop", "this is on stop");
        timer.cancel ();
    }

    /// create new thread on ui for timer
    public class SliderTimer extends TimerTask {
        @Override
        public void run() {
            MainActivity.this.runOnUiThread (new Runnable () {
                @Override
                public void run() {
                    int item = sliderPager.getCurrentItem ();
                    if (item < listSlides.size () - 1){
                        sliderPager.setCurrentItem (item + 1);
                    } else sliderPager.setCurrentItem (0);
                }
            });
        }
    }
}