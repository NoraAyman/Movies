package com.example.nora.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;
import java.util.List;

import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import Loaders.MovieDetailsLoader;
import adapters.MovieListDetailsAdapter;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener , LoaderCallbacks<List<MovieDetails>>{

    private static final String THEMOVIEDB_POPULAR_MOVIES_REQUEST_URL = "http://api.themoviedb.org/3/movie/popular?api_key=55457b0f046c368efeaa2744b0a8eb5f";
    private MovieListDetailsAdapter adapter;
    private LoaderManager loader_manager;
    private ListView list_view;
    private TextView emptyStateView;
    private View progressBar;
    private SearchView search_view;

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
            adapter.notifyDataSetChanged();
        }
        else{
            //emptyStateView.setText(R.string.no_earthquakes);
        }
    }

    @Override
    public Loader<List<MovieDetails>> onCreateLoader(int i, Bundle bundle) {
        return new MovieDetailsLoader(this, THEMOVIEDB_POPULAR_MOVIES_REQUEST_URL);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setSubtitle(getResources().getString(R.string.popular_movies));
        ImageButton searchButton= (ImageButton)findViewById(R.id.search) ;
        search_view= (SearchView) findViewById(R.id.search_view);
        search_view.setVisibility(View.GONE);
        RelativeLayout rel= (RelativeLayout)findViewById(R.id.relative);
        rel.setPadding(0, 167,0,0);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if( search_view.getVisibility()== View.VISIBLE){
                    search_view.setVisibility(View.GONE);
                }
                else if(search_view.getVisibility()== View.GONE){
                    search_view.setVisibility(View.VISIBLE);
                }

            }
        });
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        list_view= (ListView)findViewById(R.id.list);
        progressBar= (View)findViewById(R.id.loading_indicator);

        adapter= new MovieListDetailsAdapter(this, new ArrayList<MovieDetails>());
        list_view.setAdapter(adapter);
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchableMovieActivity object= new SearchableMovieActivity(s);
                object.setQuery(s);
                Intent x= new Intent(list_view.getContext(), SearchableMovieActivity.class);
                startActivity(x);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchableMovieActivity object= new SearchableMovieActivity(s);
                object.setQuery(s);
                return false;
            }
        });
        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                MovieDetails currentMovie= adapter.getItem(i);
               // adapter.setLinkStatus(true);
                OpenMovieLinkActivity object= new OpenMovieLinkActivity(currentMovie.getId(), true);
                Intent openLink= new Intent(list_view.getContext(), OpenMovieLinkActivity.class);
                startActivity(openLink);
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
        adapter.clear();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_popular) {
            Intent popular= new Intent(list_view.getContext(), PopularMoviesActivity.class);
            this.startActivity(popular);
        } else if (id == R.id.nav_top_rated) {
            Intent topRated= new Intent(list_view.getContext(), TopRatedActivity.class);
            this.startActivity(topRated);

        } else if (id == R.id.nav_now_playing) {
            Intent nowPlaying= new Intent(list_view.getContext(), NowPlayingActivity.class);
            this.startActivity(nowPlaying);

        } else if (id == R.id.nav_people) {
            Intent popularPerson= new Intent(list_view.getContext(), PopularPersonActivity.class);
            this.startActivity(popularPerson);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


}
