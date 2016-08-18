package com.example.nora.movies;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
/**
 * Created by Nora on 18/08/2016.
 */

public class PopularActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderCallbacks<List<MovieDetails>>{
    private static final String THEMOVIEDB_POPULAR_MOVIES_REQUEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=55457b0f046c368efeaa2744b0a8eb5f";
    private MovieDetailsAdapter adapter;
    private LoaderManager loader_manager;
    private ListView list_view;
    private TextView emptyStateView;
    private View progressBar;
    @Override
    public void onLoaderReset(Loader<List<MovieDetails>> loader) {
        adapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<MovieDetails>> loader, List<MovieDetails> data) {
        progressBar.setVisibility(View.GONE);
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        }
        else{
            //emptyStateView.setText(R.string.no_earthquakes);
        }
    }

    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int i, Bundle bundle) {
        return new MovieLoader(this, THEMOVIEDB_POPULAR_MOVIES_REQUEST_URL);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        list_view= (ListView)findViewById(R.id.list);
        progressBar= (View)findViewById(R.id.loading_indicator);

        adapter= new MovieDetailsAdapter(this, new ArrayList<MovieDetails>());
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieDetails currentMovie= adapter.getItem(i);
            }
        });
        ConnectivityManager connectivity_manager= (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network_info= connectivity_manager.getActiveNetworkInfo();
        if(network_info != null && network_info.isConnected()){
            loader_manager= getLoaderManager();
            loader_manager.initLoader(0, null, this);
        }
        else
        {
            emptyStateView=(TextView)findViewById(R.id.empty_view);
            progressBar.setVisibility(View.GONE);
            list_view.setEmptyView(emptyStateView);
            emptyStateView.setText(R.string.no_internet_connection);
        }
    }
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular) {
            Intent popular= new Intent(list_view.getContext(), PopularActivity.class);
            this.startActivity(popular);
        } else if (id == R.id.nav_top_rated) {
            Intent topRated= new Intent(list_view.getContext(), TopRatedActivity.class);
            this.startActivity(topRated);

        } else if (id == R.id.nav_now_playing) {
            Intent nowPlaying= new Intent(list_view.getContext(), NowPlayingActivity.class);
            this.startActivity(nowPlaying);

        } else if (id == R.id.nav_people) {

        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
