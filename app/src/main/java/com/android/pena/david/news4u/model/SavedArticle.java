package com.android.pena.david.news4u.model;

import java.util.List;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david on 06/07/17.
 */

public class SavedArticle extends RealmObject{
    @PrimaryKey
    private String id;
    private String url;
    private String adxKeywords;
    private String section;
    private String byline;
    private String title;
    private String _abstract;
    private String publishedDate;
    private String selection;
    private SavedMedia media;

    public SavedArticle() {
    }

    public SavedArticle(Article article){
        this.id = article.getId();
        this.url = article.getUrl();
        this.adxKeywords = article.getAdxKeywords();
        this.section = article.getSection();
        this.byline = article.getByline();
        this.title = article.getTitle();
        this._abstract = article.get_abstract();
        this.publishedDate = article.getPublishedDate();
        this.selection = article.getSelection();
        this.media = new SavedMedia(article.getMedia());
    }

    public SavedMedia getMedia() {
        return media;
    }

    public void setMedia(SavedMedia media) {
        this.media = media;
    }

    public String getSelection() {
        return selection;
    }

    public void setSelection(String selection) {
        this.selection = selection;
    }

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

}
