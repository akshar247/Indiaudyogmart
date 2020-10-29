package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.MyTransactionResponse;

import java.util.List;

public class MyTransactionAdapter extends RecyclerView.Adapter<MyTransactionAdapter.MyViewHolder>{
    private Context mContext;
    private List<MyTransactionResponse.DataEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;

    public MyTransactionAdapter(Context mContext, List<MyTransactionResponse.DataEntity> mData, MyTransactionAdapter.AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
        this.def_lang=lang;
    }

    public MyTransactionAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.mytransaction_item,parent,false);
        return new MyTransactionAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final MyTransactionResponse.DataEntity dataEntitytemp = mData.get(position);
            holder.id.setText(dataEntitytemp.stId+"");
            holder.orderid.setText(dataEntitytemp.stPackageId+"");
            holder.date.setText(dataEntitytemp.stDate+"");
            holder.price.setText(dataEntitytemp.stAmount+"");



        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView id,orderid,date,price;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            id = (TextView) itemView.findViewById(R.id.sr_no);
            orderid=(TextView)itemView.findViewById(R.id.tv_order_id);
            date=(TextView)itemView.findViewById(R.id.tv_qty);
            price=(TextView)itemView.findViewById(R.id.tv_price);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_main);

        }

    }
    public interface AdapterCallback {
        void onSubcategoryClick(MyTransactionResponse.DataEntity items);

        void onSubcategoryViewAllClick(MyTransactionResponse.DataEntity items);
    }


}
