package com.android.pena.david.news4u.utils.network;

import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.utils.gson.ArticleTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by david on 06/07/17.
 */


public class NewYorkTimesClient {

    private static final String BASE_URL = "https://api.nytimes.com/svc/mostpopular/v2/";

    private static Retrofit retrofit = null;
    public static Retrofit getClient(){
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
        return retrofit;
    }
}