package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.KycInforesponse;
import com.indiaudyogmart.model.MyReviewResponse;

import java.util.List;

public class KycInfoAdapter extends RecyclerView.Adapter<KycInfoAdapter.MyViewHolder> {
    private Context mContext;
    private List<KycInforesponse.DataEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;

    public KycInfoAdapter(Context mContext, List<KycInforesponse.DataEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
        this.def_lang=lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.kyc_status_item,parent,false);
        return new MyViewHolder(view);
    }
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final KycInforesponse.DataEntity dataEntitytemp = mData.get(position);
            holder.fieldname.setText(dataEntitytemp.fieldname+"");
            holder.status.setText(dataEntitytemp.status+"");
            holder.oldvalue.setText(dataEntitytemp.oldvalue+"");
            holder.newvalue.setText(dataEntitytemp.newvalue+"");
            holder.date.setText(dataEntitytemp.createdAt+"");
            holder.admincomment.setText(dataEntitytemp.admincomment+"");
            holder.request.setText(mContext.getString(R.string.req_id)+"_"+dataEntitytemp.groupid);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView fieldname,request,status,oldvalue,newvalue,date,admincomment;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            fieldname = (TextView) itemView.findViewById(R.id.tv_field_name);
            request = (TextView) itemView.findViewById(R.id.tv_request_id);
            status = (TextView) itemView.findViewById(R.id.tv_status);
            oldvalue = (TextView) itemView.findViewById(R.id.tv_old_value);
            newvalue = (TextView) itemView.findViewById(R.id.tv_new_value);
            date = (TextView) itemView.findViewById(R.id.tv_date);
            admincomment = (TextView) itemView.findViewById(R.id.tv_admin_coment);

            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_main);

        }

    }
    public interface AdapterCallback {
        void onSubcategoryClick(KycInforesponse.DataEntity items);

        void onSubcategoryViewAllClick(KycInforesponse.DataEntity items);
    }
}
