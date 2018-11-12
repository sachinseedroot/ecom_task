package com.sachin.ecom.Model;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class RankingProductsModel{

    public String rankingName;
    public int rankingProductID;
    public int rankingProductViewCount;


    public RankingProductsModel(JSONObject jsonObject){
        rankingName = jsonObject.optString("ranking");
//        name = jsonObject.optString("name");
    }

    public RankingProductsModel(Parcel in){

//        this.id = in.readInt();
//        this.name = in.readString();
    }

}
