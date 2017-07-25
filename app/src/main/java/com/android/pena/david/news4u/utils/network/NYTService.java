package com.android.pena.david.news4u.utils.network;

import android.app.job.JobParameters;
import android.app.job.JobService;

/**
 * Created by david on 24/07/17.
 */

public class NYTService extends JobService {


    public NYTService() {
        super();
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        return false;
    }
}
