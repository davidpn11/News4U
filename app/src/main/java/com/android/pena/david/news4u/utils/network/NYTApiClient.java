package com.android.pena.david.news4u.utils.network;

import android.content.Context;
import android.widget.Toast;

import com.android.pena.david.news4u.BuildConfig;
import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.utils.db.NytDataHelper;
import com.android.pena.david.news4u.utils.gson.ArticleTypeAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
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
    private DatabaseReference refMostViewed;
    private DatabaseReference refMostShared;

    public NYTApiClient(Context context) {
        this.mContext = context;
        final GsonBuilder gsonBuilder = new GsonBuilder();
        refMostViewed = News4UApp.getArticleMostViewedEndpoint();
        refMostShared = News4UApp.getArticleMostSharedEndpoint();
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




    public void fetchMostPopularArticles(String category){

        Call<List<Article>> apiCall = apiService.getMostViewedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articles = response.body();

                if(articles.isEmpty()) return;

                for(Article article : articles){
                    refMostViewed.child(article.getId()).setValue(article);
                }
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }



    public void fetchMostSharedArticles(String category){

        Call<List<Article>> apiCall = apiService.getMostSharedArticles(category,API_KEY_VALUE);
        Timber.d(apiCall.request().url().toString());

        apiCall.enqueue(new Callback<List<Article>>() {
            @Override
            public void onResponse(Call<List<Article>> call, Response<List<Article>> response) {
                List<Article> articles = response.body();

                for(Article article : articles){
                    refMostShared.child(article.getId()).setValue(article);
                }
            }
            @Override
            public void onFailure(Call<List<Article>> call, Throwable t) {
                Toast.makeText(mContext, "FALHOU", Toast.LENGTH_SHORT).show();
                Timber.e(t.getMessage());
            }
        });
    }



}