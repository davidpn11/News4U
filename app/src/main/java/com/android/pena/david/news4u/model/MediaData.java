package com.android.pena.david.news4u.model;

/**
 * Created by david on 01/08/17.
 */

public class MediaData {
    private String url;
    private String format;

    public MediaData() {
    }

    public MediaData(Media media) {
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
