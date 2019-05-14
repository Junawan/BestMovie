package com.odading.bestmoviev3.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;

import com.odading.bestmoviev3.db.FavoriteHelper;
import com.odading.bestmoviev3.db.TvHelper;
import com.odading.bestmoviev3.fragment.FavoriteMoviefragment;
import com.odading.bestmoviev3.fragment.FavoriteTvFragment;

import static com.odading.bestmoviev3.db.DatabaseContract.AUTHORITY;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.CONTENT_URI_TV;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TABLE_NAME;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TABLE_TV;

public class MovieProvider extends ContentProvider {
    /*
    Integer digunakan sebagai identifier antara select all sama select by id
     */
    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final int TV = 3;
    private static final int TV_ID = 4;

    private static final UriMatcher sUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    /*
    Uri matcher untuk mempermudah identifier dengan menggunakan integer
    misal
    uri com.dicoding.picodiploma.mynotesapp dicocokan dengan integer 1
    uri com.dicoding.picodiploma.mynotesapp/# dicocokan dengan integer 2
     */
    static {

        // content://com.dicoding.picodiploma.mynotesapp/note
        sUriMatcher.addURI(AUTHORITY, TABLE_NAME, MOVIE);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_NAME + "/#",
                MOVIE_ID);
        sUriMatcher.addURI(AUTHORITY, TABLE_TV, TV);

        // content://com.dicoding.picodiploma.mynotesapp/note/id
        sUriMatcher.addURI(AUTHORITY,
                TABLE_TV + "/#",
                TV_ID);
    }

    private FavoriteHelper favoriteHelper;
    private TvHelper tvHelper;

    @Override
    public boolean onCreate() {
        favoriteHelper = FavoriteHelper.getInstance(getContext());
        tvHelper = TvHelper.getInstance(getContext());

        return true;
    }

    /*
    Method query digunakan ketika ingin menjalankan query Select
    Return cursor
     */
    @Override
    public Cursor query(@NonNull Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteHelper.open();
        tvHelper.open();
        Cursor cursor;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favoriteHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            case TV:
                cursor = tvHelper.queryProvider();
                break;
            case TV_ID:
                cursor = tvHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }

        return cursor;
    }


    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }


    @Override
    public Uri insert(@NonNull Uri uri, ContentValues contentValues) {
        favoriteHelper.open();
        tvHelper.open();
        Uri _uri = null;
        switch (sUriMatcher.match(uri)) {
            case MOVIE:
                favoriteHelper.insertProvider(contentValues);
                _uri = CONTENT_URI;
                getContext().getContentResolver().notifyChange(_uri, new FavoriteMoviefragment.DataObserver(new Handler(), getContext()));
                break;
            case TV:
                tvHelper.insertProvider(contentValues);
                _uri = CONTENT_URI_TV;
                getContext().getContentResolver().notifyChange(_uri, new FavoriteTvFragment.DataObserver(new Handler(), getContext()));
                break;
            default:
                break;
        }

        return _uri;
    }

    @Override
    public int update(@NonNull Uri uri, ContentValues contentValues, String s, String[] strings) {
        favoriteHelper.open();
        tvHelper.open();
        int updated;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                updated = favoriteHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            case TV_ID:
                updated = tvHelper.updateProvider(uri.getLastPathSegment(), contentValues);
                break;
            default:
                updated = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMoviefragment.DataObserver(new Handler(), getContext()));
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, new FavoriteTvFragment.DataObserver(new Handler(), getContext()));
        return updated;
    }

    @Override
    public int delete(@NonNull Uri uri, String s, String[] strings) {
        favoriteHelper.open();
        tvHelper.open();
        int deleted;
        switch (sUriMatcher.match(uri)) {
            case MOVIE_ID:
                deleted = favoriteHelper.deleteProvider(uri.getLastPathSegment());
                break;
            case TV_ID:
                deleted = tvHelper.deleteProvider(uri.getLastPathSegment());
                break;
            default:
                deleted = 0;
                break;
        }

        getContext().getContentResolver().notifyChange(CONTENT_URI, new FavoriteMoviefragment.DataObserver(new Handler(), getContext()));
        getContext().getContentResolver().notifyChange(CONTENT_URI_TV, new FavoriteTvFragment.DataObserver(new Handler(), getContext()));
        return deleted;
    }
}
