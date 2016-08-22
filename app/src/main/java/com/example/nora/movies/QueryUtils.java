package com.example.nora.movies;

import android.graphics.Movie;
import android.net.Uri;
import android.support.v4.content.FileProvider;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

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

    private static String query;
    private static Boolean personQuery= false;
    private QueryUtils(){
        query= "";
        personQuery= false;
    }
    public QueryUtils(String query){
        this.query= query;
        personQuery= false;
    }
    public QueryUtils(Boolean status){
        personQuery= status;
    }
    public QueryUtils(String query, Boolean personQuery){
        this.query= query;
        this.personQuery= personQuery;

    }
    public String getQuery(){
        return query;
    }
    public void setQuery(String q){
        this.query= q;
    }
    public Boolean getPersonQuery(){
        return this.getPersonQuery();
    }
    public void setPersonQuery(Boolean status){
        this.personQuery= status;
    }
    public static List<MovieDetails> extractFeatureFromJSON(String movieJSON) {

        if (TextUtils.isEmpty(movieJSON)) {
            return null;
        }
        else if(query == null && personQuery==false){
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
        }

        else if(query != null && !query.isEmpty() && personQuery==false) {

            List<MovieDetails> movies = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON );
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
        }
        else if(query != null && !query.isEmpty() && personQuery== true){
            List<MovieDetails> movies = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON );
                JSONArray moviesArray = object.getJSONArray("results");
                for (int counter = 0; counter < moviesArray.length(); counter++) {

                    JSONObject currentMovie = moviesArray.getJSONObject(counter);

                    String name = currentMovie.getString("name");
                    double popularity = currentMovie.getDouble("popularity");
                    String profilePath = currentMovie.getString("profile_path");
                    int id = currentMovie.getInt("id");
                    Uri x = Uri.parse(profilePath);
                    MovieDetails movie = new MovieDetails(name, popularity, x, id);
                    movies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            personQuery= false;
            return movies;
        }
        else if(personQuery== true){
            List<MovieDetails> movies = new ArrayList<>();
            try {

                JSONObject object = new JSONObject(movieJSON );
                JSONArray moviesArray = object.getJSONArray("results");
                for (int counter = 0; counter < moviesArray.length(); counter++) {

                    JSONObject currentMovie = moviesArray.getJSONObject(counter);

                    String name = currentMovie.getString("name");
                    double popularity = currentMovie.getDouble("popularity");
                    String profilePath = currentMovie.getString("profile_path");
                    int id = currentMovie.getInt("id");
                    Uri x = Uri.parse(profilePath);
                    MovieDetails movie = new MovieDetails(name, popularity, x, id);
                    movies.add(movie);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            personQuery= false;
            return movies;
        }

        else
        Log.e("hjvk","gkhj");
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
}
