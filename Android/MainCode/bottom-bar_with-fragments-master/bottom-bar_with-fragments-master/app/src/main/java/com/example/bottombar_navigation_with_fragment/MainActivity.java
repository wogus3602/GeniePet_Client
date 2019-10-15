package com.example.bottombar_navigation_with_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.example.bottombar_navigation_with_fragment.Fragment.CenterFragment;
import com.example.bottombar_navigation_with_fragment.Fragment.LeftFragment;
import com.example.bottombar_navigation_with_fragment.Fragment.RightFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity implements
        BottomNavigationView.OnNavigationItemSelectedListener {
    private int flag = 1;
    private CircleMenuLayout mCircleMenuLayout;
    public Boolean loggedin;
    private SharedPreferences appData;

    //Circle 속성
    String[] menuArray = new String[] { "사료", "간식", "미용/목욕", "하우스", "장난감", "기타" };
    float staticwidth = 0f;
    public static MainActivity mMyApplication = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //loading the default fragment
        loadFragment(new CenterFragment());

        //getting bottom navigation view and attaching the listener
        BottomNavigationView navigation = findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(this);

        appData = getSharedPreferences("myPrefs", MODE_PRIVATE);
        load();


        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        staticwidth = dm.widthPixels;
        //Toast.makeText(getApplicationContext(), ""+staticwidth ,Toast.LENGTH_SHORT).show();
        mCircleMenuLayout = findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(menuArray);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(getApplicationContext(), "현재 선택" + pos ,Toast.LENGTH_SHORT).show();
                if(pos==6){
                    Intent intent = new Intent(getApplicationContext(),StoreListActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void itemCenterClick(View view) {
                Toast.makeText(getApplicationContext(), "센터 클릭" ,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void itemSelChange(int pos) {
                //Toast.makeText(getApplicationContext(), "현재 선택" + pos ,Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        //saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
        loggedin = appData.getBoolean("loggedin",false);
        //id = appData.getString("ID", "");
        //pwd = appData.getString("PWD", "");
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment = null;

        switch (item.getItemId()) {
            case R.id.navigation_home:
                fragment = new LeftFragment();
                mCircleMenuLayout.setVisibility(View.INVISIBLE);
                flag = 1;
                break;

            case R.id.navigation_dashboard:
                fragment = new CenterFragment();
                if(flag==1) {
                    mCircleMenuLayout.setVisibility(View.VISIBLE);
                    flag = 0;
                }else {
                    mCircleMenuLayout.setVisibility(View.INVISIBLE);
                    flag = 1;
                }
                break;

            case R.id.navigation_profile:
                if(SaveSharedPreference.getLogged(this)){
                    fragment = new RightFragment();
                }else{
                    fragment =new ProfileLogin();
                }
                mCircleMenuLayout.setVisibility(View.INVISIBLE);
                flag  = 1;
                break;
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    public static MainActivity getInstance() {
        if(mMyApplication == null){
            mMyApplication = new MainActivity();
        }
        return mMyApplication;
    }

    public String[] getMenuArray() {
        return menuArray;
    }

    public float getStaticwidth() {
        return staticwidth;
    }
}
