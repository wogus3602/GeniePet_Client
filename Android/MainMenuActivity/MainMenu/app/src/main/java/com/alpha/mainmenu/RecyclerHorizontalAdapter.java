package com.alpha.mainmenu;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerHorizontalAdapter extends RecyclerView.Adapter<RecyclerHorizontalAdapter.HorizontalViewHolder>  {


    public static class HorizontalViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPicture;
        TextView tvPrice;
        TextView tvName;

        HorizontalViewHolder(View view){
            super(view);
            ivPicture = view.findViewById(R.id.iv_picture);
            tvPrice = view.findViewById(R.id.tv_price);
            tvName = view.findViewById(R.id.tv_name);
        }
    }

    private ArrayList<MainItemInfo> feedInfoArrayList;
    RecyclerHorizontalAdapter(ArrayList<MainItemInfo> foodInfoArrayList){
        this.feedInfoArrayList = foodInfoArrayList;
    }

    @Override
    public HorizontalViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_horizontal, parent, false);

        return new RecyclerHorizontalAdapter.HorizontalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(HorizontalViewHolder horizontalViewHolder, int position) {

        horizontalViewHolder.ivPicture.setImageResource(feedInfoArrayList.get(position).drawableId);
        horizontalViewHolder.tvPrice.setText(feedInfoArrayList.get(position).price);
        horizontalViewHolder.tvName.setText(feedInfoArrayList.get(position).name);
    }

    @Override
    public int getItemCount() {
        return feedInfoArrayList.size();
    }
}
