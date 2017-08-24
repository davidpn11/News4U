package com.android.pena.david.news4u.utils.network;

import com.android.pena.david.news4u.model.ArticleData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by david on 06/07/17.
 */
public interface NewYorkTimesAPI {

    @GET("mostviewed/{category}/1.json")
    Call<List<ArticleData>> getMostViewedArticles(@Path("category") String category,
                                                  @Query("api-key") String apiKey);

    @GET("mostshared/{category}/1.json")
    Call<List<ArticleData>> getMostSharedArticles(@Path("category") String category,
                                              @Query("api-key") String apiKey);
}
