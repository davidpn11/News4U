package com.android.pena.david.news4u.ui.home.dialog;

/**
 * Created by david on 14/07/17.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.pena.david.news4u.News4UApp;
import com.android.pena.david.news4u.R;
import com.android.pena.david.news4u.model.CategoryData;
import com.android.pena.david.news4u.utils.GeneralUtils;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

import timber.log.Timber;

public class CategoryGridAdapter extends RecyclerView.Adapter<CategoryGridAdapter.ViewHolder> {

    private ArrayList<CategoryData> categories;
    private Context mContext;
    private Bitmap check_img;
    private DatabaseReference ref;


    public CategoryGridAdapter(Context context, ArrayList<CategoryData> categories){
        this.categories = categories;
        this.mContext = context;
        check_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_check);
        ref = News4UApp.getCategoryEndpoint();
    }


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.category_cell,null);
        return new CategoryGridAdapter.ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        holder.bindCategory(categories.get(position));
    }

    @Override
    public int getItemCount() {
        return categories.size();
    }



    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        @BindView(R.id.category_name) TextView name;
        @BindView(R.id.category_icon) ImageView icon;
        @BindView(R.id.category_cell) ConstraintLayout layout;
        private CategoryData category;
        private Bitmap category_img;

        private ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            layout.setOnClickListener(this);

        }
        private void bindCategory(CategoryData category) {
            this.category = category;
            String cat_name = category.getCategory();
            name.setText(cat_name.toUpperCase());
            switch (cat_name){
                case "Arts":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_art);
                    layout.setBackgroundResource(R.drawable.grid_item1);
                    break;
                case "Books":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_book);
                    layout.setBackgroundResource(R.drawable.grid_item2);
                    break;
                case "Health":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_health);
                    layout.setBackgroundResource(R.drawable.grid_item3);
                    break;
                case "Movies":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_movie);
                    layout.setBackgroundResource(R.drawable.grid_item4);
                    break;
                case "Science":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_science);
                    layout.setBackgroundResource(R.drawable.grid_item5);
                    break;
                case "Sports":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_sport);
                    layout.setBackgroundResource(R.drawable.grid_item6);
                    break;
                case "Style":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_style);
                    layout.setBackgroundResource(R.drawable.grid_item7);
                    break;
                case "Technology":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_tech);
                    layout.setBackgroundResource(R.drawable.grid_item8);
                    break;
                case "World":
                    category_img = BitmapFactory.decodeResource(mContext.getResources(),R.mipmap.ic_world);
                    layout.setBackgroundResource(R.drawable.grid_item9);
                    break;
            }
            if(category.isActive()){
                icon.setImageBitmap(check_img);
            }else{
                icon.setImageBitmap(category_img);
            }
        }

        @Override
        public void onClick(View v) {
            if(category.isActive()){
                setActiveState(false);
            }else{
                setActiveState(true);
            }
        }

        private void setActiveState(final boolean active){
            try {
               //Timber.d(ref.child(category.getCategory()).toString());
                category.setActive(active);
                Map<String, Object> catUpdates = new HashMap<String, Object>();
                catUpdates.put("active",active);
                ref.child(category.getCategory()).updateChildren(catUpdates);
                if(active){
                    GeneralUtils.ImageViewAnimatedChange(mContext,icon,check_img);
                }else{
                    GeneralUtils.ImageViewAnimatedChange(mContext,icon,category_img);
                }
            } catch (Exception e) {
                e.printStackTrace();
                Timber.e(e.getMessage());
            }

        }
    }
}
