package com.example.nora.movies;

import android.net.Uri;

/**
 * Created by Nora on 18/08/2016.
 */

public class MovieDetails {

    private String movieTitle;
    private double movieRating;
    private Uri imagePath;
    private int id;
    public MovieDetails(String movieTitle, double movieRating, Uri imagePath, int id){

        this.movieTitle= movieTitle;
        this.movieRating= movieRating;
        this.imagePath= imagePath;
        this.id= id;

    }
    public String getMovieTitle(){
        return this.movieTitle;
    }
    public double getMovieRating(){
        return this.movieRating;
    }
    public Uri getImagePath(){ return  this.imagePath; }
    public int getId(){ return this.id; }
}
