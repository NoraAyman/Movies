package com.example.nora.movies;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import Loaders.MovieDetailsLoader;
import Loaders.PersonDetailsLoader;
import adapters.PersonListDetailsAdapter;

/**
 * Created by Nora on 22/08/2016.
 */

public class PopularPersonActivity  extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, LoaderManager.LoaderCallbacks<List<PersonDetails>> {
    private static final String THEMOVIEDB_POPULAR__PERSONS_REQUEST_URL = "http://api.themoviedb.org/3/person/popular?api_key=55457b0f046c368efeaa2744b0a8eb5f";
    private PersonListDetailsAdapter adapter;
    private LoaderManager loader_manager;
    private ListView list_view;
    private TextView emptyStateView;
    private View progressBar;
    private SearchView search_view;
    private Toolbar mainToolbar;
    @Override
    public void onLoaderReset(Loader<List<PersonDetails>> loader) {
        adapter.clear();
    }

    @Override
    public void onLoadFinished(Loader<List<PersonDetails>> loader, List<PersonDetails> data) {
        progressBar.setVisibility(View.GONE);
        adapter.clear();
        if (data != null && !data.isEmpty()) {
            adapter.addAll(data);
        } else {
            //emptyStateView.setText(R.string.no_earthquakes);
        }
    }

    @Override
    public Loader<List<PersonDetails>> onCreateLoader(int i, Bundle bundle) {
        QueryUtils status= new QueryUtils(true);
        status.setPersonLinkStatus(false);
        return new PersonDetailsLoader(this, THEMOVIEDB_POPULAR__PERSONS_REQUEST_URL);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list_view = (ListView) findViewById(R.id.list);
        final ActionBar toolbar = getSupportActionBar();
        toolbar.setSubtitle(getResources().getString(R.string.popular_people));
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        progressBar = (View) findViewById(R.id.loading_indicator);
        ImageButton searchButton= (ImageButton)findViewById(R.id.search) ;
        search_view= (SearchView) findViewById(R.id.search_view);
        search_view.setVisibility(View.GONE);
        mainToolbar= (Toolbar)findViewById(R.id.main_toolbar);
        mainToolbar.setVisibility(View.GONE);

        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                // .setAction("Action", null).show();
                if( search_view.getVisibility()== View.VISIBLE){
                    search_view.setVisibility(View.GONE);
                    mainToolbar.setVisibility(View.GONE);
                }
                else if(search_view.getVisibility()== View.GONE){
                    search_view.setVisibility(View.VISIBLE);
                    mainToolbar.setVisibility(View.GONE);
                }


            }
        });
        search_view.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                SearchablePersonActivity object= new SearchablePersonActivity(s);
                object.setQuery(s);
                Intent x= new Intent(list_view.getContext(), SearchablePersonActivity.class);
                startActivity(x);

                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                SearchablePersonActivity object= new SearchablePersonActivity(s);
                object.setQuery(s);
                return false;
            }
        });
        adapter = new PersonListDetailsAdapter(this, new ArrayList<PersonDetails>());
        list_view.setAdapter(adapter);

        list_view.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                PersonDetails currentPerson = adapter.getItem(i);
                OpenPersonLinkActivity object= new OpenPersonLinkActivity(currentPerson.getId());
                Intent openLink= new Intent(list_view.getContext(), OpenPersonLinkActivity.class);
                startActivity(openLink);

            }
        });
        ConnectivityManager connectivity_manager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo network_info = connectivity_manager.getActiveNetworkInfo();
        if (network_info != null && network_info.isConnected()) {
            loader_manager = getLoaderManager();
            loader_manager.initLoader(0, null, this);
        } else {
            emptyStateView = (TextView) findViewById(R.id.empty_view);
            progressBar.setVisibility(View.GONE);
            list_view.setEmptyView(emptyStateView);
            emptyStateView.setText(R.string.no_internet_connection);
        }

    }
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
