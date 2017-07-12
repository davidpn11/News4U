package com.android.pena.david.news4u.model;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david on 06/07/17.
 */

public class Article extends RealmObject{
    @PrimaryKey
    private String id;
    private String url;
    private String adxKeywords;
    private String section;
    private String byline;
    private String title;
    private String _abstract;
    private String publishedDate;
    private Media media;

    public String getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getAdxKeywords() {
        return adxKeywords;
    }

    public String getSection() {
        return section;
    }

    public String getByline() {
        return byline;
    }

    public String getTitle() {
        return title;
    }

    public String get_abstract() {
        return _abstract;
    }

    public String getPublishedDate() {
        return publishedDate;
    }

    public Media getMedia() {
        return media;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setAdxKeywords(String adxKeywords) {
        this.adxKeywords = adxKeywords;
    }

    public void setSection(String section) {
        this.section = section;
    }

    public void setByline(String byline) {
        this.byline = byline;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void set_abstract(String _abstract) {
        this._abstract = _abstract;
    }

    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }

    public void setMedia(Media media) {
        this.media = media;
    }

    public Article() {
    }
}
