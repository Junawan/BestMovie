package com.odading.bestmoviev3.helper;

import android.database.Cursor;

import com.odading.bestmoviev3.prcelable.MovieModel;
import com.odading.bestmoviev3.prcelable.TvModel;

import java.util.ArrayList;

import static android.provider.BaseColumns._ID;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.DESCRIPTION;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.PHOTO;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TITLE;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVDESCRIPTION;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVPHOTO;
import static com.odading.bestmoviev3.db.DatabaseContract.FavoriteColumns.TVTITLE;

public class MappingHelper {
    public static ArrayList<MovieModel> mapCursorToArrayList(Cursor notesCursor) {
        ArrayList<MovieModel> notesList = new ArrayList<>();

        while (notesCursor.moveToNext()) {
            int id = notesCursor.getInt(notesCursor.getColumnIndexOrThrow(_ID));
            String title = notesCursor.getString(notesCursor.getColumnIndexOrThrow(TITLE));
            String description = notesCursor.getString(notesCursor.getColumnIndexOrThrow(DESCRIPTION));
            String photo = notesCursor.getString(notesCursor.getColumnIndexOrThrow(PHOTO));
            notesList.add(new MovieModel(id, title, description, photo));
        }

        return notesList;
    }

    public static ArrayList<TvModel> mapCursorToArrayListTv(Cursor tvCursor) {
        ArrayList<TvModel> tvList = new ArrayList<>();

        while (tvCursor.moveToNext()) {
            int id = tvCursor.getInt(tvCursor.getColumnIndexOrThrow(_ID));
            String tvtitle = tvCursor.getString(tvCursor.getColumnIndexOrThrow(TVTITLE));
            String tvdescription = tvCursor.getString(tvCursor.getColumnIndexOrThrow(TVDESCRIPTION));
            String tvphoto = tvCursor.getString(tvCursor.getColumnIndexOrThrow(TVPHOTO));
            tvList.add(new TvModel(id, tvtitle, tvdescription, tvphoto));
        }

        return tvList;
    }
}
