package com.konay.moview.adapters;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.konay.moview.R;
import com.konay.moview.activity.DetailActivity;
import com.konay.moview.activity.MainActivity;
import com.konay.moview.models.Movie;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

// Create the basic adapter extending from RecyclerView.Adapter
// Note that we specify the custom ViewHolder which gives us access to our views
public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MyViewHolder> {

    public List<Movie> movieList;
    private Context context;

    public MovieAdapter(List<Movie> movieList, Context context) {
        this.movieList = movieList;
        this.context = context;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        public TextView movieTitle;
        public ImageView movieImage;
        public CardView containerView;
        public MyViewHolder(View itemView){
            super(itemView);
            movieTitle = itemView.findViewById (R.id.movie_title);
            movieImage = itemView.findViewById (R.id.movie_image);
            containerView = itemView.findViewById (R.id.card_view_movie_item);

            containerView.setOnClickListener (new View.OnClickListener () {

                @Override
                public void onClick(View v) {
                    //List<Movie> allList = MovieAdapter.movieList;
//                    List<Movie> allList = MainActivity.allList;
//                    Log.d("all list", "all movie list" + allList.size());
                    Movie movieItem = (Movie) containerView.getTag (); // getting the current movie item from setTag(movieItem)
                    int position = getAdapterPosition (); //getting the position index i.e. it start from 0
                    Log.d("Position", ""+position + movieItem.getTitle ());
                    Intent intent = new Intent (v.getContext (), DetailActivity.class);

                   // intent.putParcelableArrayListExtra("ALLMOVIELIST", (ArrayList<? extends Parcelable>) allList);
                    intent.putExtra ("movieTitle", movieItem.getTitle ());
                    intent.putExtra ("movieImage", movieItem.getImage());

                    v.getContext ().startActivity (intent);
                }
            });
        }
    }

//    public static class MyViewHolder2 extends RecyclerView.ViewHolder {
//
//        public MyViewHolder2(@NonNull View itemView) {
//            super(itemView);
//        }
//    }


    @NonNull
    @Override
    public MovieAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from (parent.getContext ()).inflate (R.layout.movie_item, parent, false);
        return new MyViewHolder (view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        final Movie movieItem = movieList.get (position);
        String imageUrl = movieItem.getImage ();
        holder.movieTitle.setText (movieItem.getTitle ());
        holder.containerView.setTag (movieItem);
        Picasso.get()
                .load(imageUrl)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.movieImage);
    }

    @Override
    public int getItemCount() {
        return movieList.size ();
    }



}
