package com.android.pena.david.news4u.utils.db;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.model.Media;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.utils.generalUtils;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import timber.log.Timber;

/**
 * Created by david on 26/07/17.
 */

public class NytDataHelper implements ArticleDbInterface, SavedArticleDbInterface, CategoryDbInterface {

    private Realm realm;
    private Context mContext;

    public NytDataHelper(Context context) {
        this.mContext = context;
        realm = Realm.getDefaultInstance();
    }

    public void close(){
        realm.close();
    }

    @Override
    public void clearArticles() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(Article.class).findAll().deleteAllFromRealm();
                bgRealm.where(Media.class).findAll().deleteAllFromRealm();
                Timber.d("Articles Cleared");
            }
        });
    }

    @Override
    public void clearCategories() {

    }

    @Override
    public void clearSavedArticles() {

    }

    @Override
    public RealmResults<Article> getArticles() {
        return realm.where(Article.class).findAll();
    }

    @Override
    public RealmResults<Category> getCategories() {
        return null;
    }

    @Override
    public RealmResults<SavedArticle> getSavedArticles() {
        return null;
    }

    @Override
    public int countCategory() {
        return realm.where(Category.class)
                .equalTo("active",true)
                .findAll()
                .size();
    }

    @Override
    public RealmResults<Category> getSeletedCategories() {
        return realm.where(Category.class)
                .equalTo("active",true)
                .findAll();
    }

    @Override
    public RealmResults<Article> getMostViewedArticles() {
        return realm.where(Article.class)
                .beginGroup()
                .equalTo("selection", generalUtils.VIEWED_TAG)
                .or()
                .equalTo("selection", generalUtils.BOTH_TAG)
                .endGroup()
                .findAll();
    }

    @Override
    public int countSavedArticles() {
        return 0;
    }

    @Override
    public boolean hasCategories() {
        return !realm.where(Category.class).findAll().isEmpty();
    }

    @Override
    public boolean hasSavedArticles() {
        return false;
    }

    @Override
    public void startCategory(List<Category> pCategories) {
        if(hasCategories()) return;

        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                String[] categories = mContext.getResources().getStringArray(R.array.categories);
                for (int i = 0; i < categories.length; i++) {
                    realm.copyToRealmOrUpdate(new Category(categories[i]));
                }
                Timber.d("Categories added!");
            }
        });
    }

    @Override
    public RealmResults<Article> getMostSharedArticles() {

        return realm.where(Article.class)
                .beginGroup()
                .equalTo("selection", generalUtils.SHARED_TAG)
                .or()
                .equalTo("selection", generalUtils.BOTH_TAG)
                .endGroup()
                .findAll();
    }

    @Override
    public void insertSavedArticle(SavedArticle pArticle) {

    }

    @Override
    public int countArticles() {
        return 0;
    }

    @Override
    public boolean hasArticles() {
        return false;
    }

    @Override
    public boolean hasArticle(String id, String selection) {
        Article a = realm.where(Article.class).equalTo("id", id).findFirst();
        if (a != null) {
            if (a.getSelection().equals(selection)) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }

    }

    @Override
    public void insertViewedArticles(final List<Article> pArticles) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    for (Article article : pArticles) {
                        if(article.getSelection() == null){
                            article.setSelection(generalUtils.VIEWED_TAG);
                        }
                        else if (hasArticle(article.getId(), generalUtils.SHARED_TAG)) {
                            article.setSelection(generalUtils.BOTH_TAG);
                        } else if (hasArticle(article.getId(), generalUtils.BOTH_TAG)) {
                            break;
                        }

                        bgRealm.copyToRealmOrUpdate(article);
                        Timber.d("Articles Insert - OK");
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertSharedArticles(final List<Article> pArticles) {
        try {
            realm.executeTransaction(new Realm.Transaction() {
                @Override
                public void execute(Realm bgRealm) {
                    Timber.d("insert: " + (Looper.getMainLooper().getThread() == Thread.currentThread()));
                    for (Article article : pArticles) {
                        if(article.getSelection() == null){
                            article.setSelection(generalUtils.SHARED_TAG);
                        }
                        else if (hasArticle(article.getId(), generalUtils.VIEWED_TAG)) {
                            article.setSelection(generalUtils.BOTH_TAG);
                        } else if (hasArticle(article.getId(), generalUtils.BOTH_TAG)) {
                            break;
                        }
                        bgRealm.copyToRealmOrUpdate(article);
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void insertViewedArticlesAsync(final List<Article> pArticles) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                for (Article article : pArticles) {
                    if(article.getSelection() == null){
                        article.setSelection(generalUtils.VIEWED_TAG);
                    }
                    else if (hasArticle(article.getId(), generalUtils.SHARED_TAG)) {
                        article.setSelection(generalUtils.BOTH_TAG);
                    } else if (hasArticle(article.getId(), generalUtils.BOTH_TAG)) {
                        break;
                    }
                    bgRealm.copyToRealmOrUpdate(article);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("Articles Insert - OK");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.d("Articles Insert fail:" + error.getMessage());
            }
        });
    }

    @Override
    public void insertSharedArticlesAsync(final List<Article> pArticles) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                Timber.d("insert: " + (Looper.getMainLooper().getThread() == Thread.currentThread()));
                for (Article article : pArticles) {
                    if(article.getSelection() == null){
                        article.setSelection(generalUtils.SHARED_TAG);
                    }
                    else if (hasArticle(article.getId(), generalUtils.VIEWED_TAG)) {
                        article.setSelection(generalUtils.BOTH_TAG);
                    } else if (hasArticle(article.getId(), generalUtils.BOTH_TAG)) {
                        break;
                    }
                    bgRealm.copyToRealmOrUpdate(article);
                }
            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("Articles Insert - OK");

            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.d("Articles Insert fail:" + error.getMessage());
            }
        });
    }
}

