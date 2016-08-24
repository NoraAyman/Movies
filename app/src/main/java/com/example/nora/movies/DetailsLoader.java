package com.example.nora.movies;

import android.content.Context;

import java.util.List;

/**
 * Created by Nora on 18/08/2016.
 */

public class DetailsLoader extends android.content.AsyncTaskLoader<List<MoviePersonDetails>> {

    private String url;
    public DetailsLoader(Context context, String url){
        super(context);
        this.url= url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<MoviePersonDetails> loadInBackground() {
        if (url== null) {
            return null;
        }

        List<MoviePersonDetails> result = QueryUtils.fetchMovieData(url);
        return result;
    }
}
