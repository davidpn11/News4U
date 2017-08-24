package com.android.pena.david.news4u.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.model.CategoryData;
import com.android.pena.david.news4u.utils.network.NYTApiClient;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import timber.log.Timber;


/**
 * Created by david on 06/07/17.
 */

public class NYTController {


    private Context mContext;
    private SharedPreferences sharedPreferences;
    private NYTApiClient nytApiClient;

    public NYTController(Context context, Application application) {
        this.mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        nytApiClient = new NYTApiClient(context);
        //fetchDailyArticles();
    }


    public boolean fetchDailyArticles(){

        DatabaseReference ref = News4UApp.getCategoryEndpoint();
        ref.addListenerForSingleValueEvent(
        new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    CategoryData cat = postSnapshot.getValue(CategoryData.class);
                    if(cat.isActive()) {
                        nytApiClient.fetchMostPopularArticles(cat.getCategory());
                        nytApiClient.fetchMostSharedArticles(cat.getCategory());
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Timber.e(databaseError.getMessage());
            }
        });
        return true;
    }

    public boolean fetchDailySharedArticles(){
        DatabaseReference ref = News4UApp.getCategoryEndpoint();
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            CategoryData cat = postSnapshot.getValue(CategoryData.class);
                            if (cat.isActive()) {
                                nytApiClient.fetchMostSharedArticles(cat.getCategory());
                            }
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e(databaseError.getMessage());
                    }
                });
        return true;
    }

    public boolean fetchDailyViewedArticles(){
        DatabaseReference ref = News4UApp.getCategoryEndpoint();
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            CategoryData cat = postSnapshot.getValue(CategoryData.class);
                            if (cat.isActive()) {
                                nytApiClient.fetchMostPopularArticles(cat.getCategory());
                            }
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e(databaseError.getMessage());
                    }
                });
        return true;
    }

}

