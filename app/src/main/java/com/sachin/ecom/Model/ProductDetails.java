package com.sachin.ecom.Model;

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

public class ProductDetails implements Parcelable{

    public int id;
    public String name;


    public ProductDetails(JSONObject jsonObject){
        id = jsonObject.optInt("id");
        name = jsonObject.optString("name");
    }

    public ProductDetails(Parcel in){

        this.id = in.readInt();
        this.name = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
    }
    public static final Creator CREATOR = new Creator() {
        public ProductDetails createFromParcel(Parcel in) {
            return new ProductDetails(in);
        }

        public ProductDetails[] newArray(int size) {
            return new ProductDetails[size];
        }
    };


    public static void writeObject(Context context, String key, String object) throws IOException {
        ObjectOutput out = new ObjectOutputStream(new FileOutputStream(new File(context.getCacheDir(),"")+key+".srl"));
        out.writeObject( object );
        out.close();
    }

    public static Object readObject(Context context, String key) throws IOException,
            ClassNotFoundException {
        ObjectInputStream in = new ObjectInputStream(new FileInputStream(new File(new File(context.getCacheDir(),"")+key+".srl")));
        String jsonObject = (String) in.readObject();
        in.close();
        return jsonObject;
    }
}
