package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.AllCityResp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCityAdapter extends RecyclerView.Adapter<AllCityAdapter.MyViewHolder> {
    private Context mContext;
    private List<AllCityResp.DataEntity> mData = new ArrayList<AllCityResp.DataEntity>();
    AdapterCallback adapterCallback;


    public AllCityAdapter(Context mContext, List<AllCityResp.DataEntity> mData, AdapterCallback adapterCallback1) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData = mData;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.item_city, parent, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {

            final AllCityResp.DataEntity dataEntitytemp = mData.get(position);

            holder.tvCity.setText(dataEntitytemp.name + "");
            holder.llRow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(adapterCallback!=null)
                            adapterCallback.onSubcategoryClick(dataEntitytemp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void updateList(List<AllCityResp.DataEntity> temp) {
        try {
            mData=temp;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_city)
        TextView tvCity;
        @BindView(R.id.ll_row)
        LinearLayout llRow;

        public MyViewHolder(@NonNull View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public interface AdapterCallback {
        void onSubcategoryClick(AllCityResp.DataEntity dataEntitytemp);
    }

    static
    class ViewHolder {


        ViewHolder(View view) {

        }
    }
}
