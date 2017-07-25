package com.android.pena.david.news4u.ui.home.adapter;

import android.support.annotation.Nullable;
import android.support.v7.util.DiffUtil;

import com.android.pena.david.news4u.model.Article;

import io.realm.RealmResults;

/**
 * Created by david on 24/07/17.
 */

public class ArticlesDiffCallBack extends DiffUtil.Callback {
    private final RealmResults<Article> mOldArticles;
    private final RealmResults<Article> mNewArticles;


    public ArticlesDiffCallBack(RealmResults<Article> mOldArticles, RealmResults<Article> mNewArticles) {
        this.mOldArticles = mOldArticles;
        this.mNewArticles = mNewArticles;
    }


    @Nullable
    @Override
    public Object getChangePayload(int oldItemPosition, int newItemPosition) {
        return super.getChangePayload(oldItemPosition, newItemPosition);
    }

    @Override
    public int getOldListSize() {
        return mOldArticles.size();
    }

    @Override
    public int getNewListSize() {
        return mNewArticles.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return mOldArticles.get(oldItemPosition).getId() == mNewArticles.get(newItemPosition).getId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        final Article oldA = mOldArticles.get(oldItemPosition);
        final Article newA = mNewArticles.get(newItemPosition);
        return oldA.equals(newA);
    }
}
