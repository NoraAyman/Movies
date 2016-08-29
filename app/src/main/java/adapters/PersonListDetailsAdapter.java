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
import com.example.nora.movies.PersonDetails;
import com.example.nora.movies.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Nora on 28/08/2016.
 */

public class PersonListDetailsAdapter extends ArrayAdapter<PersonDetails> {

    private TextView personName, popularity;
    private String rating;
    private ImageView personPicture;
    private static Boolean linkStatus= false, personStatus= false;
    private static final String MOVIE_IMAGE_BASE_URL= "http://image.tmdb.org/t/p/w780";


    @Override
    public int getPosition(PersonDetails item) {
        return super.getPosition(item);
    }
    public PersonListDetailsAdapter(Context context, List<PersonDetails> person){
        super(context, 0, person);

    }
    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View listItemView= convertView;
        if(listItemView== null) {
            listItemView = LayoutInflater.from(getContext()).inflate(R.layout.list_items, parent, false);
            }
            personName = (TextView) listItemView.findViewById(R.id.movie_title);
            popularity = (TextView) listItemView.findViewById(R.id.movie_rating);
            personPicture = (ImageView) listItemView.findViewById(R.id.movie_poster);
            PersonDetails currentPerson = getItem(position);
            rating = decimalFormatRating(currentPerson.getPopularity());
            personName.setText(currentPerson.getName());
            popularity.setText(rating);
            Picasso.with(this.getContext()).load(Uri.parse(MOVIE_IMAGE_BASE_URL + currentPerson.getPersonPicture())).into(personPicture);


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


