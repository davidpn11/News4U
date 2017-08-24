package com.android.pena.david.news4u.utils.gson;

import android.app.ProgressDialog;


import com.android.pena.david.news4u.model.ArticleData;
import com.android.pena.david.news4u.model.MediaData;
import com.android.pena.david.news4u.utils.generalUtils;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 * Created by david on 06/07/17.
 */



public class ArticleTypeAdapter extends TypeAdapter {


    @Override
    public List<ArticleData> read(JsonReader in) throws IOException {

        final List<ArticleData> article_list = new ArrayList<>();
        in.beginObject();
        while(in.hasNext()){
            switch (in.nextName()){
                case "results":
                    in.beginArray();
                    while(in.hasNext()){
                        final ArticleData article = new ArticleData();
                        in.beginObject();
                        while (in.hasNext()){
                            switch (in.nextName()){

                                case "id":
                                    article.setId(in.nextString());
                               //     article.setSelection(generalUtils.VIEWED_TAG);
                                    break;
                                case "asset_id":
                                    article.setId(in.nextString());
                              //      article.setSelection(generalUtils.SHARED_TAG);
                                    break;
                                case "title":
                                    article.setTitle(in.nextString());
                                    break;
                                case "url":
                                    article.setUrl(in.nextString());
                                    break;
                                case "adx_keywords":
                                    article.setAdxKeywords(in.nextString());
                                    break;
                                case "section":
                                    article.setSection(in.nextString());
                                    break;
                                case "byline":
                                    article.setByline(in.nextString());
                                    break;
                                case "abstract":
                                    article.set_abstract(in.nextString());
                                    break;
                                case "published_date":
                                    article.setPublishedDate(in.nextString());
                                    break;
                                case "media":
                                    if(!in.peek().equals(JsonToken.BEGIN_ARRAY)){
                                        in.skipValue();
                                        break;
                                    }
                                    in.beginArray();
                                    final ArrayList<MediaData> media_list = new ArrayList<>();
                                    while(in.hasNext()){
                                        in.beginObject();
                                        while(in.hasNext()){
                                            switch (in.nextName()){
                                                case "media-metadata":
                                                    in.beginArray();
                                                    while(in.hasNext()){
                                                        in.beginObject();
                                                        final MediaData media = new MediaData();
                                                        while(in.hasNext()){
                                                            switch (in.nextName()){
                                                                case "url":
                                                                    media.setUrl(in.nextString());
                                                                    break;
                                                                case "format":
                                                                    media.setFormat(in.nextString());
                                                                    break;
                                                                default:
                                                                    in.skipValue();
                                                                    break;
                                                            }
                                                        }
                                                        in.endObject();
                                                        media_list.add(media);
                                                    }
                                                    in.endArray();
                                                    break;
                                                default:
                                                    in.skipValue();
                                                    break;
                                            }
                                        }
                                        in.endObject();
                                    }
                                    in.endArray();
                                    for(MediaData m : media_list){
                                        if(m.getFormat().equals("mediumThreeByTwo440")){
                                            article.setMedia(m);
                                            break;
                                        }
                                    }
                                    break;

                                default:
                                    in.skipValue();
                                    break;
                            }/**/
                        }
                        in.endObject();
                        article_list.add(article);
                    }
                    in.endArray();
                    break;
                default:
                    in.skipValue();
                    break;

            }

        }
        in.endObject();
        return article_list;
    }

    @Override
    public void write(JsonWriter out, Object value) throws IOException {

    }
}
