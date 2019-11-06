package com.example.bottombar_navigation_with_fragment.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bottombar_navigation_with_fragment.R;
import com.example.bottombar_navigation_with_fragment.model.MainItemInfo;

import java.util.ArrayList;

public class RecyclerVerticalAdapter extends RecyclerView.Adapter<RecyclerVerticalAdapter.VerticalViewHolder>  {
    private ArrayList<ArrayList<MainItemInfo>> AllMainItemInfo;
    private Context context;

    public  RecyclerVerticalAdapter(Context context, ArrayList<ArrayList<MainItemInfo>> data)
    {
        this.context = context;
        this.AllMainItemInfo = data;
    }

    public class VerticalViewHolder extends RecyclerView.ViewHolder{
        protected RecyclerView recyclerView;

        public VerticalViewHolder(View view)
        {
            super(view);

            this.recyclerView = view.findViewById(R.id.recyclerViewVertical);
        }
    }
    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_vertical, null);
        return new RecyclerVerticalAdapter.VerticalViewHolder(v);
    }
    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder verticalViewHolder, int position) {
        RecyclerHorizontalAdapter adapter = new RecyclerHorizontalAdapter(AllMainItemInfo.get(position));

        verticalViewHolder.recyclerView.setHasFixedSize(true);
        verticalViewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false));
        verticalViewHolder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
        return AllMainItemInfo.size();
    }
}
