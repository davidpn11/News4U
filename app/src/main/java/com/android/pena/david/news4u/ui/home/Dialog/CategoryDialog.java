package com.android.pena.david.news4u.ui.home.Dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Category;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david on 11/07/17.
 */

public class CategoryDialog extends DialogFragment {


    @BindView(R.id.dialog_recyclerview) RecyclerView dialogRecyclerView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_grid,container);
        ButterKnife.bind(this,view);

        dialogRecyclerView.setLayoutManager(new GridLayoutManager(this.getActivity(),3));
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));
        categories.add(new Category("Altomobiles"));

        CategoryGridAdapter categoryGridAdapter = new CategoryGridAdapter(getContext(),categories);
        dialogRecyclerView.setAdapter(categoryGridAdapter);
        this.getDialog().setTitle("Choose a category");


        return view;
    }
}
