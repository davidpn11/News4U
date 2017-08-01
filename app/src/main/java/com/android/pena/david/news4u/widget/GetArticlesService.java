package com.android.pena.david.news4u.widget;

import android.app.IntentService;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.android.pena.david.news4u.utils.generalUtils;

/**
 * Created by david on 31/07/17.
 */

public class GetArticlesService extends IntentService {


    public GetArticlesService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (generalUtils.ACTION_OPEN_ARTICLE.equals(action)) {

            }
        }
    }


    public static void startActionOpenArticle(Context context){
        Intent intent = new Intent(context, GetArticlesService.class);
        //intent.
        context.startService(intent);
    }
}
