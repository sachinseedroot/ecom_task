package com.sachin.ecom.DB;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.Model.ProductDetails;

import java.util.ArrayList;
import java.util.List;

@Dao
public interface mDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addCategories(CategoryDetails categoryDetails);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    public void addProducts(ProductDetails categoryDetails);

    @Query("select * from CategoryTable")
    public List<CategoryDetails> getAllCategories();

    @Query("select * from ProductTable")
    public List<ProductDetails> getAllProducts();

    @Query("select * from CategoryTable where id LIKE :ids limit 1")
    public CategoryDetails getCategoryById(int ids);

    @Query("select * from ProductTable where category_id LIKE :ids")
    public List<ProductDetails> getProductsList(int ids);

    @Query("select * from ProductTable where category_id LIKE :ids and v_most_viewed <> 0 order by v_most_viewed desc")
    public List<ProductDetails> getProductsListViewed(int ids);

    @Query("select * from ProductTable where category_id LIKE :ids and v_most_ordered <> 0 order by v_most_ordered desc")
    public List<ProductDetails> getProductsListOrdered(int ids);

    @Query("select * from ProductTable where category_id LIKE :ids and v_most_shared <> 0 order by v_most_shared desc")
    public List<ProductDetails> getProductsListShared(int ids);

    @Query("select * from ProductTable where product_v_id LIKE :ids")
    public ProductDetails getProductById(int ids);

    @Update
    public void upDateProducts(ProductDetails productDetails);

}
