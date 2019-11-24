//package com.example.genie_pet_project.Fragment;
//
//
//import android.content.Context;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.ListView;
//
//import androidx.annotation.NonNull;
//import androidx.fragment.app.Fragment;
//
//import com.example.genie_pet_project.Adapter.CartListViewAdapter;
//import com.example.genie_pet_project.R;
//
//import butterknife.ButterKnife;
//
//public class CartFragment extends Fragment {
//    public static CartListViewAdapter adapter;
//    public static CartFragment mCartFragment;
//    public static ListView mListView;
//
//    public static CartFragment getInstance() {
//        if(mCartFragment == null){
//            mCartFragment = new CartFragment();
//        }
//        return mCartFragment;
//    }
//    public CartFragment() {
//    }
//
//    public static Fragment newInstance() {
//        return new CartFragment();
//    }
//
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        mCartFragment = this;
//
//    }
//
//
//    public void addItem(String img, String itemname, String price){
//        mListView.setAdapter(adapter);
//
//        String cartImg= img;
//        String cartItemName = itemname;
//        String cartPrice = price;
//
//        adapter.addVO(cartImg,cartItemName,cartPrice);
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.cart_fragment, container, false);
//        mListView = view.findViewById(R.id.Cart_List_view);
//        Button button = view.findViewById(R.id.button4);
//
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addItem("https://ssl.pstatic.net/tveta/libs/1266/1266402/e95e882fbae59e3387df_20191122150559721.jpg","11","11");
//            }
//        });
//
//        return view;
//    }
//
//    @Override
//    public void onStart() {
//        super.onStart();
//        Log.d("onStart","onStart");
//        adapter = new CartListViewAdapter();
//        adapter.set();
//    }
//
//    @Override
//    public void onResume() {
//        super.onResume();
//        Log.d("onResume","onResume");
//
//
//
//    }
//}