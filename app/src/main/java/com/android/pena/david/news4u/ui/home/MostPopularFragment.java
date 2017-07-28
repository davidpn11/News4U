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

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.ui.home.adapter.ArticlesAdapter;
import com.android.pena.david.news4u.utils.NYTController;

import butterknife.BindView;
import butterknife.ButterKnife;
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
    private RealmResults<Article> mArticles;
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

        mArticles = nytController.getMostViewedArticles();
        Timber.d("Got articles: "+mArticles.size());
        setArticlesList();
        return view;
    }

    @Override
    public void onRefresh() {
        //nytController.fetchDailyArticles();
        Timber.d(nytController.getMostViewedArticles().size()+"");
        Timber.d(nytController.getMostSharedArticles().size()+"");
        mArticles = nytController.getMostViewedArticles();
        articlesAdapter.updateArticles(mArticles);
        refreshArticles.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        nytController.close();
    }


    public void setArticlesList(){
        refreshArticles.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        articlesList.setLayoutManager(linearLayoutManager);

        Timber.d("creating adapter");
        articlesAdapter = new ArticlesAdapter(getContext(),mArticles);
        Timber.d("adaptercreated");
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(articlesList.getContext(),
                linearLayoutManager.getOrientation());
        articlesList.addItemDecoration(dividerItemDecoration);
        articlesList.setAdapter(articlesAdapter);
    }
}

