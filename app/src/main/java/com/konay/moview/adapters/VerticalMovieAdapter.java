package com.konay.moview.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.konay.moview.R;
import com.konay.moview.models.VerticalMovie;

import java.util.List;

public class VerticalMovieAdapter extends RecyclerView.Adapter<VerticalMovieAdapter.MyViewHolder> {

    private List<VerticalMovie> verticalMovieList;
    private Context context;
    private MovieAdapter movieAdapter;
    private RecyclerView.RecycledViewPool viewPool = new RecyclerView.RecycledViewPool ();

    //constructor
    public VerticalMovieAdapter(List<VerticalMovie> verticalMovieList, Context context) {
        this.verticalMovieList = verticalMovieList;
        this.context = context;
    }
   //inner class view holder
    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView verticalTitle ;
        private RecyclerView recyclerViewMovie;
        public MyViewHolder(@NonNull View itemView) {
            super (itemView);
            verticalTitle = itemView.findViewById (R.id.vertical_item_title);
            recyclerViewMovie = itemView.findViewById (R.id.horizontal_movie_recycler_view);
        }
    }

    @NonNull
    @Override
    public VerticalMovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.vertical_movie_item, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        VerticalMovie current = verticalMovieList.get (position); //getting the current position item from model list
        holder.verticalTitle.setText (current.getCategory ());
        movieAdapter = new MovieAdapter (current.getMovieList ()); // creating new instance
        holder.recyclerViewMovie.setAdapter (movieAdapter); //set adapter the recycler view
        holder.recyclerViewMovie.setRecycledViewPool (viewPool); // set view pool
        holder.recyclerViewMovie.setLayoutManager (new LinearLayoutManager (context, LinearLayoutManager.HORIZONTAL, false));
        holder.recyclerViewMovie.setNestedScrollingEnabled (false); // not to over write main scrolling behavior
    }

    @Override
    public int getItemCount() {
        return verticalMovieList.size ();
    }
}
