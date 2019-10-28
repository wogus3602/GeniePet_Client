package com.example.bottombar_navigation_with_fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.MenuItemCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.bottombar_navigation_with_fragment.Fragment.CartFragment;
import com.example.bottombar_navigation_with_fragment.Fragment.HomeFragment;
import com.example.bottombar_navigation_with_fragment.Fragment.ProfileFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    static int flag = 1;
    private CircleMenuLayout mCircleMenuLayout;

    int mCartItemCount = 10;
    TextView textCartItemCount;
    //Circle 속성
    String[] menuArray = new String[] { "사료", "간식", "미용/목욕", "하우스", "장난감", "기타" };
    int[] imgArray = new int[] {R.drawable.dog_food,R.drawable.dog_snack,R.drawable.dog_lotion,R.drawable.dog_house,R.drawable.dog_etc,R.drawable.dog_etc};
    float staticwidth = 0f;
    public static MainActivity mMyApplication;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyApplication = this;
        BottomNavigationView navView = findViewById(R.id.navigation);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragment_container, HomeFragment.newInstance()).commit();

        //툴바
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //Get the ActionBar here to configure the way it beh ves.
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        // actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(false); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        actionBar.setHomeAsUpIndicator(R.drawable.splash_image); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
        //actionBar.setHomeButtonEnabled(true);

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        staticwidth = dm.widthPixels;

        mCircleMenuLayout = findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(menuArray,imgArray);
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

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            SharedPreferences sf = getSharedPreferences("login",MODE_PRIVATE);

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    replaceFragment(HomeFragment.newInstance());
                    return true;
                case R.id.navigation_dashboard:
                    if(flag==1) {
                        mCircleMenuLayout.setVisibility(View.VISIBLE);
                        flag = 0;
                    }
                    else {
                        mCircleMenuLayout.setVisibility(View.INVISIBLE);
                        flag = 1;
                    }
                    return true;
                case R.id.navigation_profile:
                    if (SaveSharedPreference.getLogged(MainActivity.getInstance())) {
                        replaceFragment(ProfileFragment.newInstance());
                    } else {
                        loadFragment(new ProfileLogin());
                    }
                    return true;
            }
            return false;
        }
    };
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu, menu);

        final MenuItem menuItem = menu.findItem(R.id.action_cart);

        View actionView = MenuItemCompat.getActionView(menuItem);
        textCartItemCount = (TextView) actionView.findViewById(R.id.cart_badge);

        setupBadge();

        actionView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onOptionsItemSelected(menuItem);
            }
        });

        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_cart: {
                replaceFragment(CartFragment.newInstance());
//                mCartItemCount -= 1;
//                setupBadge();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    private void replaceFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        //fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
    }

    private void setupBadge() {
        if (textCartItemCount != null) {
            if(mCartItemCount < 0) return;
            if (mCartItemCount == 0) {
                if (textCartItemCount.getVisibility() != View.GONE) {
                    textCartItemCount.setVisibility(View.GONE);
                }
            } else {
                textCartItemCount.setText(String.valueOf(Math.min(mCartItemCount, 99)));
                if (textCartItemCount.getVisibility() != View.VISIBLE) {
                    textCartItemCount.setVisibility(View.VISIBLE);
                }
            }
        }
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
