package com.alpha.mainmenu;


import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.google.android.material.bottomnavigation.BottomNavigationView;


import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private CircleMenuLayout mCircleMenuLayout;
    //목록 용 RecyclerView
    RecyclerView recyclerView_item;
    private ArrayList<ArrayList<MainItemInfo>> all = new ArrayList();

    //Circle 속성
    String[] menuArray = new String[] { "Item1", "Item2", "Item3", "Item4", "Item5", "Item6" };
    float staticwidth = 0f;
    public static MainActivity mMyApplication = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        BottomNavigationView navView = findViewById(R.id.bottomNavigationView);
        navView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
        loadFragment(new DashboardFragment());

//        //목록 용 RecyclerView
//        this.initializeData();
//        recyclerView_item =  findViewById(R.id.activity_recycler);
//        RecyclerVerticalAdapter verticalAdapter = new RecyclerVerticalAdapter(this, all);
//        recyclerView_item.setHasFixedSize(true);
//        recyclerView_item.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
////      ---- layoutManager_menu = new LinearLayoutManager(this,RecyclerView.HORIZONTAL,false);
////      ---- HORIZONTAL로 수직, 수평을 구분하고 / reverseLayout으로 아이템 순서를 구분할 수 있음
//        recyclerView_item.setAdapter(verticalAdapter);


        //loadFragment(new MainFragment());

        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        staticwidth = dm.widthPixels;
        Toast.makeText(getApplicationContext(), ""+staticwidth ,Toast.LENGTH_SHORT).show();
        mCircleMenuLayout = findViewById(R.id.id_menulayout);
        mCircleMenuLayout.setMenuItemIconsAndTexts(menuArray);
        mCircleMenuLayout.setOnMenuItemClickListener(new CircleMenuLayout.OnMenuItemClickListener() {
            @Override
            public void itemClick(View view, int pos) {
                Toast.makeText(getApplicationContext(), "현재 선택" + pos ,Toast.LENGTH_SHORT).show();
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

        //목록 용 RecyclerView
        this.initializeData();
        recyclerView_item = (RecyclerView) findViewById(R.id.activity_recycler);
        RecyclerVerticalAdapter verticalAdapter = new RecyclerVerticalAdapter(this, all);
        recyclerView_item.setHasFixedSize(true);
        recyclerView_item.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL,false));
//      ---- layoutManager_menu = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//      ---- HORIZONTAL로 수직, 수평을 구분하고 / reverseLayout으로 아이템 순서를 구분할 수 있음
        recyclerView_item.setAdapter(verticalAdapter);

    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;

            switch (item.getItemId()) {
                case R.id.navigation_home:
                    fragment = new DashboardFragment();
                    mCircleMenuLayout.setVisibility(View.INVISIBLE);
                    break;
                case R.id.navigation_dashboard:
                    fragment = null;
                    mCircleMenuLayout.setVisibility(View.VISIBLE);
                    break;
                case R.id.navigation_notifications:
                    fragment = null;
                    mCircleMenuLayout.setVisibility(View.INVISIBLE);
                    break;
            }
            return loadFragment(fragment);
        }
    };


    public void initializeData(){
//      Item 뷰 표시 내용
        ArrayList<MainItemInfo> feedInfoArrayList_1 = new ArrayList<>();
        feedInfoArrayList_1.add(new MainItemInfo(R.drawable.feed1, "39,900원", "퓨리나 원 1세이상 관절건강 강아지사료 3.4kg","모두 보기(63개)->"));
        feedInfoArrayList_1.add(new MainItemInfo(R.drawable.feed2, "41,500원", "[간식포켓+휴대용물그릇 증정] 로얄캐닌 엑스 퍼피 강아지사료 1.5kg","모두 보기(24개)->"));

        ArrayList<MainItemInfo> feedInfoArrayList_2 = new ArrayList<>();
        feedInfoArrayList_2.add(new MainItemInfo(R.drawable.feed3, "43,200원", "[간식포켓+휴대용물그릇 증정] 로얄캐닌 시츄 어덜트 강아지사료 3kg (1.5kg x 2개)","모두 보기(31개)->"));
        feedInfoArrayList_2.add(new MainItemInfo(R.drawable.feed4, "39,800원", "뉴트리탑 소프트 어덜트 강아지 사료 1.2kg","모두 보기(97개)->"));

        ArrayList<MainItemInfo> feedInfoArrayList_3 = new ArrayList<>();
        feedInfoArrayList_3.add(new MainItemInfo(R.drawable.feed5, "43,200원", "[간식포켓+휴대용물그릇 증정] 로얄캐닌 미니 인도어 어덜트 강아지사료 4.5kg (3kg+1.5kg)","모두 보기(69개)->"));
        feedInfoArrayList_3.add(new MainItemInfo(R.drawable.feed6, "39,800원", "[유통19.12.24] 네이처스 버라이어티 인스팅트 오리 강아지사료 1.8kg","모두 보기(112개)->"));


        all.add(feedInfoArrayList_1);
        all.add(feedInfoArrayList_2);
        all.add(feedInfoArrayList_3);
    }


    private boolean loadFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.bottomNavigationView, fragment)
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
