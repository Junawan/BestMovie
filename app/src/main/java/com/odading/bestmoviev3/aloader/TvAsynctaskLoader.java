package com.odading.bestmoviev3.aloader;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.content.AsyncTaskLoader;

import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.SyncHttpClient;
import com.odading.bestmoviev3.item.TvItems;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvAsynctaskLoader extends AsyncTaskLoader<ArrayList<TvItems>> {
    private ArrayList<TvItems> mData;
    private boolean mHasResult = false;
    private String tvshow;

    public TvAsynctaskLoader(@NonNull Context context, String tvshow) {
        super(context);
        onContentChanged();
        this.tvshow = tvshow;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged())
            forceLoad();
        else if (mHasResult)
            deliverResult(mData);
    }

    @Override
    public void deliverResult(final ArrayList<TvItems> data) {
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

    @Override
    public ArrayList<TvItems> loadInBackground() {
        SyncHttpClient client = new SyncHttpClient();
        final ArrayList<TvItems> tvItemses = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/discover/tv?api_key="+API_KEY+"&language=en-US";
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
                        JSONObject tv = list.getJSONObject(i);
                        TvItems tvItems = new TvItems(tv);
                        tvItemses.add(tvItems);

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

            }
        });
        return tvItemses;
    }
}
