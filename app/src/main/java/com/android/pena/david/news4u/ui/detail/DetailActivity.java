package com.android.pena.david.news4u.ui.detail;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.utils.generalUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david on 17/07/17.
 */

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.article_img) ImageView articleImg;
    @BindView(R.id.article_title) TextView articleTitle;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Bundle extras = getIntent().getExtras();
        String img_url = extras.getString(generalUtils.EXTRA_ARTICLE_ID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            String imageTransitionName = extras.getString(generalUtils.EXTRA_ARTICLE_TITLE);
            articleImg.setTransitionName(imageTransitionName);
            articleTitle.setText(imageTransitionName);
        }

        Picasso.with(this)
                .load(img_url)
                .noFade()
                .into(articleImg, new Callback() {
                    @Override
                    public void onSuccess() {
                        supportStartPostponedEnterTransition();
                    }

                    @Override
                    public void onError() {
                        supportStartPostponedEnterTransition();
                    }
                });
    }
}
