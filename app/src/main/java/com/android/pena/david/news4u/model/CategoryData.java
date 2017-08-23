package com.android.pena.david.news4u.model;

/**
 * Created by david on 03/08/17.
 */

public class CategoryData {

    private String category;
    private boolean active;


    public CategoryData(){
        this.category = null;
        this.active = false;
    }

    public CategoryData(String category) {
        this.category = category;
        this.active = false;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

