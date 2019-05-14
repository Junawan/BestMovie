package com.odading.bestmoviev3.prcelable;

import android.os.Parcel;
import android.os.Parcelable;

public class MovieParcelable implements Parcelable {
    private int id;
    public String titles, overview, release, popularity, language, photo, tvshowTites,
            tvshowOverview,tvshowPopularity,tvshowLanguage, firstAirDate,  tvPhoto;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getTvPhoto() {
        return tvPhoto;
    }

    public void setTvPhoto(String tvPhoto) {
        this.tvPhoto = tvPhoto;
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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }

    public String getTvshowTites() {
        return tvshowTites;
    }

    public void setTvshowTites(String tvshowTites) {
        this.tvshowTites = tvshowTites;
    }

    public String getTvshowOverview() {
        return tvshowOverview;
    }

    public void setTvshowOverview(String tvshowOverview) {
        this.tvshowOverview = tvshowOverview;
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

    public String getTvshowPopularity() {
        return tvshowPopularity;
    }

    public void setTvshowPopularity(String tvshowPopularity) {
        this.tvshowPopularity = tvshowPopularity;
    }

    public String getTvshowLanguage() {
        return tvshowLanguage;
    }

    public void setTvshowLanguage(String tvshowLanguage) {
        this.tvshowLanguage = tvshowLanguage;
    }

    public String getFirstAirDate() {
        return firstAirDate;
    }

    public void setFirstAirDate(String firstAirDate) {
        this.firstAirDate = firstAirDate;
    }

    public MovieParcelable() {
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
        dest.writeString(this.release);
        dest.writeString(this.popularity);
        dest.writeString(this.language);
        dest.writeString(this.photo);
        dest.writeString(this.tvshowTites);
        dest.writeString(this.tvshowOverview);
        dest.writeString(this.tvshowPopularity);
        dest.writeString(this.tvshowLanguage);
        dest.writeString(this.firstAirDate);
        dest.writeString(this.tvPhoto);
    }

    public MovieParcelable(int id, String titles, String overview, String photo) {
        this.id = id;
        this.titles = titles;
        this.overview = overview;
        this.photo = photo;
    }

    private MovieParcelable(Parcel in) {
        this.id = in.readInt();
        this.titles = in.readString();
        this.overview = in.readString();
        this.release = in.readString();
        this.popularity = in.readString();
        this.language = in.readString();
        this.photo = in.readString();
        this.tvshowTites = in.readString();
        this.tvshowOverview = in.readString();
        this.tvshowPopularity = in.readString();
        this.tvshowLanguage = in.readString();
        this.firstAirDate = in.readString();
        this.tvPhoto = in.readString();
    }

    public static final Creator<MovieParcelable> CREATOR = new Creator<MovieParcelable>() {
        @Override
        public MovieParcelable createFromParcel(Parcel source) {
            return new MovieParcelable(source);
        }

        @Override
        public MovieParcelable[] newArray(int size) {
            return new MovieParcelable[size];
        }
    };
}
