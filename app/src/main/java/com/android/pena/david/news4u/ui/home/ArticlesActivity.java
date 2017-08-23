package com.android.pena.david.news4u.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
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
import com.android.pena.david.news4u.model.CategoryData;
import com.android.pena.david.news4u.ui.home.adapter.NewsPagerAdapter;
import com.android.pena.david.news4u.ui.home.Dialog.CategoryDialog;
import com.android.pena.david.news4u.ui.save.SavedActivity;
import com.android.pena.david.news4u.ui.settings.SettingsActivity;
import com.android.pena.david.news4u.utils.NYTController;
import com.android.pena.david.news4u.utils.db.CategoryDataHelper;
import com.android.pena.david.news4u.utils.network.DispatcherUtils;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

public class ArticlesActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    @BindView(R.id.toolbar) Toolbar toolbar;
    @BindView(R.id.drawer_layout) DrawerLayout drawer;
    @BindView(R.id.nav_view) NavigationView navigationView;
    @BindView(R.id.viewpager) ViewPager viewPager;
    @BindView(R.id.tabs) TabLayout tabLayout;

    //private NYTController NYTController;
    private CategoryDialog dialog;
    private static final String DIALOG_TAG ="CATEGORIES_SELECTOR_TAG";
    private NYTController nytController;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_articles);
        ButterKnife.bind(this);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getResources().getString(R.string.home_name));
        nytController = new NYTController(this,getApplication());
        setViewPager();
        setDrawer();
        dialog = new CategoryDialog();
        hasSelectedCategories();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.articles, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_filter) {
            dialog.show(getSupportFragmentManager(),DIALOG_TAG);
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_saved) {
            startActivity(new Intent(ArticlesActivity.this, SavedActivity.class));
        }
        if(id == R.id.nav_settings){
            startActivity(new Intent(ArticlesActivity.this, SettingsActivity.class));
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }


    private void setDrawer(){
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void setViewPager(){
        viewPager.setAdapter(new NewsPagerAdapter(getSupportFragmentManager(),
                ArticlesActivity.this,getResources().getStringArray(R.array.tab_titles)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        tabLayout.setupWithViewPager(viewPager);
    }

    private void hasSelectedCategories(){
        DatabaseReference categoryDb = News4UApp.getCategoryEndpoint();

        categoryDb.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        boolean has_cat = false;
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            CategoryData cat = postSnapshot.getValue(CategoryData.class);
                            if(cat.isActive()) has_cat = true;
                        }
                        if(!has_cat) dialog.show(getSupportFragmentManager(),DIALOG_TAG);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e(databaseError.getMessage());
                    }
                });
    }
}
