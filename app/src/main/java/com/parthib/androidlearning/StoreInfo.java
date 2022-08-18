package com.parthib.androidlearning;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class StoreInfo {

    SharedPreferences sPref;
    SharedPreferences.Editor prefEditor;

    public StoreInfo(Context context){
        sPref = PreferenceManager.getDefaultSharedPreferences(context);
        prefEditor = sPref.edit();
    }

    public void storeId(String userId){
        prefEditor.putString("u-id", userId); // key-value pair
        prefEditor.commit(); // save the details in the zone
    }

    public String getId(){
        String uid = sPref.getString("u-id", "");
        return uid;
    }

    public void delete(){
        prefEditor.clear();
        prefEditor.commit();
    }
}
