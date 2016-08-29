package adapters;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.nora.movies.MovieDetails;
import com.example.nora.movies.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nora on 28/08/2016.
 */

public class MovieDetailsAdapter extends ArrayAdapter<MovieDetails> {

    private TextView movieTitle, movieRating, movieGenre, movieReleaseDate, movieDescription;
    private String rating;
    private ImageView poster;
    private static Boolean linkStatus= false, personStatus= false;
    private static final String MOVIE_IMAGE_BASE_URL= "http://image.tmdb.org/t/p/w780";


    @Override
    public int getPosition(MovieDetails item) {
        return super.getPosition(item);
    }
    public MovieDetailsAdapter(Context context, List<MovieDetails> movies){
        super(context, 0, movies);

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView= convertView;
        if(listItemView== null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items_link_opened_movie, parent, false);
        }
        movieRating= (TextView)listItemView.findViewById(R.id.movie_rating);
        movieGenre= (TextView)listItemView.findViewById(R.id.movie_genre);
        movieReleaseDate= (TextView)listItemView.findViewById(R.id.movie_release_date);
        movieDescription= (TextView)listItemView.findViewById(R.id.movie_description);
        movieTitle= (TextView)listItemView.findViewById(R.id.movie_title);

        poster= (ImageView)listItemView.findViewById(R.id.movie_poster);
        MovieDetails currentMovie= getItem(position);
        rating = decimalFormatRating(currentMovie.getMovieRating());
        movieTitle.setText(currentMovie.getMovieTitle());
        movieRating.setText(rating);
        movieGenre.setText(currentMovie.getGenre());
        movieDescription.setText(currentMovie.getMovieDescription());
        movieReleaseDate.setText(currentMovie.getReleaseDate());
        Picasso.with(this.getContext()).load(Uri.parse(MOVIE_IMAGE_BASE_URL + currentMovie.getImagePath())).into(poster);
       // this.linkStatus= false;

        return listItemView;

    }
    private String decimalFormatRating(double rating){
        DecimalFormat decimalFormat= new DecimalFormat("0.00");
        return decimalFormat.format(rating);
    }
    private String formatDate(Date dateObject) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("LLL ,dd, yyyy");
        return dateFormat.format(dateObject);
    }
    public void setLinkStatus(Boolean linkStatus){
        this.linkStatus= linkStatus;
    }
    public void setPersonStatus(Boolean personStatus){ this.personStatus= personStatus; }
}


