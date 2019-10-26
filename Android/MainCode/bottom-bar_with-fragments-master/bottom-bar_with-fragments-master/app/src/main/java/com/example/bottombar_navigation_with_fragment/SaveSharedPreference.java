package com.example.bottombar_navigation_with_fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SaveSharedPreference {

    static final String PREF_TOKEN = "username";
    static final String PREF_LOGGED = "logged";

    static SharedPreferences getSharedPreferences(Context ctx) {

        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setUserName(Context ctx, String token,boolean Logged) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TOKEN, token);
        editor.putBoolean(PREF_LOGGED, Logged);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_TOKEN, "");
    }

    public static Boolean getLogged(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_LOGGED,false);
    }

    // 로그아웃
    public static void clearPreference(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}