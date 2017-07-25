package com.android.pena.david.news4u.utils.network;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Looper;
import android.support.v7.preference.PreferenceManager;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.utils.db.ArticleDataHelper;
import com.android.pena.david.news4u.utils.db.CategoryDataHelper;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

import static com.android.pena.david.news4u.R.string.pref_remove;

/**
 * Created by david on 06/07/17.
 */

public class NYTController {

    private static final String API_KEY_VALUE = BuildConfig.API_KEY;
    private List<Article> articles;
    private Context mContext;
    private final Realm realm;
    private SharedPreferences sharedPreferences;

    public NYTController(Context context, Application application) {
        this.mContext = context;
        realm = Realm.getDefaultInstance();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
       // ArticleDataHelper.clearAll(realm);
       // CategoryDataHelper.startCategory(realm,application);
    }

    public void fetchMostPopularArticles(String category){
        final NewYorkTimesAPI apiService = NewYorkTimesClient.getClient().create(NewYorkTimesAPI.class);
        Call<List<Article>> apiCall = apiService.getMostViewedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                articles = response.body();

                ArticleDataHelper.insertViewedArticles(realm,articles);
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }

    public void fetchMostSharedArticles(String category){
        final NewYorkTimesAPI apiService = NewYorkTimesClient.getClient().create(NewYorkTimesAPI.class);
        Call<List<Article>> apiCall = apiService.getMostSharedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                articles = response.body();
                //Timber.d("Num: "+articles.size());
                ArticleDataHelper.insertSharedArticles(realm,articles);
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }

    public void close(){
        Timber.d("Controller closed");
        realm.close();
    }

    public int countArticles(){
        return ArticleDataHelper.getCount(realm);
    }

    public RealmResults<Category> getSelectedCategories(){
        return CategoryDataHelper.getSeletedCategories(realm);
    }


    public void fetchDailyArticles(){


        Timber.d("fetch dayley: "+ (Looper.getMainLooper().getThread() == Thread.currentThread()));
        if(sharedPreferences.getBoolean(mContext.getResources().getString(R.string.pref_remove),false)){
            ArticleDataHelper.clearAll(realm);
        }

        RealmResults<Category> categories = getSelectedCategories();

        for(Category cat : categories){
            Timber.d(cat.getCategory());
            fetchMostSharedArticles(cat.getCategory());
            fetchMostPopularArticles(cat.getCategory());
        }

    }
}

