package com.android.pena.david.news4u.ui.save;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.ui.save.adapter.SavedArticlesAdapter;
import com.android.pena.david.news4u.ui.settings.SettingsActivity;
import com.android.pena.david.news4u.utils.NYTController;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class SavedActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.refresh_saved_articles) SwipeRefreshLayout refreshArticles;
    @BindView(R.id.saved_articles_list) RecyclerView articlesList;
    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;

    private SavedArticlesAdapter savedArticlesAdapter;
    private NYTController nytController;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        nytController = new NYTController(this,getApplication());
        ref = News4UApp.getSavedArticleEndpoint();
        savedArticleListener();
        setDrawer();
        setSavedArticles();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_articles) {
            finish();
        }
        if(id == R.id.nav_settings){
            startActivity(new Intent(SavedActivity.this, SettingsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onRefresh() {
        refreshArticles.setRefreshing(false);
    }

    private void setDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }


    private void savedArticleListener(){
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Timber.d("onChildAdded");
                ArticleData a = dataSnapshot.getValue(ArticleData.class);
                savedArticlesAdapter.addArticle(a);
                refreshArticles.setRefreshing(false);
            }
            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Timber.d("onChildChanged");
                ArticleData a = dataSnapshot.getValue(ArticleData.class);

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                Timber.d("onChildRemoved");
                ArticleData a = dataSnapshot.getValue(ArticleData.class);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                Timber.d("onChildMoved");
                ArticleData a = dataSnapshot.getValue(ArticleData.class);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Timber.d("onCancelled");
            }
        });
    }


    private void setSavedArticles(){
        refreshArticles.setOnRefreshListener(this);
        refreshArticles.setRefreshing(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setAutoMeasureEnabled(false);
        articlesList.setLayoutManager(linearLayoutManager);
        savedArticlesAdapter = new SavedArticlesAdapter(this);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(articlesList.getContext(),
                linearLayoutManager.getOrientation());
        articlesList.addItemDecoration(dividerItemDecoration);
        articlesList.setAdapter(savedArticlesAdapter);
    }


}
