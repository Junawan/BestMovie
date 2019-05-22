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
import com.odading.bestmoviev3.adapter.SearchTvAdapter;
import com.odading.bestmoviev3.aloader.SearchTvAsyncTaskLoader;
import com.odading.bestmoviev3.item.SearchTvItem;

import java.util.ArrayList;

public class SearchTvActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<SearchTvItem>>, View.OnClickListener {
    RecyclerView rvSearch;
    SearchTvAdapter searchTvAdapter;

    public static final String EXTRAS_TITLES = "EXTRAS_TITLES";
    private Button btnscanQr;
    IntentIntegrator intentIntegrator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_tv);

        setActinBarTitle("Search Tv");

        rvSearch = findViewById(R.id.rv_search_tv);
        rvSearch.setHasFixedSize(true);
        rvSearch.setLayoutManager(new LinearLayoutManager(this));

        searchTvAdapter = new SearchTvAdapter(this);
        searchTvAdapter.notifyDataSetChanged();
        rvSearch.setAdapter(searchTvAdapter);

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
    public Loader<ArrayList<SearchTvItem>> onCreateLoader(int i, @Nullable Bundle bundle) {
        String titles = "-";
        if (bundle != null) {
            titles = bundle.getString(EXTRAS_TITLES);
        }
        return new SearchTvAsyncTaskLoader(this, titles);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<SearchTvItem>> loader, ArrayList<SearchTvItem> data) {
        searchTvAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<SearchTvItem>> loader) {
        searchTvAdapter.setData(null);
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
                    getSupportLoaderManager().restartLoader(0, bundle, SearchTvActivity.this);

                    Toast.makeText(SearchTvActivity.this, qrCode, Toast.LENGTH_SHORT).show();
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
