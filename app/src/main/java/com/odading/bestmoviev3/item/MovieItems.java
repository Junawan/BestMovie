package com.odading.bestmoviev3.item;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class MovieItems implements Parcelable {
    public int id;
    public String titles, detail, release, photo, popularity, language;

    public MovieItems(JSONObject object) {
        try {
            String titles = object.getString("title");
            String detail = object.getString("overview");
            String release = object.getString("release_date");
            String popularity = object.getString("popularity");
            String language = object.getString("original_language");
            String photo = "https://image.tmdb.org/t/p/w185/"+object.getString("poster_path");
            this.titles = titles;
            this.detail = detail;
            this.popularity = popularity;
            this.language = language;
            this.photo = photo;
            this.release = release;
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public MovieItems() {

    }

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

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
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


    @Override
    public int describeContents() {
        return 0;
    }

    public MovieItems(int id, String titles, String detail, String photo) {
        this.id = id;
        this.titles = titles;
        this.detail = detail;
        this.photo = photo;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titles);
        dest.writeString(this.detail);
        dest.writeString(this.release);
        dest.writeString(this.photo);
        dest.writeString(this.popularity);
        dest.writeString(this.language);
    }

    protected MovieItems(Parcel in) {
        this.id = in.readInt();
        this.titles = in.readString();
        this.detail = in.readString();
        this.release = in.readString();
        this.photo = in.readString();
        this.popularity = in.readString();
        this.language = in.readString();
    }

    public static final Parcelable.Creator<MovieItems> CREATOR = new Parcelable.Creator<MovieItems>() {
        @Override
        public MovieItems createFromParcel(Parcel source) {
            return new MovieItems(source);
        }

        @Override
        public MovieItems[] newArray(int size) {
            return new MovieItems[size];
        }
    };
}
