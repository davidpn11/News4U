package com.android.pena.david.news4u.utils.network;

import android.content.Context;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.utils.db.NytDataHelper;
import com.android.pena.david.news4u.utils.gson.ArticleTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import timber.log.Timber;

/**
 * Created by david on 06/07/17.
 */


public class NYTApiClient {

    private static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";
    private static Retrofit retrofit = null;
    private static final String API_KEY_VALUE = BuildConfig.API_KEY;

    private  NewYorkTimesAPI apiService;
    private Context mContext;


    public NYTApiClient(Context context) {
        this.mContext = context;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        Type collectionType = new TypeToken<List<Article>>(){}.getType();
        gsonBuilder.registerTypeAdapter(collectionType,new ArticleTypeAdapter());
        final Gson gson = gsonBuilder.create();

        if(retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        apiService = retrofit.create(NewYorkTimesAPI.class);
    }


    public void fetchMostPopularArticles(String category, final NytDataHelper dataHelper){

        Call<List<Article>> apiCall = apiService.getMostViewedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articles = response.body();
               // Timber.d(articles.toString());
                dataHelper.insertViewedArticlesAsync(articles);

            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }

    public void fetchMostPopularArticlesAsync(String category, final NytDataHelper dataHelper){

        Call<List<Article>> apiCall = apiService.getMostViewedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articles = response.body();
                // Timber.d(articles.toString());
                dataHelper.insertViewedArticlesAsync(articles);

            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }



    public void fetchMostSharedArticles(String category, final NytDataHelper dataHelper){

        Call<List<Article>> apiCall = apiService.getMostSharedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articles = response.body();
                //Timber.d(articles.toString());
                dataHelper.insertSharedArticlesAsync(articles);
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }

    public void fetchMostSharedArticlesAsync(String category, final NytDataHelper dataHelper){

        Call<List<Article>> apiCall = apiService.getMostSharedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articles = response.body();
                //Timber.d(articles.toString());
                dataHelper.insertSharedArticlesAsync(articles);
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }

}