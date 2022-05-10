package com.example.ocean1;

import android.content.Context;
import android.content.SharedPreferences;

public class sharedPrefence {
    static final String pref_key ="key";
    static final String pref_name ="name";

    public void save(Context context, String text){
        SharedPreferences settings= context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= settings.edit();
        editor.putString(pref_key,text);
        editor.commit();
    }

    public String getValue(Context context){
        SharedPreferences settings= context.getSharedPreferences("dosya",Context.MODE_PRIVATE);
        String name = settings.getString(pref_key,null);
        return name;

    }
    public void clear(Context context){
        SharedPreferences settings= context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= settings.edit();
        editor.clear();
        editor.commit();
    }
    public void remove(Context context){
        SharedPreferences settings= context.getSharedPreferences(pref_name,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= settings.edit();
        editor.remove(pref_key);
        editor.commit();
    }
}
