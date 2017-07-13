package com.android.pena.david.news4u.utils.network;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
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

/**
 * Created by david on 06/07/17.
 */

public class NYTController {

    private static final String API_KEY_VALUE = BuildConfig.API_KEY;
    private List<Article> articles;
    private Context mContext;
    private final Realm realm;


    public NYTController(Context context, Application application) {
        this.mContext = context;
        realm = Realm.getDefaultInstance();
     //     ArticleDataHelper.clearAll(realm);
    }

    public void fetchArticles(){
        final NewYorkTimesAPI apiService = NewYorkTimesClient.getClient().create(NewYorkTimesAPI.class);
        Call<List<Article>> apiCall = apiService.getMostViewdArticles("Arts",API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                articles = response.body();
                Timber.d("Num: "+articles.size());
                ArticleDataHelper.insertArticles(realm,articles);
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


    public RealmResults<Category> getSelectedCategories(){
        return CategoryDataHelper.getSeletedCategories(realm);
    }
}

