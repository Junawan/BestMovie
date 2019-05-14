package com.odading.bestmoviev3.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.adapter.MovieAdapter;
import com.odading.bestmoviev3.aloader.MyAsynctaskLoader;
import com.odading.bestmoviev3.item.MovieItems;

import java.util.ArrayList;

public class ListMovieFragment extends Fragment implements LoaderManager.LoaderCallbacks<ArrayList<MovieItems>> {
    MovieAdapter movieAdapter;
    private RecyclerView rvCategory;
    ProgressBar progressBar;
    static final String EXTRAS_MOVIE = "extras_movie";

    public ListMovieFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_list_movie, container, false);
        progressBar = view.findViewById(R.id.progressBarLoading);

        rvCategory = view.findViewById(R.id.rv_list_movie);
        rvCategory.setHasFixedSize(true);
        rvCategory.setLayoutManager(new LinearLayoutManager(getActivity()));
        movieAdapter = new MovieAdapter(getActivity());
        movieAdapter.notifyDataSetChanged();
        rvCategory.setAdapter(movieAdapter);


        Bundle bundle = new Bundle();
        bundle.putString(EXTRAS_MOVIE, null);

        getLoaderManager().initLoader(0, bundle, this);
        return view;
    }

    @Override
    public Loader<ArrayList<MovieItems>> onCreateLoader(int id, Bundle args) {
        progressBar.setVisibility(View.VISIBLE);
        String movies = "";
        if (args == null) {
            movies = args.getString(EXTRAS_MOVIE);
        }
        return new MyAsynctaskLoader(getContext(), movies);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<MovieItems>> loader, ArrayList<MovieItems> data) {
        progressBar.setVisibility(View.GONE);
        if (data != null) {
            movieAdapter.setData(data);
        } else if (data.isEmpty()) {
            showErrorMessage();
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<MovieItems>> loader) {
        progressBar.setVisibility(View.GONE);
        movieAdapter.setData(null);
    }

    private void showErrorMessage() {
        Toast.makeText(getContext(), "Request time out, please try again", Toast.LENGTH_SHORT).show();
    }
}
