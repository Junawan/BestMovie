package com.odading.bestmoviev3.prcelable;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import com.odading.bestmoviev3.db.DatabaseContract;

import static android.provider.BaseColumns._ID;
import static com.odading.bestmoviev3.db.DatabaseContract.getColumnInt;
import static com.odading.bestmoviev3.db.DatabaseContract.getColumnString;

public class TvModel implements Parcelable {
    private int id;
    public String tvTitles, tvOverview, firsAirDate, tvPhoto, tvPopularity, tvLanguage, tvCountry;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTvTitles() {
        return tvTitles;
    }

    public void setTvTitles(String tvTitles) {
        this.tvTitles = tvTitles;
    }

    public String getTvOverview() {
        return tvOverview;
    }

    public void setTvOverview(String tvOverview) {
        this.tvOverview = tvOverview;
    }

    public String getFirsAirDate() {
        return firsAirDate;
    }

    public void setFirsAirDate(String firsAirDate) {
        this.firsAirDate = firsAirDate;
    }

    public String getTvPhoto() {
        return tvPhoto;
    }

    public void setTvPhoto(String tvPhoto) {
        this.tvPhoto = tvPhoto;
    }

    public String getTvPopularity() {
        return tvPopularity;
    }

    public void setTvPopularity(String tvPopularity) {
        this.tvPopularity = tvPopularity;
    }

    public String getTvLanguage() {
        return tvLanguage;
    }

    public void setTvLanguage(String tvLanguage) {
        this.tvLanguage = tvLanguage;
    }

    public String getTvCountry() {
        return tvCountry;
    }

    public void setTvCountry(String tvCountry) {
        this.tvCountry = tvCountry;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.tvTitles);
        dest.writeString(this.tvOverview);
        dest.writeString(this.firsAirDate);
        dest.writeString(this.tvPhoto);
        dest.writeString(this.tvPopularity);
        dest.writeString(this.tvLanguage);
        dest.writeString(this.tvCountry);
    }

    public TvModel() {
    }

    public TvModel(int id, String tvTitles, String tvOverview, String tvPhoto) {
        this.id = id;
        this.tvTitles = tvTitles;
        this.tvOverview = tvOverview;
        this.tvPhoto = tvPhoto;
    }

    public TvModel(Cursor cursor) {
        this.id = getColumnInt(cursor, _ID);
        this.tvTitles = getColumnString(cursor, DatabaseContract.FavoriteColumns.TVTITLE);
        this.tvOverview = getColumnString(cursor, DatabaseContract.FavoriteColumns.TVDESCRIPTION);
        this.tvPhoto = getColumnString(cursor, DatabaseContract.FavoriteColumns.TVPHOTO);
    }

    protected TvModel(Parcel in) {
        this.id = in.readInt();
        this.tvTitles = in.readString();
        this.tvOverview = in.readString();
        this.firsAirDate = in.readString();
        this.tvPhoto = in.readString();
        this.tvPopularity = in.readString();
        this.tvLanguage = in.readString();
        this.tvCountry = in.readString();
    }

    public static final Creator<TvModel> CREATOR = new Creator<TvModel>() {
        @Override
        public TvModel createFromParcel(Parcel source) {
            return new TvModel(source);
        }

        @Override
        public TvModel[] newArray(int size) {
            return new TvModel[size];
        }
    };
}
