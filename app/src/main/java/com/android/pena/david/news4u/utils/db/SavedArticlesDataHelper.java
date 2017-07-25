package com.android.pena.david.news4u.utils.db;

import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.model.SavedMedia;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 20/07/17.
 */

public class SavedArticlesDataHelper {

    public static void clearAll(Realm realm) {



        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(SavedArticle.class).findAll().deleteAllFromRealm();
                bgRealm.where(SavedMedia.class).findAll().deleteAllFromRealm();
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

    //find all objects in the Recipe.class
    public static RealmResults<SavedArticle> getSavedArticles(Realm realm) {

        return realm.where(SavedArticle.class).findAll();
    }

    //query a single item with the given id
    public static Article getSavedArticle(Realm realm, String id) {

        SavedArticle saved = realm.where(SavedArticle.class).equalTo("id", id).findFirst();
        return new Article(saved);
    }

    //check if Recipe.class is empty
    public static boolean hasSavedArticles(Realm realm) {

        return !realm.where(SavedArticle.class).findAll().isEmpty();
    }

    public static boolean hasSavedArticle(Realm realm,String id) {
        RealmResults<SavedArticle> saved = realm.where(SavedArticle.class).equalTo("id",id).findAll();
        if( saved.size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public static boolean saveArticle(Realm realm,final SavedArticle saved_article){
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealmOrUpdate(saved_article);
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(e.getMessage());
            return false;
        }
    }



    public static boolean deleteArticle(Realm realm,final String id){
        try {
            realm.executeTransactionAsync(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    RealmResults<SavedArticle> saved = realm.where(SavedArticle.class).equalTo("id",id).findAll();
                    saved.clear();
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(e.getMessage());
            return false;
        }
    }


}
