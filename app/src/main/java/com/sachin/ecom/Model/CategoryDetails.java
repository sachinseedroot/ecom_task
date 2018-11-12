package com.sachin.ecom.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONObject;

@Entity(tableName = "CategoryTable")
public class CategoryDetails {

    @PrimaryKey
    public int id;

    @ColumnInfo(name = "category_name")
    public String name;

    @ColumnInfo(name = "sub_category_ids")
    public String childCategories;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getChildCategories() {
        return childCategories;
    }

    public void setChildCategories(String childCategories) {
        this.childCategories = childCategories;
    }
}