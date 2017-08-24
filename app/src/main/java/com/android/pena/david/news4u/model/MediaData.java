package com.android.pena.david.news4u.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by david on 01/08/17.
 */

public class MediaData implements Parcelable {
    private String url;
    private String format;

    public MediaData() {
    }

    public MediaData(Parcel in){
        this.url = in.readString();
        this.format = in.readString();
    }


    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(url);
        parcel.writeString(format);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public MediaData createFromParcel(Parcel in) {
            return new MediaData(in);
        }

        public MediaData[] newArray(int size) {
            return new MediaData[size];
        }
    };
}
