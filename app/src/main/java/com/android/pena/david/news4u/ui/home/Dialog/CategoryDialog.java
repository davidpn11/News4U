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

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Category;
import com.android.pena.david.news4u.utils.db.CategoryDataHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.realm.Realm;
import io.realm.RealmResults;
/**
 * Created by david on 14/07/17.
 */

public class CategoryDialog extends DialogFragment implements View.OnClickListener {

    @BindView(R.id.dialog_recyclerview) RecyclerView dialogRecyclerView;
    @BindView(R.id.btn_submit) Button submitBtn;
    private RealmResults<Category> pCategories;
    private  CategoryGridAdapter categoryGridAdapter;
    Realm realm;
    public CategoryDialog() {

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
        realm = Realm.getDefaultInstance();
        pCategories = CategoryDataHelper.getCategories(realm);
        categoryGridAdapter = new CategoryGridAdapter(getContext(),pCategories);
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
        realm.close();
        categoryGridAdapter.closeRealm();
    }
}
