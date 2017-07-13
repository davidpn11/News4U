package com.android.pena.david.news4u.utils.db;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Media;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 11/07/17.
 */

public class ArticleDataHelper {

    public static void clearAll(Realm realm) {

        realm.beginTransaction();
        realm.clear(Article.class);
        realm.clear(Media.class);
        realm.commitTransaction();
    }

    //find all objects in the Recipe.class
    public static RealmResults<Article> getArticles(Realm realm) {

        return realm.where(Article.class).findAll();
    }

    public static int getCount(Realm realm){
        return realm.where(Article.class).findAll().size();
    }

    //query a single item with the given id
    public static Article getArticle(Realm realm,String id) {

        return realm.where(Article.class).equalTo("id", id).findFirst();
    }

    //check if Recipe.class is empty
    public static boolean hasArticles(Realm realm) {

        return !realm.allObjects(Article.class).isEmpty();
    }

    public static boolean insertArticle(Realm realm,final Article article){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(article);
                }
            });
            return true;
        } catch (Exception e) {
            Timber.e(e.getMessage());
            return false;
        }
    }
    public static boolean insertArticles(Realm realm,final List<Article> articles){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    realm.copyToRealm(articles);
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
