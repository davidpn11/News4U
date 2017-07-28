package com.android.pena.david.news4u.utils.db;

import com.android.pena.david.news4u.model.SavedArticle;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by david on 26/07/17.
 */

public interface SavedArticleDbInterface {
    void clearSavedArticles();
    RealmResults<SavedArticle> getSavedArticles();
    int countSavedArticles();
    boolean hasSavedArticles();
    void insertSavedArticle(final SavedArticle pArticle);
}
