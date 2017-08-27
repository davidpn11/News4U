package com.android.pena.david.news4u.utils.network;



import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.v7.preference.PreferenceManager;


import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.ui.detail.DetailActivity;
import com.android.pena.david.news4u.ui.fullarticle.FullArticleActivity;
import com.android.pena.david.news4u.utils.NYTController;
import com.google.firebase.database.DatabaseReference;

import timber.log.Timber;

import static com.android.pena.david.news4u.utils.generalUtils.EXTRA_ARTICLE_URL;

/**
 * Created by david on 24/07/17.
 */

public class NYTService extends JobService {

    public NYTService() {
        super();
    }

    @Override
    public boolean onStartJob(final JobParameters params) {
        try {
            HandlerThread handlerThread = new HandlerThread("SomeOtherThread");
            handlerThread.start();
            Timber.e("Start job");
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
            if(sharedPreferences.getBoolean(getResources().getString(R.string.pref_remove_key),true)){
                DatabaseReference ref1 = News4UApp.getArticleMostSharedEndpoint();
                DatabaseReference ref2 = News4UApp.getArticleMostViewedEndpoint();
                ref1.setValue(null);
                ref2.setValue(null);
                NYTController nytController = new NYTController(getApplicationContext());
                nytController.fetchDailyArticles();
            }

            jobFinished(params, true);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        Timber.d("job finished");
        return false;
    }
}
