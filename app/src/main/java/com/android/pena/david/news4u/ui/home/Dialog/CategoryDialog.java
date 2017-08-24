package com.android.pena.david.news4u.ui.home.Dialog;

import android.support.v4.app.DialogFragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.CategoryData;
import com.android.pena.david.news4u.utils.NYTController;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

import timber.log.Timber;

/**
 * Created by david on 14/07/17.
 */

public class CategoryDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.dialog_recyclerview) RecyclerView dialogRecyclerView;
    @BindView(R.id.btn_submit) Button submitBtn;
    private ArrayList<CategoryData> categoryList;
    private  CategoryGridAdapter categoryGridAdapter;
    private NYTController nytController;
    private DatabaseReference categoryDb;
    public CategoryDialog() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        categoryList = new ArrayList<>();
        startCategories();
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getDialog().getWindow().getAttributes().windowAnimations = R.style.DialogAnimator;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_grid,container);
        ButterKnife.bind(this,view);
        dialogRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
   //     nytController = new NYTController(getContext(),getActivity().getApplication());
//        pCategories = nytController.getCategories();
        categoryGridAdapter = new CategoryGridAdapter(getContext(),categoryList);
        dialogRecyclerView.setAdapter(categoryGridAdapter);
        submitBtn.setOnClickListener(this);


            return view;
    }


    @Override
    public void onClick(View v) {
        dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    private void startCategories(){
        categoryDb = News4UApp.getCategoryEndpoint();

        categoryDb.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                            //Timber.d(postSnapshot.getKey());
                            CategoryData cat = postSnapshot.getValue(CategoryData.class);
                            categoryList.add(cat);
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Timber.e(databaseError.getMessage());
                    }
                });
    }
}
