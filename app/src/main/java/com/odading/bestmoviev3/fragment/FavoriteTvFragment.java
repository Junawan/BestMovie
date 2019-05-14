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

import com.odading.bestmoviev3.helper.LoadTvCallbak;
import com.odading.bestmoviev3.R;
import com.odading.bestmoviev3.adapter.FavoriteTvAdapter;
import com.odading.bestmoviev3.db.TvHelper;
import com.odading.bestmoviev3.prcelable.TvModel;

import java.lang.ref.WeakReference;
import java.util.ArrayList;

import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI_TV;
import static com.odading.bestmoviev3.helper.MappingHelper.mapCursorToArrayListTv;

public class FavoriteTvFragment extends Fragment implements LoadTvCallbak {
    private RecyclerView rvFavoriteTv;
    private ProgressBar progressBar;
    private static final String EXTRA_STATETV = "EXTRA_STATETV";
    private FavoriteTvAdapter favoriteTvAdapter;
    private TvHelper tvHelper;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_favorite_tv, container, false);

        rvFavoriteTv = view.findViewById(R.id.rv_list_favourite_tv);
        rvFavoriteTv.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvFavoriteTv.setHasFixedSize(true);

        tvHelper = TvHelper.getInstance(getActivity());
        tvHelper.open();

        progressBar = view.findViewById(R.id.progressBarLoadingTv);

        favoriteTvAdapter = new FavoriteTvAdapter(getActivity());
        rvFavoriteTv.setAdapter(favoriteTvAdapter);

        if (savedInstanceState == null) {
            new LoadTvAsync(getActivity(), this).execute();
        } else {
            ArrayList<TvModel> list = savedInstanceState.getParcelableArrayList(EXTRA_STATETV);
            if (list != null) {
                favoriteTvAdapter.setListFavoriteTv(list);
            }
        }
        return view;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelableArrayList(EXTRA_STATETV, favoriteTvAdapter.getListFavoriteTv());
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

        ArrayList<TvModel> listMovie = mapCursorToArrayListTv(movie);
        if (listMovie.size() > 0) {
            favoriteTvAdapter.setListFavoriteTv(listMovie);
        } else {
            favoriteTvAdapter.setListFavoriteTv(new ArrayList<TvModel>());
            showSnackBarMessage("Tidak ada data saat ini");
        }

    }

    private static class LoadTvAsync extends AsyncTask<Void, Void, Cursor> {

        private final WeakReference<Context> weakContext;
        private final WeakReference<LoadTvCallbak> weakCallback;

        private LoadTvAsync(Context context, LoadTvCallbak callback) {
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
            return context.getContentResolver().query(CONTENT_URI_TV, null, null, null, null);
        }

        @Override
        protected void onPostExecute(Cursor movie) {
            super.onPostExecute(movie);

            weakCallback.get().postExecute(movie);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        tvHelper.close();
    }

    private void showSnackBarMessage(String message) {
        Snackbar.make(rvFavoriteTv, message, Snackbar.LENGTH_SHORT).show();
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
            new LoadTvAsync(context, (LoadTvCallbak) context).execute();

        }
    }
}
