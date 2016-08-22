package com.example.nora.movies;

import android.net.Uri;

/**
 * Created by Nora on 18/08/2016.
 */

public class MovieDetails {

    private String name, movieTitle;
    private double movieRating;
    private Uri imagePath;
    private int id;
    public MovieDetails(String movieTitle, double movieRating, Uri imagePath, int id){

        this.movieTitle= movieTitle;
        this.movieRating= movieRating;
        this.imagePath= imagePath;
        this.id= id;

    }
    public MovieDetails(String name, String movieTitle, double movieRating, Uri imagePath, int id){

        this.name= name;
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
    public Uri getImagePath()
    { return  this.imagePath;
    }
    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
}
