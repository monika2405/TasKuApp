package com.staradmin.android.tasku;

import android.content.Context;
import android.content.SharedPreferences;

public class LocalStorage {
    private static  String SESSION = "session_login";
    private static String USER_ID = "id";
    private static String USERNAME = "username";
    private static String PASSWORD = "password";


    public static void setCustomerId(Context context, String id){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE).edit();
        editor.putString(USER_ID, id);
        editor.apply();
    }
    public static String getCustomerId(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        return prefs.getString(USER_ID,"");
    }

    public static void setusername(Context context, String id){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE).edit();
        editor.putString(USERNAME, id);
        editor.apply();
    }
    public static String getusername(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        return prefs.getString(USERNAME,"");
    }

    public static void setpassword(Context context, String id){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE).edit();
        editor.putString(PASSWORD, id);
        editor.apply();
    }
    public static String getpassword(Context context){
        SharedPreferences prefs = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE);
        return prefs.getString(PASSWORD,"");
    }

    public static void logout(Context context, String id){
        SharedPreferences.Editor editor = context.getSharedPreferences(SESSION, Context.MODE_PRIVATE).edit();
        editor.clear().apply();
    }
}
