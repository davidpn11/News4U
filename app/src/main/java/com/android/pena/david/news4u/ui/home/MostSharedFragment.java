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

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.ui.home.adapter.ArticlesAdapter;
import com.android.pena.david.news4u.utils.NYTController;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import timber.log.Timber;

/**
 * Created by david on 05/07/17.
 */

public class MostSharedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    @BindView(R.id.refresh_articles) SwipeRefreshLayout refreshArticles;
    @BindView(R.id.articles_list) RecyclerView articlesList;

    private static final String STATE_SHARED_ARRAY = "MOST_SHARED_ARTICLES_ARRAY";

    private NYTController nytController;
    private ArticlesAdapter articlesAdapter;
    private ArrayList<ArticleData> mArticles;
    private DatabaseReference ref;

    public MostSharedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nytController = new NYTController(getContext());

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared,container,false);
        ButterKnife.bind(this,view);
        refreshArticles.setRefreshing(true);

        ref = News4UApp.getArticleMostSharedEndpoint();
        if(savedInstanceState == null){
            mArticles = new ArrayList<>();
        }else{
            mArticles = savedInstanceState.getParcelableArrayList(STATE_SHARED_ARRAY);
            refreshArticles.setRefreshing(false);
        }

        if (mArticles.isEmpty()) {
            articlesListener();
        }
        setArticlesList();
        return view;
    }

    @Override
    public void onRefresh() {
        nytController.fetchDailySharedArticles();
        articlesListener();
        refreshArticles.setRefreshing(false);
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if(!mArticles.isEmpty()){
            outState.putParcelableArrayList(STATE_SHARED_ARRAY,mArticles);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void articlesListener(){
        ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Timber.d("onChildAdded");
                refreshArticles.setRefreshing(false);
                ArticleData a = dataSnapshot.getValue(ArticleData.class);
                mArticles.add(a);
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
