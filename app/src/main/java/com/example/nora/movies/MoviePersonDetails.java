package com.example.nora.movies;

import android.net.Uri;

/**
 * Created by Nora on 18/08/2016.
 */

public class MoviePersonDetails {

    private String name, movieTitle, genre, movieDescription, dateOfBirth, placeOfBirth, biography, dateOfDeath;
    private double movieRating;
    private Uri imagePath, personPicture;
    private int id;
    private String releaseDate;
    public  MoviePersonDetails(String name, String dateOfBirth, String placeOfBirth,
                               String biography, int id, Uri personPicture, String dateOfDeath){
        this.name= name;
        this.dateOfBirth= dateOfBirth;
        this.placeOfBirth= placeOfBirth;
        this.biography= biography;
        this.id= id;
        this.personPicture= personPicture;
        this.dateOfDeath= dateOfDeath;
    }
    public MoviePersonDetails(String movieTitle, double movieRating, Uri imagePath, int id){

        this.movieTitle= movieTitle;
        this.movieRating= movieRating;
        this.imagePath= imagePath;
        this.id= id;

    }
    public MoviePersonDetails(String name, String movieTitle, double movieRating, Uri imagePath, int id){

        this.name= name;
        this.movieTitle= movieTitle;
        this.movieRating= movieRating;
        this.imagePath= imagePath;
        this.id= id;

    }
    public MoviePersonDetails(String movieTitle, String genre, String releaseDate,
                              String movieDescription, double movieRating, Uri imagePath, int id){

        this.movieTitle= movieTitle;
        this.movieRating= movieRating;
        this.imagePath= imagePath;
        this.id= id;
        this.genre= genre;
        this.releaseDate= releaseDate;
        this.movieDescription= movieDescription;

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
    public Uri getPersonPicture(){ return this.personPicture; }
    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getGenre(){
        return this.genre;
    }
    public String getReleaseDate(){
        return this.releaseDate;
    }

    public String getMovieDescription() {
        return this.movieDescription;
    }
    public String getDateOfBirth(){ return this.dateOfBirth; }
    public String getPlaceOfBirth(){ return this.placeOfBirth; }
    public String getBiography(){ return this.biography; }
    public String getDateOfDeath(){ return this.dateOfDeath; }
}
