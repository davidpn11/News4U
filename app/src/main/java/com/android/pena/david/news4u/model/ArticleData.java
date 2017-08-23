package com.android.pena.david.news4u.model;

import com.google.firebase.database.IgnoreExtraProperties;

/**
 * Created by david on 01/08/17.
 */
@IgnoreExtraProperties
public class ArticleData {
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

    public ArticleData(Article article){
        this.id = article.getId();
        this.url = article.getUrl();
        this.adxKeywords = article.getAdxKeywords();
        this.section = article.getSection();
        this.byline = article.getByline();
        this.title = article.getTitle();
        this._abstract = article.get_abstract();
        this.publishedDate = article.getPublishedDate();
        this.selection = article.getSelection();
        if(article.getMedia() == null){
            this.media = null;
        }else{
            this.media =
                    new MediaData(article.getMedia());
        }

    }

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
