package com.android.pena.david.news4u.ui.save.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.android.pena.david.news4u.model.SavedArticle;

import io.realm.RealmResults;

/**
 * Created by david on 24/07/17.
 */

public class SavedArticlesDiffCallBack extends DiffUtil.Callback {
    private final RealmResults<SavedArticle> mOldSavedArticles;
    private final RealmResults<SavedArticle> mNewSavedArticles;


    public SavedArticlesDiffCallBack(RealmResults<SavedArticle> mOldSavedArticles, RealmResults<SavedArticle> mNewSavedArticles) {
        this.mOldSavedArticles = mOldSavedArticles;
        this.mNewSavedArticles = mNewSavedArticles;
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    @Override
    public int getOldListSize() {
        return mOldSavedArticles.size();
    }

    @Override
    public int getNewListSize() {
        return mNewSavedArticles.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldSavedArticles.get(oldItemPosition).getId() == mNewSavedArticles.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final SavedArticle oldA = mOldSavedArticles.get(oldItemPosition);
        final SavedArticle newA = mNewSavedArticles.get(newItemPosition);
        return oldA.equals(newA);
    }
}
