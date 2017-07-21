package com.android.pena.david.news4u.utils.db;

import android.app.Activity;
import android.app.Application;
import android.support.v4.app.Fragment;

import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Media;
import com.android.pena.david.news4u.utils.generalUtils;

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

    public static RealmResults<Article> getMostViewedArticles(Realm realm) {

        return realm.where(Article.class)
                .beginGroup()
                .equalTo("selection", generalUtils.VIEWED_TAG)
                .or()
                .equalTo("selection", generalUtils.BOTH_TAG)
                .endGroup()
                .findAll();
    }


    public static RealmResults<Article> getMostSharedArticles(Realm realm) {

        return realm.where(Article.class)
                .beginGroup()
                .equalTo("selection", generalUtils.SHARED_TAG)
                .or()
                .equalTo("selection", generalUtils.BOTH_TAG)
                .endGroup()
                .findAll();
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


    public static boolean hasArticle(Realm realm, String id, String selection){
        Article a = realm.where(Article.class).equalTo("id", id).findFirst();
        if(a != null){
            if(a.getSelection().equals(selection)){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }


    }
    public static boolean insertViewedArticles(Realm realm,final List<Article> articles){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {
                    for(Article article : articles){
                        if(hasArticle(realm, article.getId(), generalUtils.SHARED_TAG)){
                            article.setSelection(generalUtils.BOTH_TAG);
                        }else if(hasArticle(realm, article.getId(), generalUtils.BOTH_TAG)){
                         break;
                        }
                        realm.copyToRealmOrUpdate(article);
                    }
                }
            });
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            Timber.e(e.getMessage());
            return false;
        }
    }

    public static boolean insertSharedArticles(Realm realm,final List<Article> articles){
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm realm) {

                    for(Article article :articles){
                        if(hasArticle(realm, article.getId(), generalUtils.VIEWED_TAG)){
                            article.setSelection(generalUtils.BOTH_TAG);
                        }else if(hasArticle(realm, article.getId(), generalUtils.BOTH_TAG)){
                            break;
                        }
                        realm.copyToRealmOrUpdate(article);
                    }
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
