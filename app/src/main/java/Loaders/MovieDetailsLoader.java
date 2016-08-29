package Loaders;

import android.content.Context;

import com.example.nora.movies.MovieDetails;
import com.example.nora.movies.QueryUtils;

import java.util.List;

/**
 * Created by Nora on 18/08/2016.
 */

public class MovieDetailsLoader extends android.content.AsyncTaskLoader<List<MovieDetails>> {

    private String url;
    public MovieDetailsLoader(Context context, String url){
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
