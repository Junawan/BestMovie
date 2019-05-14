package com.odading.bestmoviev3.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.adapter.TvAdapter;
import com.odading.bestmoviev3.aloader.TvAsynctaskLoader;
import com.odading.bestmoviev3.item.TvItems;

import java.util.ArrayList;


public class ListTvShowFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<TvItems>> {
    TvAdapter tvAdapter;
    private RecyclerView rvCategory;
    ProgressBar progressBar;

    static final String EXTRAS_TV = "extras_movie";

    public ListTvShowFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_tv_show, container, false);
        rvCategory = view.findViewById(R.id.rv_list_tvshow);
        progressBar = view.findViewById(R.id.progres_bar_tv);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        tvAdapter = new TvAdapter(getActivity());
        tvAdapter.notifyDataSetChanged();
        rvCategory.setAdapter(tvAdapter);

        rvCategory.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.GONE);

        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_TV, null);

        getLoaderManager().initLoader(0, bundle, this);

        return view;
    }



    @NonNull
    @Override
    public Loader<ArrayList<TvItems>> onCreateLoader(int id, @Nullable Bundle args) {
        progressBar.setVisibility(View.VISIBLE);

        String tv = "";
        if (args != null) {
            tv = args.getString(EXTRAS_TV);
        }
        return new TvAsynctaskLoader(getContext(), tv);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<TvItems>> loader, ArrayList<TvItems> data) {
        rvCategory.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.GONE);
        tvAdapter.setData(data);
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<TvItems>> loader) {
        progressBar.setVisibility(View.GONE);
        tvAdapter.setData(null);
    }
}
