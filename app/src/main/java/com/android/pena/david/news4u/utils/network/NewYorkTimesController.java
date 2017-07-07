package com.android.pena.david.news4u.utils.network;

import android.content.Context;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
import com.android.pena.david.news4u.model.Article;

import java.util.List;

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

    public NewYorkTimesController(Context context) {
        this.mContext = context;
    }

    public void getArticles(){
        final NewYorkTimesAPI apiService = NewYorkTimesClient.getClient().create(NewYorkTimesAPI.class);
        Call<List<Article>> apiCall = apiService.getMostViewdArticles("Automobiles",API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());
        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                articles = response.body();
                Timber.d(articles.toString());
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });

    }
}
