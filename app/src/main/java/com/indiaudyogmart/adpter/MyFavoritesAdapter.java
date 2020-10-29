package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.MyFavoritesItem;

import java.util.List;

public class MyFavoritesAdapter extends RecyclerView.Adapter<MyFavoritesAdapter.MyViewHolder>{

    private Context mContext;
    private List<MyFavoritesItem> mData;

    public MyFavoritesAdapter(Context mContext, List<MyFavoritesItem> mData) {
        this.mContext = mContext;
        this.mData=mData;
    }

    public MyFavoritesAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.my_favorites_item,parent,false);
        return new MyFavoritesAdapter.MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyFavoritesAdapter.MyViewHolder holder, final int position) {



        try {
            holder.title.setText(mData.get(position).getTitle());
            holder.image.setImageResource(mData.get(position).getImage());


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        ImageView image;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.productname);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_favorites);
        }
    }
}