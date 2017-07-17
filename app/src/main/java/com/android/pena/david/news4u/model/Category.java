package com.android.pena.david.news4u.model;



import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by david on 11/07/17.
 */

public class Category extends RealmObject {

    @PrimaryKey
    private String category;
    private boolean active;


    public Category(){
        this.category = null;
        this.active = false;
    }

    public Category(String category) {
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
