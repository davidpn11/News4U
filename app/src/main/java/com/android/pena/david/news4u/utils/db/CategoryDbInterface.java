package com.android.pena.david.news4u.utils.db;

import com.android.pena.david.news4u.model.Category;

import java.util.List;

import io.realm.RealmResults;

/**
 * Created by david on 26/07/17.
 */

public interface CategoryDbInterface {
    void clearCategories();
    RealmResults<Category> getCategories();
    int countCategory();
    boolean hasCategories();
    RealmResults<Category> getSeletedCategories();
    void startCategory();
}
