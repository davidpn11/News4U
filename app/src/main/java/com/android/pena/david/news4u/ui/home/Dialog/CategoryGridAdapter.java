package com.android.pena.david.news4u.ui.home.Dialog;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.Category;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by david on 11/07/17.
 */

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    public List<Category> stepsArray;
    private Context mContext;



    public CategoryGridAdapter(Context context, List<Category> stepsArray){
        this.stepsArray = stepsArray;
        this.mContext = context;

    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_cell,null);
        return new CategoryGridAdapter.ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bindStepData(stepsArray.get(position));
    }

    @Override
    public int getItemCount() {
        return stepsArray.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.category_name)
        TextView name;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        private void bindStepData(Category step) {
            name.setText(step.getCategory());
        }

    }
}
