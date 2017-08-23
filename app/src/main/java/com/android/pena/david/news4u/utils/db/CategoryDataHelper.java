package com.android.pena.david.news4u.utils.db;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.model.CategoryData;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import timber.log.Timber;

/**
 * Created by david on 03/08/17.
 */

public class CategoryDataHelper {

    public static ArrayList<CategoryData> getCategories(){

        DatabaseReference ref = News4UApp.getCategoryEndpoint();
        final ArrayList<CategoryData> categoriesList = new ArrayList<>();

        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot noteSnapshot: dataSnapshot.getChildren()){
                    CategoryData cat = noteSnapshot.getValue(CategoryData.class);
                    categoriesList.add(cat);
                }
                String x = categoriesList.toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Timber.d(databaseError.getMessage());
            }
        });
        return null;
    }


    public static void updateCategory(String id){

    }
}
