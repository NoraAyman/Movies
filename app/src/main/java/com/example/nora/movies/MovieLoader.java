package com.example.nora.movies;

import android.content.Context;

import java.util.List;

/**
 * Created by Nora on 18/08/2016.
 */

public class MovieLoader extends android.content.AsyncTaskLoader<List<MovieDetails>> {

    private String url;
    public MovieLoader(Context context, String url){
        super(context);
        this.url= url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MovieDetails> loadInBackground() {
        if (url== null) {
            return null;
        }

        List<MovieDetails> result = QueryUtils.fetchMovieData(url);
        return result;
    }
}
