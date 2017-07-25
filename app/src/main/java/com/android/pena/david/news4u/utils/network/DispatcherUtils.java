package com.android.pena.david.news4u.utils.network;


import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;


import java.util.List;
import java.util.concurrent.TimeUnit;

import timber.log.Timber;

/**
 * Created by david on 25/07/17.
 */

public class DispatcherUtils {

    private static final int REMINDER_INTERVAL_HOURS = 24;
    private static final int REMINDER_JOB_ID = 101;

    private static boolean sInitialized;

    synchronized public static void scheduleNYTReminder(@NonNull final Context context) {

        if(sInitialized) return;

        JobScheduler jobScheduler = (JobScheduler) context.getSystemService(Context.JOB_SCHEDULER_SERVICE);
        JobInfo job = getJobInfo(REMINDER_JOB_ID,REMINDER_INTERVAL_HOURS,new ComponentName(context,NYTService.class));

        int result = jobScheduler.schedule(job);
        if (result == JobScheduler.RESULT_SUCCESS) {
            Timber.d( "Scheduled job successfully!");
        }else{
            Timber.d( "Scheduled job failed!");
        }
        sInitialized = true;

    }

    private static JobInfo getJobInfo(final int id, final long hour, final ComponentName name) {
        final long interval = TimeUnit.HOURS.toMillis(hour); // run every hour
        final boolean isPersistent = true; // persist through boot
        final int networkType = JobInfo.NETWORK_TYPE_ANY; // Requires some sort of connectivity

        final JobInfo jobInfo;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            jobInfo = new JobInfo.Builder(id, name)
                    .setMinimumLatency(interval)
                    .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        } else {
            jobInfo = new JobInfo.Builder(id, name)
                    .setPeriodic(interval)
                    .setRequiredNetworkType(networkType)
                    .setPersisted(isPersistent)
                    .build();
        }
        return jobInfo;
    }
}
