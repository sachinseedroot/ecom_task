package com.sachin.ecom.DB;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.sachin.ecom.Activities.MainActivity;
import com.sachin.ecom.Controller.MainApplication;
import com.sachin.ecom.Model.CategoryDetails;
import com.sachin.ecom.Model.ProductDetails;

@Database(entities = {CategoryDetails.class,ProductDetails.class},version = 1,exportSchema = false)
public abstract class AppDataBase extends RoomDatabase {

    public abstract mDao myDAO();
}
