package com.android.pena.david.news4u.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by david on 01/08/17.
 */
@IgnoreExtraProperties
public class ArticleData implements Parcelable {
    private String id;
    private String url;
    private String adxKeywords;
    private String section;
    private String byline;
    private String title;
    private String _abstract;
    private String publishedDate;
    private String selection;
    private MediaData media;

    public ArticleData() {
    }

    public ArticleData(Parcel in){
        this.id = in.readString();
        this.url = in.readString();
        this.adxKeywords = in.readString();
        this.section = in.readString();
        this.byline = in.readString();
        this.title = in.readString();
        this._abstract = in.readString();
        this.publishedDate = in.readString();
        this.selection = in.readString();
        this.media = in.readParcelable(MediaData.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(url);
        parcel.writeString(adxKeywords);
        parcel.writeString(section);
        parcel.writeString(byline);
        parcel.writeString(title);
        parcel.writeString(_abstract);
        parcel.writeString(publishedDate);
        parcel.writeString(selection);
        parcel.writeParcelable(media,i);
    }

    public static final Parcelable.Creator CREATOR
            = new Parcelable.Creator() {
        public ArticleData createFromParcel(Parcel in) {
            return new ArticleData(in);
        }

        public ArticleData[] newArray(int size) {
            return new ArticleData[size];
        }
    };


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public String getSection() {
        return section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public String getByline() {
        return byline;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_abstract() {
        return _abstract;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

    public MediaData getMedia() {
        return media;
    }

    public void setMedia(MediaData media) {
        this.media = media;
    }
}
