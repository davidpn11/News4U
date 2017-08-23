package com.android.pena.david.news4u.model;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david on 06/07/17.
 */

public class Media extends RealmObject{
    private String url;
    private String format;

    public Media() {
    }

    public Media(SavedMedia media) {
        this.url = media.getUrl();
        this.format = media.getFormat();
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
}
