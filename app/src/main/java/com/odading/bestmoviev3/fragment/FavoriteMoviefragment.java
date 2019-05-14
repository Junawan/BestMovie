package com.odading.bestmoviev3.fragment;

import android.content.Context;
import android.database.ContentObserver;
import android.database.Cursor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.odading.bestmoviev3.helper.LoadFavoriteCallback;
import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.adapter.FavoriteAdapter;
import com.odading.bestmoviev3.db.FavoriteHelper;
import com.odading.bestmoviev3.prcelable.MovieModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Objects;

import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.odading.bestmoviev3.helper.MappingHelper.mapCursorToArrayList;

public class FavoriteMoviefragment extends Fragment implements LoadFavoriteCallback {
    private RecyclerView rvFavorite;
    private ProgressBar progressBar;
    private static final String EXTRA_STATE = "EXTRA_STATE";
    private FavoriteAdapter favoriteAdapter;
    private FavoriteHelper favoriteHelper;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_movie, container, false);

        rvFavorite = view.findViewById(R.id.rv_list_favourite_movie);
        rvFavorite.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavorite.setHasFixedSize(true);

        favoriteHelper = FavoriteHelper.getInstance(Objects.requireNonNull(getActivity()).getApplicationContext());
        favoriteHelper.open();

        progressBar = view.findViewById(R.id.progressBarLoading);

        favoriteAdapter = new FavoriteAdapter(getActivity());
        rvFavorite.setAdapter(favoriteAdapter);

        if (savedInstanceState == null) {
            new LoadFavAsync(getActivity(), this).execute();
        } else {
            ArrayList<MovieModel> list = savedInstanceState.getParcelableArrayList(EXTRA_STATE);
            if (list != null) {
                favoriteAdapter.setListFavorite(list);
            }
        }

        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATE, favoriteAdapter.getListFavorite());
    }

    @Override
    public void preExecute() {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }

    @Override
    public void postExecute(Cursor movie) {
        progressBar.setVisibility(View.INVISIBLE);

        ArrayList<MovieModel> listMovie = mapCursorToArrayList(movie);
        if (listMovie.size() > 0) {
            favoriteAdapter.setListFavorite(listMovie);
        } else {
            favoriteAdapter.setListFavorite(new ArrayList<MovieModel>());
            showSnackbarMessage("Tidak ada data saat ini");
        }

    }

    private void showSnackbarMessage(String message) {
        Snackbar.make(rvFavorite, message, Snackbar.LENGTH_SHORT).show();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        favoriteHelper.close();
    }

    private static class LoadFavAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadFavoriteCallback> weakCallback;

        private LoadFavAsync(Context context, LoadFavoriteCallback callback) {
            weakContext = new WeakReference<>(context);
            weakCallback = new WeakReference<>(callback);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            weakCallback.get().preExecute();
        }

        @Override
        protected Cursor doInBackground(Void... voids) {
            Context context = weakContext.get();
            return context.getContentResolver().query(CONTENT_URI, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movie) {
            super.onPostExecute(movie);

            weakCallback.get().postExecute(movie);
        }
    }

    public static class DataObserver extends ContentObserver {

        final Context context;

        public DataObserver(Handler handler, Context context) {
            super(handler);
            this.context = context;
        }

        @Override
        public void onChange(boolean selfChange) {
            super.onChange(selfChange);
            new LoadFavAsync(context, (LoadFavoriteCallback) context).execute();

        }
    }
}
