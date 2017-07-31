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

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.ui.home.adapter.ArticlesAdapter;
import com.android.pena.david.news4u.utils.NYTController;
import com.android.pena.david.news4u.utils.db.ArticleDataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 05/07/17.
 */

public class MostSharedFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener  {

    @BindView(R.id.refresh_articles) SwipeRefreshLayout refreshArticles;
    @BindView(R.id.articles_list) RecyclerView articlesList;


    private NYTController nytController;
    private ArticlesAdapter articlesAdapter;
    private RealmResults<Article> mArticles;

    public MostSharedFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        nytController = new NYTController(getContext(),getActivity().getApplication());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_shared,container,false);
        ButterKnife.bind(this,view);

        mArticles = nytController.getMostSharedArticles();
        setArticlesList();

        mArticles.addChangeListener(new RealmChangeListener<RealmResults<Article>>() {
            @Override
            public void onChange(RealmResults<Article> articles) {
                newArticles(articles);
                refreshArticles.setRefreshing(false);
            }
        });
        return view;
    }

    @Override
    public void onRefresh() {
        mArticles = nytController.getMostSharedArticles();
        refreshArticles.setRefreshing(false);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private void newArticles(RealmResults<Article> new_articles){
        mArticles = new_articles;
        articlesList.getRecycledViewPool().clear();
        articlesAdapter.notifyDataSetChanged();
    }


    public void setArticlesList(){
        refreshArticles.setOnRefreshListener(this);
        refreshArticles.setRefreshing(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setAutoMeasureEnabled(false);
        refreshArticles.setRefreshing(true);
        articlesList.setLayoutManager(linearLayoutManager);


        articlesAdapter = new ArticlesAdapter(getContext(),mArticles);

        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(articlesList.getContext(),
                linearLayoutManager.getOrientation());
        articlesList.addItemDecoration(dividerItemDecoration);
        articlesList.setAdapter(articlesAdapter);
    }
}
