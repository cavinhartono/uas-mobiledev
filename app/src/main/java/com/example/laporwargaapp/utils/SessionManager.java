package com.example.laporwargaapp.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManager {
    private final SharedPreferences preferences;
    private static final String KEY_USERNAME = "username";
    private static final String KEY_REMEMBER = "remember_me";

    public SessionManager(Context context){
        preferences = context.getSharedPreferences("session", Context.MODE_PRIVATE);
    }

    public void saveUser(String username, boolean remember){
        preferences.edit().putString(KEY_USERNAME, username).putBoolean(KEY_REMEMBER, remember).apply();
    }

    public String getUser(){
        return preferences.getString(KEY_USERNAME, null);
    }

    public boolean isRememberMe(){
        return preferences.getBoolean(KEY_REMEMBER, false);
    }

    public void logout(){
        preferences.edit().clear().apply();
    }
}