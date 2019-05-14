package com.odading.bestmoviev3.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {
    public static final String AUTHORITY = "com.odading.bestmovie";
    public static final String SCHEME = "content";

    private DatabaseContract() {
    }

    public static final class FavoriteColumns implements BaseColumns {
        public static String TABLE_NAME = "favoritemoviecoycoyy";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String PHOTO = "photo";

        public static String TABLE_TV = "favoritetvcoyyy";
        public static String TVTITLE = "tvtitles";
        public static String TVDESCRIPTION = "tvdescriptions";
        public static String TVPHOTO = "tvphotos";

        public static final Uri CONTENT_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build();

        public static final Uri CONTENT_URI_TV = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_TV)
                .build();
    }

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }
}
