package com.android.pena.david.news4u.utils.db;

import com.android.pena.david.news4u.model.Article;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by david on 26/07/17.
 */

public interface ArticleDbInterface {

    void clearArticles();
    RealmResults<Article> getArticles();
    RealmResults<Article> getMostViewedArticles();
    RealmResults<Article> getMostSharedArticles();
    int countArticles();
    boolean hasArticles();
    boolean hasArticle(String id, String selection);
    void insertViewedArticles(final List<Article> pArticles);
    void insertViewedArticlesAsync(final List<Article> pArticles);
    void insertSharedArticles(final List<Article> pArticles);
    void insertSharedArticlesAsync(final List<Article> pArticles);
}
