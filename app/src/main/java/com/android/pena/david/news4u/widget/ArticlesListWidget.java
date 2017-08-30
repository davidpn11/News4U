package com.android.pena.david.news4u.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.ui.detail.DetailActivity;
import com.android.pena.david.news4u.ui.home.ArticlesActivity;
import com.android.pena.david.news4u.utils.GeneralUtils;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.io.EOFException;
import java.io.IOException;

/**
 * Implementation of App Widget functionality.
 */
public class ArticlesListWidget extends AppWidgetProvider {

    private static ArticleData widgetArticle;


    static void updateAppWidget(final Context context,final AppWidgetManager appWidgetManager,
                                final int appWidgetId) {

        // Construct the RemoteViews object
        final RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.articles_list_widget);
        DatabaseReference ref = News4UApp.getArticleMostViewedEndpoint();

        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot data: dataSnapshot.getChildren()){
                        widgetArticle = data.getValue(ArticleData.class);
                        views.setTextViewText(R.id.widget_title,widgetArticle.getTitle());

                            Picasso.with(context).load(widgetArticle.getUrl()).into(views,R.id.widget_img,new int[] {appWidgetId});
//                            views.setBitmap(R.id.widget_img,widgetArticle.getId(),Picasso.with(context).load(widgetArticle.getUrl()).get());
                        Intent it = new Intent(context, DetailActivity.class);
                        it.putExtra(GeneralUtils.ARTICLE_PARCELABLE,widgetArticle);
                        PendingIntent pendingIntent = PendingIntent.getActivity(context,0, it, PendingIntent.FLAG_UPDATE_CURRENT);
                        views.setOnClickPendingIntent(R.id.app_widget,pendingIntent);

                        // Instruct the widget manager to update the widget
                        appWidgetManager.updateAppWidget(appWidgetId, views);

                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId);
        }
    }


    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

