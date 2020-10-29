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
import com.indiaudyogmart.model.ImportantSupplierResponse;
import com.indiaudyogmart.model.ImportantSupplierResponse.DataEntity;

import java.util.List;

public class SupplierAdapter extends RecyclerView.Adapter<SupplierAdapter.MyViewHolder>{

    private Context mContext;
    private List<DataEntity> mData;
    AdapterCallback adapterCallback;

    public SupplierAdapter(Context mContext, List<DataEntity> mData, AdapterCallback adapterCallback1) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
    }

    public SupplierAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.suppliers_item,parent,false);
        return new SupplierAdapter.MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull SupplierAdapter.MyViewHolder holder, final int position) {



        try {
            final ImportantSupplierResponse.DataEntity dataEntitytemp = mData.get(position);

            holder.title.setText(dataEntitytemp.category.name+"");
            holder.relativelayout.setOnClickListener(new View.OnClickListener() {
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
        TextView title;
        ImageView image;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.tv_title);
            image = (ImageView) itemView.findViewById(R.id.iv_button);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_1);
        }
    }
    public interface AdapterCallback {
        void onSubcategoryClick(ImportantSupplierResponse.DataEntity items);

        void onSubcategoryViewAllClick(ImportantSupplierResponse.DataEntity items);
    }
}