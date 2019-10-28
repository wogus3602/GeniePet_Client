package com.example.bottombar_navigation_with_fragment.Fragment;


import android.net.Uri;
import android.os.Bundle;

import android.view.LayoutInflater;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottombar_navigation_with_fragment.R;

import java.util.ArrayList;


public class LeftFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private RecyclerView rcv;
    private LinearLayoutManager llm;



    //private OnFragmentInteractionListener mListener;

    public LeftFragment() {
    }

    public static Fragment newInstance() {

        return new LeftFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
        ActionBar actionBar = ((AppCompatActivity)getActivity()).getSupportActionBar();
        actionBar.setDisplayShowCustomEnabled(true); //커스터마이징 하기 위해 필요
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayHomeAsUpEnabled(true); // 뒤로가기 버튼, 디폴트로 true만 해도 백버튼이 생김
        //actionBar.setHomeAsUpIndicator(R.drawable.ic_home_black_24dp); //뒤로가기 버튼을 본인이 만든 아이콘으로 하기 위해 필요
        //getActivity().setTitle("툴바");
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_friend, menu);
    }
    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_left, container, false);


        /*
        list.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //Toast.makeText(activity, "c",Toast.LENGTH_LONG).show();//각 아이템을 누르면 토스트 메세지가 뜨도록

            }
        });
        */

//        if(view instanceof RecyclerView){
//            RecyclerView recyclerView = (RecyclerView) view;
//            recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//            if(mAdapter == null){
//                mQuery.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        for(DocumentSnapshot document: task.getResult()){
//                            HashMap<String, Boolean> users = (HashMap<String, Boolean>) document.get("users");
//                            if(users.size()==1){
//                                mUsers.put(MainActivity.USER_PROFILE.getUid(),  MainActivity.USER_PROFILE);
//                            }
//                            for(String uid: users.keySet()){
//                                if(!uid.equals(MainActivity.USER_PROFILE.getUid())){
//                                    Log.d("!!!!", "uid is :"+uid);
//                                    FirebaseFirestore.getInstance().collection("users")
//                                            .document(uid).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                        @Override
//                                        public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
//                                            User user = documentSnapshot.toObject(User.class);
//                                            mUsers.put(user.getUid(), user);
//                                        }
//                                    });
//                                }
//
//                            }
//                            Log.d("!!!!!", mUsers.size()+", "+ users.size());
//
//                        }
//
//                        mAdapter = new ChatRoomRecyclerViewAdapder(mQuery, mListener, mUsers);
//                        mAdapter.startListening();
//                        recyclerView.setAdapter(mAdapter);
//
//                    }
//                });
//
//            }else if(mUsers.get(mUser.getUid()) != null && mUsers.get(mUser.getUid()).getName() != MainActivity.USER_PROFILE.getName()){
//                Log.d("!!!!", "User Profile is different" + mUsers.get(mUser.getUid()).getName());
//                mUsers.put(mUser.getUid(), MainActivity.USER_PROFILE);
//                mAdapter = new ChatRoomRecyclerViewAdapder(mQuery, mListener, mUsers);
//                mAdapter.startListening();
//                recyclerView.setAdapter(mAdapter);
//
//            }
//            recyclerView.setAdapter(mAdapter);
//
//            final int initialTopPosition = recyclerView.getTop();
//            recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
//                @Override
//                public void onScrolled(@NonNull RecyclerView recyclerView, int dx, int dy) {
//                    super.onScrolled(recyclerView, dx, dy);
//                    if(recyclerView.getChildAt(0).getTop() < initialTopPosition){
//
//                        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(5);
//                    }else{
//                        ((AppCompatActivity)getActivity()).getSupportActionBar().setElevation(0);
//
//
//                    }
//                }
//            });
//        }

        return view;
    }

// 필요한지 모르겠음
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    public interface OnFragmentInteractionListener {
//        void onChatItemSelected(ChatRoom chatRoom, String name);
//    }
}