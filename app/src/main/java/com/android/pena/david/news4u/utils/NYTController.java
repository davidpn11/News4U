package com.android.pena.david.news4u.utils;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.preference.PreferenceManager;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.model.CategoryData;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.utils.db.NytDataHelper;
import com.android.pena.david.news4u.utils.network.NYTApiClient;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import io.realm.RealmResults;
import timber.log.Timber;


/**
 * Created by david on 06/07/17.
 */

public class NYTController {


    private Context mContext;
    private SharedPreferences sharedPreferences;
    private NYTApiClient nytApiClient;


    ArticleData data;
    public NYTController(Context context, Application application) {
        this.mContext = context;
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        nytApiClient = new NYTApiClient(context);



        fetchDailyArticles();
    }

    public void listener(){

//        data = new ArticleData(getMostViewedArticles().get(1));
//
//        databaseReference.child("teste").setValue(data);
//        databaseReference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Timber.d("CHANGED:");
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Timber.d(postSnapshot.getKey());
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        //DatabaseReference ref = FirebaseDatabase.getInstance()getReference("ArticleData")
//        databaseReference.addListenerForSingleValueEvent(
//                new ValueEventListener() {
//                    @Override
//                    public void onDataChange(DataSnapshot dataSnapshot) {
//                        Timber.d("addListenerForSingleValueEvent");
//                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                            Timber.d(postSnapshot.getKey());
//                        }
//                    }
//
//                    @Override
//                    public void onCancelled(DatabaseError databaseError) {
//                        //handle databaseError
//                    }
//                });
//
//        databaseReference.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                Timber.d("new");
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Timber.d(postSnapshot.getKey());
//                }
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//                Timber.d("changed");
//                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
//                    Timber.d(postSnapshot.getKey());
//                }
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
    }


    public void checkClearArticles(){
        if(sharedPreferences.getBoolean(mContext.getResources().getString(R.string.pref_remove_key),false)){
    //        dataHelper.clearArticles();
            Timber.d("Articles cleared");
        }
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


    NytDataHelper dataHelper;

    public boolean hasSelectedCategories(){
        if(dataHelper.getSeletedCategories().size() > 0){
            return true;
        }else{
            return false;
        }
    }

    public boolean isSaved(String id){
        return dataHelper.isSaved(id);
    }

    public Article getArticle(String id ){
        return dataHelper.getArticle(id);
    }

    public Article getSavedArticle(String id){
        return new Article(dataHelper.getSavedArticle(id));
    }

    public RealmResults<SavedArticle> getSavedArticles(){
        return dataHelper.getSavedArticles();
    }

    public void saveArticle(SavedArticle article){
        dataHelper.insertSavedArticle(article);
    }

    public void deleteArticle(String id){
        dataHelper.deletedSavedArticle(id);
    }

}

