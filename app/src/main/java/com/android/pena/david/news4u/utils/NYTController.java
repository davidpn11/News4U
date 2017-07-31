package com.android.pena.david.news4u.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.utils.db.NytDataHelper;
import com.android.pena.david.news4u.utils.network.NYTApiClient;

import io.realm.RealmResults;
import timber.log.Timber;


/**
 * Created by david on 06/07/17.
 */

public class NYTController {


    private Context mContext;
    private SharedPreferences sharedPreferences;
    private NYTApiClient nytApiClient;
    private  NytDataHelper dataHelper;


    public NYTController(Context context, Application application) {
        this.mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        nytApiClient = new NYTApiClient(context);
        dataHelper = new NytDataHelper(context);

     //   dataHelper.startCategory();
        Timber.d(dataHelper.getArticles().toString());
        clearArticles();
        fetchDailyArticles();
        Timber.d("Size: "+dataHelper.getArticles().size());

    }


    public void close(){
        Timber.d("Controller closed");
        dataHelper.close();
    }


    public RealmResults<Article> getMostViewedArticles(){
        fetchDailyViewedArticles();
        return dataHelper.getMostViewedArticles();
    }

    public RealmResults<Article> getMostSharedArticles(){
        fetchDailySharedArticles();
        return dataHelper.getMostSharedArticles();
    }

//    public int countMostViewedArticles(){
//        return dataHelper.getMostViewedArticles().size();
//    }
//
//    public int countMostSharedArticles(){
//        return dataHelper.getMostSharedArticles().size();
//    }

    public void clearArticles(){
        dataHelper.clearArticles();
    }

    public boolean fetchDailyArticles(){
        RealmResults<Category> categories = dataHelper.getSeletedCategories();
        for(Category cat : categories){
            Timber.d(cat.getCategory());
            nytApiClient.fetchMostPopularArticlesAsync(cat.getCategory(),dataHelper);
            nytApiClient.fetchMostSharedArticlesAsync(cat.getCategory(),dataHelper);
        }
        return true;
    }

    public boolean fetchDailySharedArticles(){
        RealmResults<Category> categories = dataHelper.getSeletedCategories();
        for(Category cat : categories){
            Timber.d(cat.getCategory());
            nytApiClient.fetchMostSharedArticlesAsync(cat.getCategory(),dataHelper);
        }
        return true;
    }

    public boolean fetchDailyViewedArticles(){
        RealmResults<Category> categories = dataHelper.getSeletedCategories();
        for(Category cat : categories){
            Timber.d(cat.getCategory());
            nytApiClient.fetchMostPopularArticlesAsync(cat.getCategory(),dataHelper);
        }
        return true;
    }


    public RealmResults<Category> getCategories(){
        RealmResults<Category>  data = dataHelper.getCategories();
        return dataHelper.getCategories();
    }

    public boolean hasCategories(){
        return dataHelper.hasCategories();
    }

    public boolean hasSelectedCategories(){
        if(dataHelper.getSeletedCategories().size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isSaved(String id){
        return dataHelper.isSaved(id);
    }

    public Article getArticle(String id ){
        return dataHelper.getArticle(id);
    }

    public Article getSavedArticle(String id){
        return new Article(dataHelper.getSavedArticle(id));
    }

    public void saveArticle(SavedArticle article){
        dataHelper.insertSavedArticle(article);
    }

    public void deleteArticle(String id){
        dataHelper.deletedSavedArticle(id);
    }

}

