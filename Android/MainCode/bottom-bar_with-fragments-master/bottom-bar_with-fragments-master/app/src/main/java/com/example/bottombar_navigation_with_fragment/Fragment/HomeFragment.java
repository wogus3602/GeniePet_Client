package com.example.bottombar_navigation_with_fragment.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottombar_navigation_with_fragment.Adapter.RecyclerVerticalAdapter;
import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.model.MainItemInfo;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeFragment extends Fragment {

    private ArrayList<ArrayList<MainItemInfo>> all = new ArrayList();

    @BindView(R.id.activity_recycler)
    RecyclerView recyclerView_item;

    public HomeFragment() {
    }

    public static Fragment newInstance() {
        return new HomeFragment();
    }

    public void onStart() {
        super.onStart();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //목록 용 RecyclerView
        this.initializeData();
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        ButterKnife.bind(this,view);

        RecyclerVerticalAdapter verticalAdapter = new RecyclerVerticalAdapter(getContext(), all);
        recyclerView_item.setHasFixedSize(true);
        recyclerView_item.setLayoutManager(new LinearLayoutManager(getContext(), RecyclerView.VERTICAL,false));
//      ---- layoutManager_menu = new LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false);
//      ---- HORIZONTAL로 수직, 수평을 구분하고 / reverseLayout으로 아이템 순서를 구분할 수 있음
        recyclerView_item.setAdapter(verticalAdapter);


        return view;
    }


    public void initializeData(){
//      Item 뷰 표시 내용
        ArrayList<MainItemInfo> feedInfoArrayList_1 = new ArrayList<>();
        feedInfoArrayList_1.add(new MainItemInfo(R.drawable.feed1, "39,000원", "퓨리나 원 1세이상 관절건강 강아지사료 3.4kg","모두 보기(63개)->"));
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
}
