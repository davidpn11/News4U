package com.android.pena.david.news4u.utils.db;

import android.app.Application;
import android.content.Context;
import android.os.Looper;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Article;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.model.Media;
import com.android.pena.david.news4u.model.SavedArticle;
import com.android.pena.david.news4u.model.SavedMedia;
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


    //-------------------Articles--------------------------

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
    public RealmResults<Article> getArticles() {
        return realm.where(Article.class).findAll();
    }


    @Override
    public int countArticles() {
        return realm.where(Article.class).findAll().size();
    }

    @Override
    public boolean hasArticles() {
        return !realm.where(Article.class).findAll().isEmpty();
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
    public Article getArticle(String id) {
        Article a =realm.where(Article.class).equalTo("id",id).findFirst();

        return realm.where(Article.class).equalTo("id",id).findFirst();
    }

    @Override
    public RealmResults<Article> getMostSharedArticles() {

        return realm.where(Article.class)
                .beginGroup()
                .equalTo("selection", generalUtils.SHARED_TAG)
                .or()
                .equalTo("selection", generalUtils.BOTH_TAG)
                .endGroup()
                .findAllSorted("publishedDate");
    }

    @Override
    public RealmResults<Article> getMostViewedArticles() {
        return realm.where(Article.class)
                .beginGroup()
                .equalTo("selection", generalUtils.VIEWED_TAG)
                .or()
                .equalTo("selection", generalUtils.BOTH_TAG)
                .endGroup()
                .findAllSorted("publishedDate");
    }


//    @Override
//    public void insertViewedArticles(final List<Article> pArticles) {
//        try {
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm bgRealm) {
//                    for (Article article : pArticles) {
//                        if(article.getSelection() == null){
//                            article.setSelection(generalUtils.VIEWED_TAG);
//                        }
//                        else if (hasArticle(article.getId(), generalUtils.SHARED_TAG)) {
//                            article.setSelection(generalUtils.BOTH_TAG);
//                        } else if (hasArticle(article.getId(), generalUtils.BOTH_TAG)) {
//                            break;
//                        }
//
//                        bgRealm.copyToRealmOrUpdate(article);
//                        Timber.d("Articles Insert - OK");
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @Override
//    public void insertSharedArticles(final List<Article> pArticles) {
//        try {
//            realm.executeTransaction(new Realm.Transaction() {
//                @Override
//                public void execute(Realm bgRealm) {
//                    for (Article article : pArticles) {
//                        if(article.getSelection() == null){
//                            article.setSelection(generalUtils.SHARED_TAG);
//                        }
//                        else if (hasArticle(article.getId(), generalUtils.VIEWED_TAG)) {
//                            article.setSelection(generalUtils.BOTH_TAG);
//                        } else if (hasArticle(article.getId(), generalUtils.BOTH_TAG)) {
//                            break;
//                        }
//                        bgRealm.copyToRealmOrUpdate(article);
//                    }
//                }
//            });
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

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

    //-------------------Categories--------------------------

    @Override
    public void clearCategories() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(Category.class).findAll().deleteAllFromRealm();
                Timber.d("Categories Cleared");
            }
        });

    }

    @Override
    public RealmResults<Category> getCategories() {
        RealmResults<Category> categories = realm.where(Category.class).findAll();
        return categories;

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
    public boolean hasCategories() {
        return !realm.where(Category.class).findAll().isEmpty();
    }


    @Override
    public void startCategory() {
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

    //-------------------Saved Articles--------------------------




    @Override
    public void clearSavedArticles() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.where(SavedArticle.class).findAll().deleteAllFromRealm();
                bgRealm.where(SavedMedia.class).findAll().deleteAllFromRealm();
                Timber.d("Saved Cleared");
            }
        });

    }


    @Override
    public RealmResults<SavedArticle> getSavedArticles() {
        return realm.where(SavedArticle.class).findAllSorted("publishedDate");
    }


    @Override
    public int countSavedArticles() {
        return realm.where(SavedArticle.class).findAll().size();
    }



    @Override
    public boolean hasSavedArticles() {
        return !realm.where(SavedArticle.class).findAll().isEmpty();
    }

    @Override
    public SavedArticle getSavedArticle(String id) {
        return realm.where(SavedArticle.class).equalTo("id",id).findFirst();
    }


    @Override
    public boolean isSaved(String id) {
        if(realm.where(SavedArticle.class).equalTo("id",id).count() > 0){
            return true;
        }
        else {
            return false;
        }
    }

    @Override
    public void insertSavedArticle(final SavedArticle pArticle) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                bgRealm.copyToRealmOrUpdate(pArticle);

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("Saved Article Insert - OK");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.d("SavedArticles Insert fail:" + error.getMessage());
            }
        });
    }


    @Override
    public void deletedSavedArticle(final String id) {
        realm.executeTransactionAsync(new Realm.Transaction() {
            @Override
            public void execute(Realm bgRealm) {
                RealmResults<SavedArticle> saved = realm.where(SavedArticle.class).equalTo("id",id).findAll();
                saved.clear();

            }
        }, new Realm.Transaction.OnSuccess() {
            @Override
            public void onSuccess() {
                Timber.d("Saved Article Insert - OK");
            }
        }, new Realm.Transaction.OnError() {
            @Override
            public void onError(Throwable error) {
                Timber.d("SavedArticles Insert fail:" + error.getMessage());
            }
        });
    }


}

