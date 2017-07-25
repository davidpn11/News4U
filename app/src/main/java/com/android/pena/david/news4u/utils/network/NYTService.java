package com.android.pena.david.news4u.utils.network;



import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.HandlerThread;



import timber.log.Timber;

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
            NYTController nytController = new NYTController(getApplicationContext(),getApplication());
            nytController.checkClearArticles();
            //nytController.fetchDailyArticles();
            nytController.close();

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
