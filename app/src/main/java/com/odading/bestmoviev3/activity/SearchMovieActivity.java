package com.odading.bestmoviev3.activity;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.widget.Toast;

import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.adapter.SearchAdapter;
import com.odading.bestmoviev3.aloader.SearchAsyncTaskLoader;
import com.odading.bestmoviev3.item.SearchItem;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SearchItem>> {
    RecyclerView rvSearch;
    SearchAdapter searchAdapter;

    public static final String EXTRAS_TITLES = "EXTRAS_TITLES";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_movie);
        setActinBarTitle("Search Movie");

        rvSearch = findViewById(R.id.rv_search_movie);
        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));

        searchAdapter = new SearchAdapter(this);
        searchAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(searchAdapter);

        Bundle bundle = new Bundle();
        String title = bundle.getString(EXTRAS_TITLES);

        bundle.putString(EXTRAS_TITLES, title);
        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    private void setActinBarTitle(String titleBar) {
        getSupportActionBar().setTitle(titleBar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        if (searchManager != null) {
            SearchView searchView = (SearchView) (menu.findItem(R.id.search)).getActionView();
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setQueryHint(getResources().getString(R.string.search_hint));
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_TITLES, query);
                    getSupportLoaderManager().restartLoader(0, bundle, SearchMovieActivity.this);

                    Toast.makeText(SearchMovieActivity.this, query, Toast.LENGTH_SHORT).show();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newtext) {
                    return false;
                }
            });
        }
        return super.onCreateOptionsMenu(menu);
    }

    @NonNull
    @Override
    public Loader<ArrayList<SearchItem>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String titles = "-";
        if (bundle != null) {
            titles = bundle.getString(EXTRAS_TITLES);
        }
        return new SearchAsyncTaskLoader(this, titles);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SearchItem>> loader, ArrayList<SearchItem> data) {
        searchAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SearchItem>> loader) {
        searchAdapter.setData(null);
    }
}
