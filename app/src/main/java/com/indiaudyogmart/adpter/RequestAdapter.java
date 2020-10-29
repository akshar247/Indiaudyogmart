package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.RequestResponse;

import java.util.List;

public class RequestAdapter extends RecyclerView.Adapter<RequestAdapter.MyViewHolder> {
    private Context mContext;
    private List<RequestResponse.DataEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;


    public RequestAdapter(Context mContext, List<RequestResponse.DataEntity> mData, RequestAdapter.AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
        this.def_lang=lang;
    }

    public RequestAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.my_request_item,parent,false);
        return new RequestAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final RequestResponse.DataEntity dataEntitytemp = mData.get(position);
            holder.title.setText(dataEntitytemp.productName+"");
            holder.des.setText(dataEntitytemp.details+"");
            holder.price.setText(dataEntitytemp.productPrice+" "+mContext.getString(R.string.inr)+" / "+dataEntitytemp.qtyUnit+" "+mContext.getString(R.string.piese));

            holder.button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(adapterCallback!=null)
                        adapterCallback.onSubcategoryClick(dataEntitytemp);
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


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,des,price;
        Button button;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.product_name);
            des=(TextView)itemView.findViewById(R.id.message);
            price=(TextView)itemView.findViewById(R.id.tv_price);
            button=(Button)itemView.findViewById(R.id.btn_delivered);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_main);

        }

    }
    public interface AdapterCallback {
        void onSubcategoryClick(RequestResponse.DataEntity items);

        void onSubcategoryViewAllClick(RequestResponse.DataEntity items);
    }
}
