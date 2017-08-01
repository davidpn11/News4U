package com.android.pena.david.news4u.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

/**
 * Created by david on 12/07/17.
 */

public class generalUtils {

    public static final String EXTRA_ARTICLE_TITLE = "article_title_tag";
    public static final String EXTRA_ARTICLE_ID = "article_id_tag";
    public static final String EXTRA_ARTICLE_URL = "article_url_tag";
    public static final String ACTION_SAVED_ARTICLE = "action_saved_article";
    public static final String ACTION_ARTICLE = "article_action";
    public static final String ACTION_OPEN_ARTICLE = "open_article_widget";
    public static final String LOAD_WIDGET = "load_widget";


    public static final String VIEWED_TAG = "most_view";
    public static final String SHARED_TAG = "most_shared";
    public static final String BOTH_TAG = "both_selection";

    public static void ImageViewAnimatedChange(Context c, final ImageView v, final Bitmap new_image) {
        final Animation anim_out = AnimationUtils.loadAnimation(c, android.R.anim.fade_out);
        final Animation anim_in  = AnimationUtils.loadAnimation(c, android.R.anim.fade_in);
        anim_out.setAnimationListener(new Animation.AnimationListener()
        {
            @Override public void onAnimationStart(Animation animation) {}
            @Override public void onAnimationRepeat(Animation animation) {}
            @Override public void onAnimationEnd(Animation animation)
            {
                v.setImageBitmap(new_image);
                anim_in.setAnimationListener(new Animation.AnimationListener() {
                    @Override public void onAnimationStart(Animation animation) {}
                    @Override public void onAnimationRepeat(Animation animation) {}
                    @Override public void onAnimationEnd(Animation animation) {}
                });
                v.startAnimation(anim_in);
            }
        });
        v.startAnimation(anim_out);
    }
}
