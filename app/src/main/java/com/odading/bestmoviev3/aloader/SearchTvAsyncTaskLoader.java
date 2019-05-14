package com.odading.bestmoviev3.aloader;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.odading.bestmoviev3.item.SearchTvItem;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class SearchTvAsyncTaskLoader extends AsyncTaskLoader<ArrayList<SearchTvItem>> {
    private ArrayList<SearchTvItem> mData;
    private boolean mHasResult = false;
    private String titles;

    public SearchTvAsyncTaskLoader(final Context context, String titles) {
        super(context);

        onContentChanged();
        this.titles = titles;
    }

    @Override
    protected void onStartLoading() {
        super.onStartLoading();
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(@Nullable ArrayList<SearchTvItem> data) {
        mData = data;
        mHasResult = true;
        super.deliverResult(data);
    }

    @Override
    protected void onReset() {
        super.onReset();
        onStopLoading();
        if (mHasResult) {
            mData = null;
            mHasResult = false;
        }
    }

    private static final String API_KEY = "ada000c31f5e64dae2587b9baaac505e";

    @Nullable
    @Override
    public ArrayList<SearchTvItem> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<SearchTvItem> movieItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/search/tv?api_key="+API_KEY+"&language=en-US&query="+titles;
        client.get(url, new AsyncHttpResponseHandler() {
            @Override
            public void onStart() {
                super.onStart();
                setUseSynchronousMode(true);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responObject = new JSONObject(result);
                    JSONArray list = responObject.getJSONArray("results");
                    for (int i = 0; i < list.length(); i++) {
                        JSONObject movie = list.getJSONObject(i);
                        SearchTvItem movieItems = new SearchTvItem(movie);
                        movieItemses.add(movieItems);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return movieItemses;
    }
}
