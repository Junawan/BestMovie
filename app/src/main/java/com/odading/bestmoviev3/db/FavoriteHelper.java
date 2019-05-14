package com.odading.bestmoviev3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.odading.bestmoviev3.prcelable.MovieModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.PHOTO;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TABLE_NAME;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TITLE;

public class FavoriteHelper {

    private static final String DATABASE_TABLE = TABLE_NAME;
    private static DatabaseHelper dataBaseHelper;
    private static FavoriteHelper INSTANCE;
    private static SQLiteDatabase database;
    private static final String TAG = FavoriteHelper.class.getSimpleName();

    private FavoriteHelper(Context context) {
        dataBaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    public void open() throws SQLException {
        database = dataBaseHelper.getWritableDatabase();
    }
    public void close() {
        dataBaseHelper.close();
        if (database.isOpen())
            database.close();
    }

    public ArrayList<MovieModel> query() {
        ArrayList<MovieModel> arrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        MovieModel movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new MovieModel();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTitles(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setOverview(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setPhoto(cursor.getString(cursor.getColumnIndexOrThrow(PHOTO)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertMovie(MovieModel movie) {
        ContentValues args = new ContentValues();
        args.put(TITLE, movie.getTitles());
        args.put(DESCRIPTION, movie.getOverview());
        args.put(PHOTO, movie.getPhoto());

        return database.insert(DATABASE_TABLE, null, args);
    }

    public int updateFav(MovieModel movieModel) {
        ContentValues args = new ContentValues();
        args.put(TITLE, movieModel.getTitles());
        args.put(DESCRIPTION,movieModel.getOverview());
        args.put(PHOTO, movieModel.getPhoto());
        return database.update(DATABASE_TABLE, args, _ID + "= '" + movieModel.getId() + "'", null);
    }


    public int deleteFavorite(int id) {
        Log.d(TAG, "deleteFavorite");
        return database.delete(TABLE_NAME, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    /**
     * Ambil data dari semua note yang ada di dalam database
     * Gunakan method ini untuk ambil data di dalam provider
     *
     * @return cursor hasil query
     */
    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }

    /**
     * Simpan data ke dalam database
     * Gunakan method ini untuk query insert di dalam provider
     *
     * @param values nilai data yang akan di simpan
     * @return long id dari data yang baru saja di masukkan
     */
    public long insertProvider(ContentValues values) {
        return database.insert(DATABASE_TABLE, null, values);
    }

    /**
     * Update data dalam database
     *
     * @param id     data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int updateProvider(String id, ContentValues values) {
        return database.update(DATABASE_TABLE, values, _ID + " = ?", new String[]{id});
    }

    /**
     * Delete data dalam database
     *
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id) {
        return database.delete(DATABASE_TABLE, _ID + " = ?", new String[]{id});
    }
}
