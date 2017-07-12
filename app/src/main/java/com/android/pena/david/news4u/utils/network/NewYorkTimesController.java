package com.android.pena.david.news4u.utils.network;

import android.app.Application;
import android.content.Context;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.utils.db.ArticleRealmController;
import com.android.pena.david.news4u.utils.db.CategoryRealmController;

import java.util.List;

import io.realm.Realm;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import timber.log.Timber;

/**
 * Created by david on 06/07/17.
 */

public class NewYorkTimesController{

    private static final String API_KEY_VALUE = BuildConfig.API_KEY;
    private List<Article> articles;
    private Context mContext;
    private ArticleRealmController articleController;
    private CategoryRealmController categoriesController;
    private final Realm realm;
    public NewYorkTimesController(Context context, Application application) {
        this.mContext = context;
        realm = Realm.getDefaultInstance();
        articleController = new ArticleRealmController();
        articleController.clearAll(realm);
        categoriesController = new CategoryRealmController(realm,application);

        Timber.d(categoriesController.getCount(realm)+"");

    }

    public void getArticles(){
        final NewYorkTimesAPI apiService = NewYorkTimesClient.getClient().create(NewYorkTimesAPI.class);
        Call<List<Article>> apiCall = apiService.getMostViewdArticles("Arts",API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                articles = response.body();
                Timber.d("Num: "+articles.size());
               articleController.insertArticles(realm,articles);
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }



    public void close(){
        realm.close();
    }
}

