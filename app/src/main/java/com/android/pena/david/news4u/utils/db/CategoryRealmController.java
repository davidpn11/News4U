package com.android.pena.david.news4u.utils.db;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;


import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Category;

import io.realm.Realm;
import io.realm.RealmResults;

/**
 * Created by david on 11/07/17.
 */

public class CategoryRealmController {


    public CategoryRealmController(Realm realm,Application application) {
        if(!hasCategories(realm)){
            startCategory(realm,application);
        }
    }

    //clear all objects from Recipe.class
    public void clearAll(Realm realm) {

        realm.beginTransaction();
        realm.clear(Category.class);
        realm.commitTransaction();
    }

    private boolean hasCategories(Realm realm){
        return !realm.allObjects(Category.class).isEmpty();
    }

    public RealmResults<Category> getCategories(Realm realm) {

        return realm.where(Category.class).findAll();
    }

    public int getCount(Realm realm){
        return realm.where(Category.class).findAll().size();
    }

    private void startCategory(Realm realm,final Application application){
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String[] categories = application.getResources().getStringArray(R.array.categories);
                for(int i = 0;i < categories.length; i++){
                    realm.copyToRealm(new Category(categories[i]));
                }
            }
        });
    }
}
