package com.sachin.ecom.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class RankingProductsModel{

    public int rankingProductID;
    public int rankingCount;


    public RankingProductsModel(JSONObject jsonObject,int type){

        if(type==1){
            rankingProductID = jsonObject.optInt("id");
            rankingCount = jsonObject.optInt("view_count");
        }else if(type==2){
            rankingProductID = jsonObject.optInt("id");
            rankingCount = jsonObject.optInt("order_count");
        }else if(type==3){
            rankingProductID = jsonObject.optInt("id");
            rankingCount = jsonObject.optInt("shares");
        }
    }
}
