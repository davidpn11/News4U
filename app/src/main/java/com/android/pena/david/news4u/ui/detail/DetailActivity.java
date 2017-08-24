package com.android.pena.david.news4u.ui.detail;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.preference.PreferenceManager;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.TextAppearanceSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.ui.fullarticle.FullArticleActivity;
import com.android.pena.david.news4u.utils.NYTController;
import com.android.pena.david.news4u.utils.generalUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;


import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import timber.log.Timber;

import static com.android.pena.david.news4u.utils.generalUtils.EXTRA_ARTICLE_URL;

/**
 * Created by david on 17/07/17.
 */

public class DetailActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.article_img) ImageView articleImg;
    @BindView(R.id.article_title) TextView articleTitle;
    @BindView(R.id.article_byline) TextView articleByLine;
    @BindView(R.id.article_description) TextView articleDescription;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.goto_article) Button gotoArticleBtn;
    @BindView(R.id.save_fab) FloatingActionButton saveFab;

    private ArticleData mArticle;
    private Bitmap saveOn,saveOff;
    private boolean saved;
    private DatabaseReference ref;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        if(getIntent().hasExtra(generalUtils.ARTICLE_PARCELABLE)){
            mArticle = getIntent().getExtras().getParcelable(generalUtils.ARTICLE_PARCELABLE);
            buildArticle();
        }

        checkSaved();
        saveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!saved){
                        ref.setValue(mArticle);
                        saved = true;
                        saveFab.setImageBitmap(saveOn);
                        Snackbar.make(v, getResources().getString(R.string.article_saved), Snackbar.LENGTH_SHORT)
                                .show();

                }else{
                        ref.setValue(null);
                       // nytController.deleteArticle(mArticle.getId());
                        saved = false;
                        saveFab.setImageBitmap(saveOff);
                        Snackbar.make(v, getResources().getString(R.string.article_deleted), Snackbar.LENGTH_SHORT)
                                .show();

                }
            }
        });

        gotoArticleBtn.setOnClickListener(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void checkSaved(){
        saveOn = BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_save_on);
        saveOff = BitmapFactory.decodeResource(this.getResources(),R.mipmap.ic_save_off);

        ref = News4UApp.getSavedArticleEndpoint().child(mArticle.getId());
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.child("id").exists()) {
                    saveFab.setImageBitmap(saveOn);
                    saved = true;
                }else{
                    saveFab.setImageBitmap(saveOff);
                    saved = false;
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onClick(View v) {

        if(sharedPreferences.getBoolean(getResources().getString(R.string.pref_browser_key),false)){
            Intent it = new Intent(DetailActivity.this, FullArticleActivity.class);
            it.putExtra(EXTRA_ARTICLE_URL,mArticle.getUrl());
            startActivity(it);
        }else{
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mArticle.getUrl()));
            startActivity(i);
        }


    }

    private void buildArticle(){
        try {

            articleTitle.setText(mArticle.getTitle());

            if(mArticle.getMedia() != null){
                articleImg.setTransitionName(mArticle.getTitle());
                Picasso.with(this)
                        .load(mArticle.getMedia().getUrl())
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

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd",Locale.getDefault());
            Date currentDate = sdf.parse(mArticle.getPublishedDate());
            SimpleDateFormat format = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
            String formatedDate = format.format(currentDate);
            formatedDate = formatedDate+" ";
            String byline = mArticle.getByline();
            SpannableStringBuilder ssb = new SpannableStringBuilder(formatedDate + byline);
            ssb.setSpan(new TextAppearanceSpan(this,R.style.TextStyle_ByLine,Color.WHITE),formatedDate.length()+2,
                    formatedDate.length()+byline.length(), Spannable.SPAN_INCLUSIVE_INCLUSIVE);

            articleByLine.setText(ssb);
            articleDescription.setText(mArticle.get_abstract());

        } catch (Exception e) {
            Timber.e(e.getMessage());
            e.printStackTrace();
        }


    }
}
