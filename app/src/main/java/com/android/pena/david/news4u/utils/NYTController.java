package com.android.pena.david.news4u.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.utils.db.ArticleDataHelper;
import com.android.pena.david.news4u.utils.db.CategoryDataHelper;
import com.android.pena.david.news4u.utils.db.NytDataHelper;
import com.android.pena.david.news4u.utils.network.NYTApiClient;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
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

    public int countMostViewedArticles(){
        return dataHelper.getMostViewedArticles().size();
    }

    public int countMostSharedArticles(){
        return dataHelper.getMostSharedArticles().size();
    }

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
}

