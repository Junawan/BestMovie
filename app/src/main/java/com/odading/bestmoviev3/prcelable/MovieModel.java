package com.odading.bestmoviev3.prcelable;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.odading.bestmoviev3.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.odading.bestmoviev3.db.DatabaseContract.getColumnInt;
import static com.odading.bestmoviev3.db.DatabaseContract.getColumnString;

public class MovieModel implements Parcelable {
    private int id;
    public String titles;
    public String overview;
    public String photo;
    public String release;
    public String popularity;
    public String language;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitles() {
        return titles;
    }

    public void setTitles(String titles) {
        this.titles = titles;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPopularity() {
        return popularity;
    }

    public void setPopularity(String popularity) {
        this.popularity = popularity;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public MovieModel() {
    }

    public MovieModel(int id, String titles, String overview, String photo) {
        this.id = id;
        this.titles = titles;
        this.overview = overview;
        this.photo = photo;
    }

    public MovieModel(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.titles = getColumnString(cursor, DatabaseContract.FavoriteColumns.TITLE);
        this.overview = getColumnString(cursor, DatabaseContract.FavoriteColumns.DESCRIPTION);
        this.photo = getColumnString(cursor, DatabaseContract.FavoriteColumns.PHOTO);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titles);
        dest.writeString(this.overview);
        dest.writeString(this.photo);
        dest.writeString(this.release);
        dest.writeString(this.popularity);
        dest.writeString(this.language);
    }

    protected MovieModel(Parcel in) {
        this.id = in.readInt();
        this.titles = in.readString();
        this.overview = in.readString();
        this.photo = in.readString();
        this.release = in.readString();
        this.popularity = in.readString();
        this.language = in.readString();
    }

    public static final Creator<MovieModel> CREATOR = new Creator<MovieModel>() {
        @Override
        public MovieModel createFromParcel(Parcel source) {
            return new MovieModel(source);
        }

        @Override
        public MovieModel[] newArray(int size) {
            return new MovieModel[size];
        }
    };
}
