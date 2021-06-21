package com.konay.moview.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
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
import com.konay.moview.services.ApiFetcher;
import com.konay.moview.utils.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
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
        adapter = new SliderPagerAdapter (this, listSlides);
        //set up adapter
        sliderPager.setAdapter (adapter);
        //set up timer
//        timer = new Timer ();
//        timer.scheduleAtFixedRate (new MainActivity.SliderTimer(), 4000, 6000);
        indicator.setupWithViewPager (sliderPager, true);

        verticalMovieAdapter = new VerticalMovieAdapter (verticalMovieList, this);
        recyclerViewVerticalMovie.setAdapter (verticalMovieAdapter);

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
        String url = "https://www.googleapis.com/blogger/v3/blogs/" + Constants.BLOG_ID +
                    "/posts?" + "maxResults=" + Constants.MAX_RESUlT +
                    "&key=" + Constants.KEY;
        new ApiFetcher(this).setOnResponseLister(new ApiFetcher.OnResponseListener() {
            @Override
            public void OnResponse(JSONObject response) throws JSONException {
                progressBar.setVisibility(View.INVISIBLE);
                List<Movie> allList = new ArrayList<>();
                JSONArray jsonArrayItems = response.getJSONArray("items");
                List<String> categoryArr = new ArrayList<>();
                for (int i = 0; i < jsonArrayItems.length (); i++) {
                    JSONObject jsonObjectContent = jsonArrayItems.getJSONObject (i);
                    String htmlContents = jsonObjectContent.getString("content");
                    String contents = Html.fromHtml(htmlContents).toString();
                    //Log.i("contents", contents);
                    JSONObject jsonObjectMovies = new JSONObject(contents);
                    //Log.i("movie: ", ""+ jsonObjectMovies);
                    String category = jsonObjectMovies.getString("category");
                    categoryArr.add(category);
                    listSlides.add (new Slide ((jsonObjectMovies.getString ("title")), jsonObjectMovies.getString ("image")));
                    // random slide list movie
                    for(int j = 6; j < listSlides.size (); j++) {
                        Random random = new Random ();
                        int randomIndex = random.nextInt (listSlides.size ());
                        listSlides.remove (randomIndex);
                    }

                    allList.add(new Movie (jsonObjectMovies.getString ("title"),
                            jsonObjectMovies.getString ("image"),
                            jsonObjectMovies.getString("category"),
                            jsonObjectMovies.getJSONArray("source")));
                }

                adapter.notifyDataSetChanged ();

                verticalMovieList.add (new VerticalMovie ("Recent Added"+" ("+ allList.size ()+")", allList));
                String[] categoryArray = new LinkedHashSet<>(categoryArr).toArray(new String[0]);
                Log.i("array", "" + categoryArray);
                for (int j = 0; j < categoryArray.length; j++) {
                    List<Movie> movieList = new ArrayList<> ();
                    for(int i = 0; i < jsonArrayItems.length (); i++) {
                        JSONObject jsonObjectContent = jsonArrayItems.getJSONObject (i);
                        String htmlContents = jsonObjectContent.getString("content");
                        String contents = Html.fromHtml(htmlContents).toString();
                        // Log.i("contents", contents);
                        JSONObject jsonObjectMovies = new JSONObject(contents);
                        String title = jsonObjectMovies.getString ("title");
                        String image = jsonObjectMovies.getString ("image");
                        String category = jsonObjectMovies.getString ("category");
                        JSONArray jsonArraySource = jsonObjectMovies.getJSONArray("source");
                        if(categoryArray[j] .equals (jsonObjectMovies.getString ("category"))) {
                            movieList.add(new Movie (title, image,category,jsonArraySource ));
                        }

                    }
                    // matching the category and movie list
                    verticalMovieList.add (new VerticalMovie (categoryArray[j]+" ("+movieList.size ()+")", movieList));

                }
                verticalMovieAdapter.notifyDataSetChanged ();
            }
        }, new ApiFetcher.OnErrorListener() {
            @Override
            public void OnError(VolleyError error) {

            }
        }).fetch(url);
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

    @Override
    protected void onStop() {
        super.onStop();
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