package com.example.nora.movies;

import android.graphics.LinearGradient;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nora on 17/08/2016.
 */

public final class QueryUtils {

    private static String query; //search query
    private static Boolean movieLinkStatus= false; //open movie
    private static Boolean personLinkStatus= false; //open person
    protected QueryUtils(){
        query= "";
        movieLinkStatus= false;
        personLinkStatus= false;
    }
    public QueryUtils(String query){
        this.query= query;
//        movieLinkStatus= false;
//        personLinkStatus= false;
    }
    public QueryUtils(Boolean status){
        movieLinkStatus= status;
    }
//
//    public QueryUtils(String query, Boolean personQuery){
//        this.query= query;
////        this.movieLinkStatus= personQuery;
//
//    }

    public String getQuery(){
        return query;
    }
    public void setQuery(String q){
        this.query= q;
    }
    public Boolean getPersonQuery(){
        return this.getPersonQuery();
    }
    public void setPersonLinkStatus(Boolean status){
        this.personLinkStatus= status;
    }
    public void setMovieLinkStatus(Boolean linkStatus){
        this.movieLinkStatus= linkStatus;
    }

    public static List<MovieDetails> extractFeatureFromJSON(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        } else if ((query == null || query.isEmpty() == true) && movieLinkStatus == false) {  //default list of movies
            List<MovieDetails> movies = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON);
                JSONArray moviesArray = object.getJSONArray("results");
                for (int counter = 0; counter < moviesArray.length(); counter++) {

                    JSONObject currentMovie = moviesArray.getJSONObject(counter);

                    String title = currentMovie.getString("title");
                    double rating = currentMovie.getDouble("vote_average");
                    String posterPath = currentMovie.getString("poster_path");
                    int id = currentMovie.getInt("id");
                    Uri x = Uri.parse(posterPath);
                    MovieDetails movie = new MovieDetails(title, rating, x, id);
                    movies.add(movie);
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return movies;
        } else if (query != null && !query.isEmpty() && movieLinkStatus == false) { //search movies

            List<MovieDetails> movies = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON);
                JSONArray moviesArray = object.getJSONArray("results");
                for (int counter = 0; counter < moviesArray.length(); counter++) {

                    JSONObject currentMovie = moviesArray.getJSONObject(counter);

                    String title = currentMovie.getString("title");
                    double rating = currentMovie.getDouble("vote_average");
                    String posterPath = currentMovie.getString("poster_path");
                    int id = currentMovie.getInt("id");
                    Uri x = Uri.parse(posterPath);
                    MovieDetails movie = new MovieDetails(title, rating, x, id);
                    movies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            query = null;
            return movies;
        } else if(movieLinkStatus == true) { //open movie
            List<MovieDetails> movies = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON);
                String title = object.getString("original_title");
                String description = object.getString("overview");
                String date = object.getString("release_date");
                double rating = object.getDouble("vote_average");
                String posterPath = object.getString("backdrop_path");
                int id = object.getInt("id");
                Uri x = Uri.parse(posterPath);

                String genreName = "Genre: ";
                JSONArray genre = object.getJSONArray("genres");
                for (int i = 0; i < genre.length(); i++) {

                    JSONObject currentMovie = genre.getJSONObject(i);
                    genreName = genreName + "/ " + currentMovie.getString("name");
                }
                MovieDetails movie = new MovieDetails("Original Title:\t" + title, genreName, "Release Date:\t" + date + "\n",
                        "Description: \n \n" + description, rating, x, id);
                movies.add(movie);

                Log.e("-----------------", title);
                Log.e("genre", genreName);
                Log.e("description", description);
                Log.e("date", date);

            } catch (JSONException e) {
                e.printStackTrace();
            }
            query = null;
            movieLinkStatus= false;
            return movies;
        }
        else
            return null;
    }

    public static List<PersonDetails> extractPersonFeatureFromJSON(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        if ((query == null || query.isEmpty() == true) && personLinkStatus == false) {
            List<PersonDetails> person = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON);
                JSONArray moviesArray = object.getJSONArray("results");
                for (int counter = 0; counter < moviesArray.length(); counter++) {

                    JSONObject currentMovie = moviesArray.getJSONObject(counter);

                    String name = currentMovie.getString("name");
                    double popularity = currentMovie.getDouble("popularity");
                    String profilePath = currentMovie.getString("profile_path");
                    int id = currentMovie.getInt("id");
                    Uri x = Uri.parse(profilePath);
                    PersonDetails p = new PersonDetails(name, popularity, x, id);
                    person.add(p);
                    Log.e(name, profilePath);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            query = null;
            return person;
        }
            else if(query != null && query.isEmpty() == false && personLinkStatus == false){
                List<PersonDetails> person = new ArrayList<>();
                try {


                    JSONObject object = new JSONObject(movieJSON);
                    JSONArray moviesArray = object.getJSONArray("results");
                    for (int counter = 0; counter < moviesArray.length(); counter++) {

                        JSONObject currentMovie = moviesArray.getJSONObject(counter);

                        String name = currentMovie.getString("name");
                        double popularity = currentMovie.getDouble("popularity");
                        String profilePath = currentMovie.getString("profile_path");
                        int id = currentMovie.getInt("id");
                        Uri x = Uri.parse(profilePath);
                        PersonDetails p = new PersonDetails(name, popularity, x, id);
                        person.add(p);

                    }
                    } catch (JSONException e) {
                    e.printStackTrace();
                }
                query= null;
                personLinkStatus= false;
                return person;
            }
        else if(personLinkStatus == true) {
            List<PersonDetails> person = new ArrayList<>();
            try {
                JSONObject object = new JSONObject(movieJSON);
                String name = object.getString("name");
                String birthday= object.getString("birthday");
                String dateOfDeath= object.getString("deathday");
                String placeOfBirth= object.getString("place_of_birth");
                String biography = object.getString("biography");
                String posterPath = object.getString("profile_path");
                int id = object.getInt("id");
                Uri x = Uri.parse(posterPath);


                PersonDetails p = new PersonDetails("Name:\t" + name, "Date of Birth:\t" + birthday,
                        "Place of Birth:\t" + placeOfBirth + "\n",
                        "Biography: \n \n" + biography,id, x, "Date of Death:\t" + dateOfDeath);
                person.add(p);


            } catch (JSONException e) {
                e.printStackTrace();
            }
            query = null;
            personLinkStatus= false;
            return person;
        }
        else
            return null;
    }
    private static URL createUrl(String urlString) {
        URL url = null;
        try {
            url = new URL(urlString);
        } catch (MalformedURLException e) {
            Log.e("", "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e("", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("", "Problem retrieving the movie JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
    public static List<MovieDetails> fetchMovieData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("", "Problem making the HTTP request.", e);
        }

        List<MovieDetails> movies = extractFeatureFromJSON(jsonResponse);

        return movies;
    }
    public static List<PersonDetails> fetchPersonData(String requestUrl) {

        URL url = createUrl(requestUrl);
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("", "Problem making the HTTP request.", e);
        }

        List<PersonDetails> person = extractPersonFeatureFromJSON(jsonResponse);

        return person;
    }
}
