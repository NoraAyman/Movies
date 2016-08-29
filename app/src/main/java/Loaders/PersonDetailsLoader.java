package Loaders;

import android.content.Context;

import com.example.nora.movies.MovieDetails;
import com.example.nora.movies.PersonDetails;
import com.example.nora.movies.QueryUtils;

import java.util.List;

/**
 * Created by Nora on 28/08/2016.
 */

public class PersonDetailsLoader extends android.content.AsyncTaskLoader<List<PersonDetails>> {

    private String url;
    public PersonDetailsLoader(Context context, String url){
        super(context);
        this.url= url;
    }
    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public List<PersonDetails> loadInBackground() {
        if (url== null) {
            return null;
        }

        List<PersonDetails> result = QueryUtils.fetchPersonData(url);
        return result;
    }
}
