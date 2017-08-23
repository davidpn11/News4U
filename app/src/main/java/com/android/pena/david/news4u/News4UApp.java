package com.android.pena.david.news4u;

import android.app.Application;

import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.utils.network.DispatcherUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class News4UApp extends Application {

    private FirebaseDatabase firebase;
    private static DatabaseReference articleMostViewedEndpoint,articleMostSharedEndPoint,savedArticleEndpoint,categoryEndpoint;



    public static DatabaseReference getArticleMostViewedEndpoint(){
        return articleMostViewedEndpoint;
    }
    public static DatabaseReference getSavedArticleEndpoint(){
        return savedArticleEndpoint;
    }
    public static DatabaseReference getCategoryEndpoint(){return categoryEndpoint;}
    public static DatabaseReference getArticleMostSharedEndpoint(){return articleMostSharedEndPoint;}

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }

        firebase = FirebaseDatabase.getInstance();
        firebase.setPersistenceEnabled(true);
        articleMostViewedEndpoint = firebase.getReference("ArticleMostViewed");
        articleMostSharedEndPoint= firebase.getReference("ArticleMostShared");
        savedArticleEndpoint = firebase.getReference("SavedArticle");
        categoryEndpoint = firebase.getReference("Category");


        Realm.init(this);

        Realm.Transaction transaction = new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if(realm.where(Category.class).findAll().isEmpty()){
                    String[] categories = getApplicationContext().getResources().getStringArray(R.array.categories);
                    for (String category : categories) {
                        realm.copyToRealmOrUpdate(new Category(category));
                    }
                    Timber.d("Categories added!");
                }
            }
        };

        RealmConfiguration config = new RealmConfiguration.Builder()
                .name("myrealm.realm")
                .initialData(transaction)
                .build();
        Realm.setDefaultConfiguration(config);
        DispatcherUtils.scheduleNYTReminder(this);
        startCategories();
    }

    private void startCategories(){
        categoryEndpoint.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        if(!dataSnapshot.hasChildren()){
                            String[] categories = getApplicationContext().getResources().getStringArray(R.array.categories);
                            for (String category : categories) {
                                //String key = categoryEndpoint.push().getKey();
                                Category c = new Category(category);
                                categoryEndpoint.child(c.getCategory()).setValue(c);
                            }
                            Timber.d("Categories added!");
                        }
                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.d(databaseError.getMessage());
                    }
                });
    }
}
