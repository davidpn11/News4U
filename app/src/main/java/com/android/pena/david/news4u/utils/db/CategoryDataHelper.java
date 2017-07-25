package com.android.pena.david.news4u.utils.db;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;


import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Category;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 11/07/17.
 */

public class CategoryDataHelper {



    //clear all objects from Recipe.class
    public static void clearAll(Realm realm) {


        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(Category.class).findAll().deleteAllFromRealm();
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                // Transaction was a success.
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                // Transaction failed and was automatically canceled.
            }
        });
    }

    public static boolean hasCategories(Realm realm){
        return !realm.where(Category.class).findAll().isEmpty();
    }

    public static RealmResults<Category> getCategories(Realm realm) {
        return realm.where(Category.class).findAll();
    }

    public static RealmResults<Category> getSeletedCategories(Realm realm){
        return realm.where(Category.class)
                .equalTo("active",true)
                .findAll();
    }

    public static int getCount(Realm realm){
        return realm.where(Category.class).findAll().size();
    }


    public static void startCategory(Realm realm,final Application application){
        if(hasCategories(realm)) return;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String[] categories = application.getResources().getStringArray(R.array.categories);
                for (int i = 0; i < categories.length; i++) {
                    realm.copyToRealmOrUpdate(new Category(categories[i]));
                }
                Timber.d("Categories added!");
            }
        });
    }

}
