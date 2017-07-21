package com.android.pena.david.news4u.model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david on 21/07/17.
 */

public class SavedMedia extends RealmObject {
    @PrimaryKey
    private String url;
    private String format;

    public SavedMedia() {
    }

    public SavedMedia(Media media) {
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
