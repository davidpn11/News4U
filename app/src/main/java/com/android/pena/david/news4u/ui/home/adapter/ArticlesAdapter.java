package com.android.pena.david.news4u.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.ui.detail.DetailActivity;
import com.android.pena.david.news4u.ui.home.ArticlesActivity;
import com.android.pena.david.news4u.utils.generalUtils;
import com.squareup.picasso.Picasso;


import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 13/07/17.
 */

public class ArticlesAdapter extends RecyclerView.Adapter<ArticlesAdapter.ViewHolder>{

    private RealmResults<Article> articles;
    private Context mContext;
    private int lastPosition;

    public ArticlesAdapter(Context context, RealmResults<Article> articles){
        //setHasStableIds(true);
        lastPosition = -1;
        this.mContext = context;
        this.articles = articles;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.article_row,parent,false);
        return new ArticlesAdapter.ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bindArticle(articles.get(position));
       // setAnimation(holder.itemView,position);
    }

    @Override
    public int getItemCount() {
        if( articles != null){
            return articles.size();
        }else{
            return 0;
        }

    }
    private void setAnimation(View viewToAnimate, int position)
    {
//
        if (position > lastPosition)
        {
            Animation animation = AnimationUtils.loadAnimation(mContext, android.R.anim.slide_in_left);
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    public void updateArticles(RealmResults<Article> pArticles){
        final ArticlesDiffCallBack diffCallBack = new ArticlesDiffCallBack(this.articles,pArticles);
        final DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallBack);

        articles = pArticles;
        notifyDataSetChanged();
        //diffResult.dispatchUpdatesTo(this);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.article_title) TextView articleTitle;
    @BindView(R.id.article_img) ImageView articleImg;
    @BindView(R.id.article_category) TextView articleCategory;
    @BindView(R.id.article_layout) ConstraintLayout layout;

    private Article mArticle;

    private ViewHolder(View itemView){
        super(itemView);
        ButterKnife.bind(this,itemView);
        layout.setOnClickListener(this);
    }

    private void bindArticle(Article article){
        mArticle = article;
        articleTitle.setText(article.getTitle());
        articleCategory.setText(article.getSection());
        if(article.getMedia() != null) {
            Picasso.with(mContext).load(article.getMedia().getUrl()).into(articleImg);
            ViewCompat.setTransitionName(articleImg,article.getTitle());
        }else{
            articleImg.setImageResource(R.mipmap.ic_no_image);
        }
    }

    @Override
    public void onClick(View v) {
        Timber.d("clicked");
        Intent intent = new Intent(mContext, DetailActivity.class);
        intent.setAction(generalUtils.ACTION_ARTICLE);
        intent.putExtra(generalUtils.EXTRA_ARTICLE_ID,mArticle.getId());

        if(mArticle.getMedia() != null){
            ActivityOptionsCompat options = ActivityOptionsCompat.
                    makeSceneTransitionAnimation((ArticlesActivity) mContext,articleImg,
                            ViewCompat.getTransitionName(articleImg));
            mContext.startActivity(intent, options.toBundle());
        }else{
            mContext.startActivity(intent);
        }
    }
}
}
