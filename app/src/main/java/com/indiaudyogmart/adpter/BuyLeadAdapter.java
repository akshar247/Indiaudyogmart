package com.indiaudyogmart.adpter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.BuyLeadResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyLeadAdapter extends RecyclerView.Adapter<BuyLeadAdapter.MyViewHolder> {
    String TAG="Payment Error";
    private Activity activity;
    private Context mContext;
    private List<BuyLeadResponse.PackagesEntity> mData = new ArrayList<BuyLeadResponse.PackagesEntity>();
    AdapterCallback adapterCallback;
    public BuyLeadAdapter(Context mContext, List<BuyLeadResponse.PackagesEntity> mData, AdapterCallback adapterCallback1 ) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData = (List<BuyLeadResponse.PackagesEntity>) mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.leads_item, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final BuyLeadResponse.PackagesEntity dataEntitytemp = mData.get(position);
            holder.tvStarter.setText(dataEntitytemp.packageName+"");
            holder.tvLeads.setText(dataEntitytemp.leadLimit+" "+mContext.getString(R.string.leads));
            holder.tvDuration.setText(mContext.getString(R.string.duration)+" : "+dataEntitytemp.baseNo+" " +dataEntitytemp.base+"");
            holder.tvInr.setText(dataEntitytemp.price+" "+mContext.getString(R.string.inr));
            holder.tvInr.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
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


    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_starter)
        TextView tvStarter;
        @BindView(R.id.tv_leads)
        TextView tvLeads;
        @BindView(R.id.tv_duration)
        TextView tvDuration;
        @BindView(R.id.tv_inr)
        TextView tvInr;
        @BindView(R.id.tv_main)
        RelativeLayout tvMain;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }

    public interface AdapterCallback {
        void onSubcategoryClick(BuyLeadResponse.PackagesEntity dataEntitytemp);
        void OnSubcategoryViewAllClick(BuyLeadResponse.PackagesEntity dataEntitytemp);
    }
}
