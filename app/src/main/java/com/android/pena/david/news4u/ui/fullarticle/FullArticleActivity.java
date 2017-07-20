package com.android.pena.david.news4u.ui.fullarticle;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;


import com.android.pena.david.news4u.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

import static com.android.pena.david.news4u.utils.generalUtils.EXTRA_ARTICLE_URL;


public class FullArticleActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.fullarticle_webview) WebView webView;
    @BindView(R.id.toolbar) Toolbar toolbar;

    private String url;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_article);
        ButterKnife.bind(this);

        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        toolbar.setNavigationOnClickListener(this);
        url = getIntent().getStringExtra(EXTRA_ARTICLE_URL);
        if(url != null){
            loadWebView(url);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Timber.d("back pressed");
    }

    @Override
    public void onClick(View v) {
        Timber.d("back pressed");
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fullarticle, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_browser) {
            openOnBrowser();
        }else if (id == R.id.action_share) {
            shareArticle();
        }

        return super.onOptionsItemSelected(item);
    }

    private void shareArticle(){

        String msg = "Hey! Check out this article: "+url;
        Intent it = new Intent(android.content.Intent.ACTION_SEND);
        it.setType("text/plain");
        it.putExtra(android.content.Intent.EXTRA_SUBJECT, "Popular Movies");
        it.putExtra(android.content.Intent.EXTRA_TEXT, msg);
        startActivity(Intent.createChooser(it,getResources().getString(R.string.share_string)));
    }

    private void openOnBrowser(){
        Intent i = new Intent(Intent.ACTION_VIEW);
        i.setData(Uri.parse(url));
        startActivity(i);
    }

    private void loadWebView(String url){
        try {
            webView.setWebViewClient(new WebViewClient());
            webView.getSettings().setLoadsImagesAutomatically(true);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            webView.loadUrl(url);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
