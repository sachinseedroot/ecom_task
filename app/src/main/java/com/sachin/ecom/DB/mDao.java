package com.sachin.ecom.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.Model.ProductDetails;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface mDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addCategories(CategoryDetails categoryDetails);

    @Insert
    public void addProducts(ProductDetails categoryDetails);

    @Query("select * from CategoryTable")
    public List<CategoryDetails> getAllCategories();

    @Query("select * from ProductTable")
    public List<ProductDetails> getAllProducts();

    @Query("select * from CategoryTable where id LIKE :ids limit 1")
    public CategoryDetails getCategoryById(int ids);

}
