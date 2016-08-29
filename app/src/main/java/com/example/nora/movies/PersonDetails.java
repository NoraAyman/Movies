package com.example.nora.movies;

import android.net.Uri;

/**
 * Created by Nora on 29/08/2016.
 */

public class PersonDetails {
    private String name, dateOfBirth, placeOfBirth, biography, dateOfDeath;
    private double popularity;
    private Uri personPicture;
    private int id;
    public PersonDetails(String name, String dateOfBirth, String placeOfBirth,
                              String biography, int id, Uri personPicture, String dateOfDeath){
        this.name= name;
        this.dateOfBirth= dateOfBirth;
        this.placeOfBirth= placeOfBirth;
        this.biography= biography;
        this.id= id;
        this.personPicture= personPicture;
        this.dateOfDeath= dateOfDeath;
    }

    public PersonDetails(String name, double popularity, Uri personPicture, int id){

        this.name= name;
        this.popularity= popularity;
        this.personPicture= personPicture;
        this.id= id;

    }
    public Uri getPersonPicture(){ return this.personPicture; }
    public int getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }
    public String getDateOfBirth(){ return this.dateOfBirth; }
    public String getPlaceOfBirth(){ return this.placeOfBirth; }
    public String getBiography(){ return this.biography; }
    public String getDateOfDeath(){ return this.dateOfDeath; }

    public double getPopularity() {
        return this.popularity;
    }
}
