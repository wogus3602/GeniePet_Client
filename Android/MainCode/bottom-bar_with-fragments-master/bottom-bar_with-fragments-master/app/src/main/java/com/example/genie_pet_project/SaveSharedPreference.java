package com.example.genie_pet_project;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ViewGroup;

import com.example.genie_pet_project.Activity.MainActivity;

public class SaveSharedPreference {
    static final String PREF_TOKEN = "username";
    static final String PREF_LOGGED = "logged";
    static final String PREF_SPECIE = "specie";
    static final String PREF_DogName = "dogname";
    static final String PREF_DogAge = "dogage";
    static final String PREF_DogImagePath = "dogimagepath";



    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 견종 이름 가져오기
    public static String getDogName(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_DogName, "");
    }

    // 강아지 견종 이름 저장
    public static void setDogName(Context ctx, String dogName) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DogName, dogName);
        editor.commit();
    }

    // 견종 나이 가져오기
    public static String getDogAge(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_DogAge, "");
    }

    // 견종 나이 저장
    public static void setDogAge(Context ctx, String dogAge) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DogAge, dogAge);
        editor.commit();
    }

    // 견종 이미지 경로 가져오기
    public static String getDogImagePath(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_DogImagePath, "");
    }

    // 견종 이미지 경로 저장
    public static void setDogImagePath(Context ctx, String dogImagePath) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_DogImagePath, dogImagePath);
        editor.commit();
    }



    // 계정 정보 저장
    public static void setUserName(Context ctx, String token,boolean Logged) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_TOKEN, token);
        editor.putBoolean(PREF_LOGGED, Logged);
        editor.commit();
    }

    // 계정 정보 저장
    public static void setSpecie(Context ctx, String specie) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_SPECIE, specie);
        editor.commit();
    }

    // 저장된 정보 가져오기
    public static String getToken(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_TOKEN, "");
    }

    public static Boolean getLogged(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(PREF_LOGGED,false);
    }

    // 저장된 정보 가져오기
    public static String getSpecie(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_SPECIE, "");
    }

    // 로그아웃
    public static void clearPreference(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.commit();
    }
}