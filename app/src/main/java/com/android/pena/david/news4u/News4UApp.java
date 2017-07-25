package com.android.pena.david.news4u;

import android.app.Application;

import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.utils.db.CategoryDataHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import timber.log.Timber;

public class News4UApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
        Realm.init(this);

        Realm.Transaction transaction = new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {

                if(!CategoryDataHelper.hasCategories(realm)) {
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
    }
}
