package com.odading.bestmoviev3.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.odading.bestmoviev3.prcelable.TvModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TABLE_TV;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVDESCRIPTION;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVPHOTO;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVTITLE;

public class TvHelper {
    private static final String DATABASE_TABLE_TV = TABLE_TV;
    private static DatabaseHelper dataBaseHelperTv;
    private static TvHelper INSTANCETV;
    private static SQLiteDatabase databasetv;

    private TvHelper(Context context) {
        dataBaseHelperTv = new DatabaseHelper(context);
    }

    public static TvHelper getInstance(Context context) {
        if (INSTANCETV == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCETV == null) {
                    INSTANCETV = new TvHelper(context);
                }
            }
        }
        return INSTANCETV;
    }

    public void open() throws SQLException {
        databasetv = dataBaseHelperTv.getWritableDatabase();
    }
    public void close() {
        dataBaseHelperTv.close();
        if (databasetv.isOpen())
            databasetv.close();
    }

    public ArrayList<TvModel> query() {
        ArrayList<TvModel> arrayList = new ArrayList<>();
        Cursor cursor = databasetv.query(DATABASE_TABLE_TV, null,
                null,
                null,
                null,
                null,
                _ID + " DESC",
                null);
        cursor.moveToFirst();
        TvModel movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new TvModel();
                movie.setId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setTvTitles(cursor.getString(cursor.getColumnIndexOrThrow(TVTITLE)));
                movie.setTvOverview(cursor.getString(cursor.getColumnIndexOrThrow(TVDESCRIPTION)));
                movie.setTvPhoto(cursor.getString(cursor.getColumnIndexOrThrow(TVPHOTO)));
                arrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return arrayList;
    }

    public long insertTv(TvModel movie) {
        ContentValues args = new ContentValues();
        args.put(TVTITLE, movie.getTvTitles());
        args.put(TVDESCRIPTION, movie.getTvOverview());
        args.put(TVPHOTO, movie.getTvPhoto());

        return databasetv.insert(DATABASE_TABLE_TV, null, args);
    }

    public int deleteFavoriteTv(int id) {
        return databasetv.delete(TABLE_TV, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return databasetv.query(DATABASE_TABLE_TV, null
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
        return databasetv.query(DATABASE_TABLE_TV
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
        return databasetv.insert(DATABASE_TABLE_TV, null, values);
    }

    /**
     * Update data dalam database
     *
     * @param id     data dengan id berapa yang akan di update
     * @param values nilai data baru
     * @return int jumlah data yang ter-update
     */
    public int updateProvider(String id, ContentValues values) {
        return databasetv.update(DATABASE_TABLE_TV, values, _ID + " = ?", new String[]{id});
    }

    /**
     * Delete data dalam database
     *
     * @param id data dengan id berapa yang akan di delete
     * @return int jumlah data yang ter-delete
     */
    public int deleteProvider(String id) {
        return databasetv.delete(DATABASE_TABLE_TV, _ID + " = ?", new String[]{id});
    }

}
