package com.odading.bestmoviev3.item;

import android.os.Parcel;
import android.os.Parcelable;

public class AlarmItem implements Parcelable {
    public int id;
    public String titles, release;

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

    public String getRelease() {
        return release;
    }

    public void setRelease(String release) {
        this.release = release;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titles);
        dest.writeString(this.release);
    }

    public AlarmItem() {
    }

    protected AlarmItem(Parcel in) {
        this.id = in.readInt();
        this.titles = in.readString();
        this.release = in.readString();
    }

    public static final Parcelable.Creator<AlarmItem> CREATOR = new Parcelable.Creator<AlarmItem>() {
        @Override
        public AlarmItem createFromParcel(Parcel source) {
            return new AlarmItem(source);
        }

        @Override
        public AlarmItem[] newArray(int size) {
            return new AlarmItem[size];
        }
    };
}
