package com.sachin.ecom.Model;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

@Entity(tableName = "ProductTable")
public class ProductDetails{

    @ColumnInfo(name = "product_id")
    public int p_id;

    @ColumnInfo(name = "product_name")
    public String p_name;

    @PrimaryKey
    @ColumnInfo(name = "product_v_id")
    public int v_id;

    @ColumnInfo(name = "v_color")
    public String v_color;

    @ColumnInfo(name = "v_size")
    public int v_size;

    @ColumnInfo(name = "v_price")
    public int v_price;

    @ColumnInfo(name = "v_most_viewed")
    public int v_most_viewed;


    @ColumnInfo(name = "v_most_ordered")
    public int v_most_ordered;


    @ColumnInfo(name = "v_most_shared")
    public int v_most_shared;

    @ColumnInfo(name = "category_name")
    public String category_name;

    @ColumnInfo(name = "category_id")
    public int category_id;

    public int getCategory_id() {
        return category_id;
    }

    public void setCategory_id(int category_id) {
        this.category_id = category_id;
    }

    public int getP_id() {
        return p_id;
    }

    public void setP_id(int p_id) {
        this.p_id = p_id;
    }

    public String getP_name() {
        return p_name;
    }

    public void setP_name(String p_name) {
        this.p_name = p_name;
    }

    public int getV_id() {
        return v_id;
    }

    public void setV_id(int v_id) {
        this.v_id = v_id;
    }

    public String getV_color() {
        return v_color;
    }

    public void setV_color(String v_color) {
        this.v_color = v_color;
    }

    public int getV_size() {
        return v_size;
    }

    public void setV_size(int v_size) {
        this.v_size = v_size;
    }

    public int getV_price() {
        return v_price;
    }

    public void setV_price(int v_price) {
        this.v_price = v_price;
    }

    public int getV_most_viewed() {
        return v_most_viewed;
    }

    public void setV_most_viewed(int v_most_viewed) {
        this.v_most_viewed = v_most_viewed;
    }

    public int getV_most_ordered() {
        return v_most_ordered;
    }

    public void setV_most_ordered(int v_most_ordered) {
        this.v_most_ordered = v_most_ordered;
    }

    public int getV_most_shared() {
        return v_most_shared;
    }

    public void setV_most_shared(int v_most_shared) {
        this.v_most_shared = v_most_shared;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }
}
