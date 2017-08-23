package com.android.pena.david.news4u.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.ui.home.adapter.ArticlesAdapter;
import com.android.pena.david.news4u.utils.NYTController;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 05/07/17.
 */

public class MostPopularFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.refresh_articles) SwipeRefreshLayout refreshArticles;
    @BindView(R.id.articles_list) RecyclerView articlesList;


    private NYTController nytController;
    private ArticlesAdapter articlesAdapter;
    private ArrayList<ArticleData> mArticles;
    private DatabaseReference ref;
    public MostPopularFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nytController = new NYTController(getContext(),getActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_popular,container,false);
        ButterKnife.bind(this,view);
        //onRefresh();
        refreshArticles.setRefreshing(false);
        mArticles = new ArrayList<>();
        ref = News4UApp.getArticleMostViewedEndpoint();
        articlesListener();
        setArticlesList();
        return view;
    }

    @Override
    public void onRefresh() {
        nytController.fetchDailyViewedArticles();
        refreshArticles.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    private void addArticle(ArticleData a){
        articlesAdapter.addArticle(a);
    }

    private void articlesListener(){
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Timber.d("onChildAdded");
                ArticleData a = dataSnapshot.getValue(ArticleData.class);
                addArticle(a);
             //   mArticles.add(a);
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


    public void setArticlesList(){
        refreshArticles.setOnRefreshListener(this);
        //refreshArticles.setRefreshing(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        articlesList.setLayoutManager(linearLayoutManager);
        articlesAdapter = new ArticlesAdapter(getContext(),mArticles);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(articlesList.getContext(),
                linearLayoutManager.getOrientation());
        articlesList.addItemDecoration(dividerItemDecoration);
        articlesList.setAdapter(articlesAdapter);
    }
}

