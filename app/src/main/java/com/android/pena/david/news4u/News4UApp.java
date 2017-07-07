package com.android.pena.david.news4u;

import android.app.Application;
import timber.log.Timber;

public class News4UApp extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        }
    }
}
