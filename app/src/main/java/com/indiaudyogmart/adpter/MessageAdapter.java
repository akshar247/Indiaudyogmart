package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.indiaudyogmart.R;
import com.indiaudyogmart.model.ChatListDetailResponse;
import com.indiaudyogmart.model.ChatListResponse;
import com.indiaudyogmart.model.HomeData;

import java.util.ArrayList;
import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyViewHolder> {
    private Context mContext;
    private List<ChatListResponse.DataEntity> mData= new ArrayList<ChatListResponse.DataEntity>();
    AdapterCallback adapterCallback;



    public MessageAdapter(Context mContext, List<ChatListResponse.DataEntity> mData, AdapterCallback adapterCallback1) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData= mData;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.messages_item,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        try {

            final ChatListResponse.DataEntity dataEntitytemp = mData.get(position);

            holder.name.setText(dataEntitytemp.name + "");
            holder.message.setText(dataEntitytemp.email+ "");
            holder.messagecount.setText(dataEntitytemp.unread + "");

            holder.companyname.setVisibility(View.GONE);
            holder.manufacture.setVisibility(View.GONE);
            holder.date.setVisibility(View.GONE);
            holder.img.setVisibility(View.GONE);
            holder.msgcardview.setOnClickListener(new View.OnClickListener() {
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

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        CardView msgcardview;
        TextView companyname;
        TextView name;
        TextView manufacture;
        TextView message;
        TextView date;
        TextView messagecount;
        ImageView img;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            companyname = (TextView) itemView.findViewById(R.id.tv_company_name);
            name = (TextView) itemView.findViewById(R.id.tv_name);
            manufacture = (TextView) itemView.findViewById(R.id.tv_manufac);
            message = (TextView) itemView.findViewById(R.id.tv_msg);
            img=(ImageView)itemView.findViewById(R.id.iv_cart);
            msgcardview=(CardView)itemView.findViewById(R.id.messagecardview);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            messagecount = (TextView) itemView.findViewById(R.id.tv_count_message);
        }
    }
    public interface AdapterCallback {
        void onSubcategoryClick(ChatListResponse.DataEntity dataEntitytemp);
    }
}
