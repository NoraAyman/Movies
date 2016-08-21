package com.example.nora.movies;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 18/08/2016.
 */

public class MovieDetailsAdapter extends ArrayAdapter<MovieDetails> {

    private TextView movieTitle, movieRating;
    private String rating;
    private ImageView poster;
    private static final String MOVIE_IMAGE_BASE_URL= "http://image.tmdb.org/t/p/w780";


    public MovieDetailsAdapter(Context context, List<MovieDetails> movies){
        super(context, 0, movies);

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView= convertView;
        if(listItemView== null){
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
        }
        movieTitle= (TextView)listItemView.findViewById(R.id.movie_title);
        movieRating= (TextView)listItemView.findViewById(R.id.movie_rating);
        poster= (ImageView)listItemView.findViewById(R.id.movie_poster);
        MovieDetails currentMovie= getItem(position);
        rating = decimalFormatRating(currentMovie.getMovieRating());
        movieTitle.setText(currentMovie.getMovieTitle());
        movieRating.setText(rating);
        Picasso.with(this.getContext()).load(Uri.parse(MOVIE_IMAGE_BASE_URL + currentMovie.getImagePath())).into(poster);

        return listItemView;
    }
    private String decimalFormatRating(double rating){
        DecimalFormat decimalFormat= new DecimalFormat("0.00");
        return decimalFormat.format(rating);
    }
}
