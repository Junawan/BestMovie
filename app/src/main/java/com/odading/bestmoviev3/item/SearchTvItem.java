package com.odading.bestmoviev3.item;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONException;
import org.json.JSONObject;

public class SearchTvItem implements Parcelable {
    private int id;
    public String tvTitles, tvOverview, firsAirDate, tvPhoto, tvPopularity, tvLanguage, tvCountry;

    public SearchTvItem(JSONObject object) {
        try {
            String tvTitles = object.getString("name");
            String tvOverview = object.getString("overview");
            String firstAirDate = object.getString("first_air_date");
            String tvPopularity = object.getString("popularity");
            String tvLanguage = object.getString("original_language");
            String tvCountry = object.getString("origin_country");
            String tvPhoto = "https://image.tmdb.org/t/p/w185/"+object.getString("poster_path");
            this.tvTitles = tvTitles;
            this.tvOverview = tvOverview;
            this.tvPopularity = tvPopularity;
            this.tvLanguage = tvLanguage;
            this.tvPhoto = tvPhoto;
            this.tvCountry = tvCountry;
            this.firsAirDate = firstAirDate;
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

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

    protected SearchTvItem(Parcel in) {
        this.id = in.readInt();
        this.tvTitles = in.readString();
        this.tvOverview = in.readString();
        this.firsAirDate = in.readString();
        this.tvPhoto = in.readString();
        this.tvPopularity = in.readString();
        this.tvLanguage = in.readString();
        this.tvCountry = in.readString();
    }

    public static final Creator<SearchTvItem> CREATOR = new Creator<SearchTvItem>() {
        @Override
        public SearchTvItem createFromParcel(Parcel source) {
            return new SearchTvItem(source);
        }

        @Override
        public SearchTvItem[] newArray(int size) {
            return new SearchTvItem[size];
        }
    };
}
