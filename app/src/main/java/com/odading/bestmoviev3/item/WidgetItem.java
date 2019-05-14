package com.odading.bestmoviev3.item;

import android.os.Parcel;
import android.os.Parcelable;

public class WidgetItem implements Parcelable {
    public int id;
    public String titles, detail, photo;

    public WidgetItem(int id, String photo) {
        this.id = id;
        this.photo = photo;
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

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.titles);
        dest.writeString(this.detail);
        dest.writeString(this.photo);
    }

    public WidgetItem() {
    }

    protected WidgetItem(Parcel in) {
        this.id = in.readInt();
        this.titles = in.readString();
        this.detail = in.readString();
        this.photo = in.readString();
    }

    public static final Parcelable.Creator<WidgetItem> CREATOR = new Parcelable.Creator<WidgetItem>() {
        @Override
        public WidgetItem createFromParcel(Parcel source) {
            return new WidgetItem(source);
        }

        @Override
        public WidgetItem[] newArray(int size) {
            return new WidgetItem[size];
        }
    };
}
