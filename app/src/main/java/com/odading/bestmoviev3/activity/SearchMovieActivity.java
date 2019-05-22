package com.odading.bestmoviev3.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.adapter.SearchAdapter;
import com.odading.bestmoviev3.aloader.SearchAsyncTaskLoader;
import com.odading.bestmoviev3.item.SearchItem;

import java.util.ArrayList;

public class SearchMovieActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SearchItem>>, View.OnClickListener {
    private RecyclerView rvSearch;
    private SearchAdapter searchAdapter;
    private Button btnscanQr;
    IntentIntegrator intentIntegrator;

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

        btnscanQr = findViewById(R.id.button_scan);
        btnscanQr.setOnClickListener(this);

        Bundle bundle = new Bundle();
        String title = bundle.getString(EXTRAS_TITLES);

        bundle.putString(EXTRAS_TITLES, title);
        getSupportLoaderManager().initLoader(0, bundle, this);
    }

    private void setActinBarTitle(String titleBar) {
        getSupportActionBar().setTitle(titleBar);
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

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.button_scan) {
            intentIntegrator = new IntentIntegrator(this);
            intentIntegrator.initiateScan();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(this, "Hasil tidak ditemukan", Toast.LENGTH_SHORT).show();
            }else{
                // jika qrcode berisi data
                try{
                    String qrCode = result.getContents();
                    Bundle bundle = new Bundle();
                    bundle.putString(EXTRAS_TITLES, qrCode);
                    getSupportLoaderManager().restartLoader(0, bundle, SearchMovieActivity.this);

                    Toast.makeText(SearchMovieActivity.this, qrCode, Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    e.printStackTrace();
                    // jika format encoded tidak sesuai maka hasil
                    // ditampilkan ke toast
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_SHORT).show();
                }
            }
        }else{
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
