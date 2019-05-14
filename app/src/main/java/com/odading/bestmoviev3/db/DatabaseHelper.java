package com.odading.bestmoviev3.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


public class DatabaseHelper extends SQLiteOpenHelper {
    public static String DATABASE_NAME = "dbfavoritemoviemovie";
    private static final int DATABASE_VERSION = 1;
    private static final String TEXT_TYPE = " TEXT";
    private static final String COMMA_SEP = ",";
    private static final String SQL_CREATE_TABLE_FAVORITE = "CREATE TABLE " + DatabaseContract.FavoriteColumns.TABLE_NAME + " (" +
            DatabaseContract.FavoriteColumns._ID + " INTEGER PRIMARY KEY," +
            DatabaseContract.FavoriteColumns.TITLE + TEXT_TYPE + COMMA_SEP +
            DatabaseContract.FavoriteColumns.DESCRIPTION + TEXT_TYPE + COMMA_SEP +
            DatabaseContract.FavoriteColumns.PHOTO + TEXT_TYPE + " )";

    private static final String SQL_CREATE_TABLE_FAVORITE_TV = "CREATE TABLE " + DatabaseContract.FavoriteColumns.TABLE_TV + " (" +
            DatabaseContract.FavoriteColumns._ID + " INTEGER PRIMARY KEY," +
            DatabaseContract.FavoriteColumns.TVTITLE + TEXT_TYPE + COMMA_SEP +
            DatabaseContract.FavoriteColumns.TVDESCRIPTION + TEXT_TYPE + COMMA_SEP +
            DatabaseContract.FavoriteColumns.TVPHOTO + TEXT_TYPE + " )";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE);
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_TV);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteColumns.TABLE_NAME);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.FavoriteColumns.TABLE_TV);
        onCreate(db);
    }


}
