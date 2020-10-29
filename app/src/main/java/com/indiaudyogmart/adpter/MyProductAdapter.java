package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.MyLeadSellerResponse;
import com.indiaudyogmart.model.MyProductResponse;

import java.util.List;

public class MyProductAdapter extends RecyclerView.Adapter<MyProductAdapter.MyViewHolder>{
    private Context mContext;
    private List<MyProductResponse.DataEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;
    public MyProductAdapter(Context mContext, List<MyProductResponse.DataEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
        this.def_lang=lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.product_item,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        try {
            final MyProductResponse.DataEntity dataEntitytemp = mData.get(position);
            holder.title.setText(dataEntitytemp.name+"");
            holder.product_detail.setText(dataEntitytemp.detail+"");
            holder.price.setText(mContext.getString(R.string.price)+" "+dataEntitytemp.price+"");
            holder.qty.setText(mContext.getString(R.string.qty)+" "+dataEntitytemp.qty+"");
            String url = IndiaMartConfig.lead_image_path + dataEntitytemp.productImages.get(0).image;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.image);
            holder.rl_row.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adapterCallback!=null)
                        adapterCallback.onSubcategoryClick(dataEntitytemp);
                }
            });
            holder.tv_edit.setVisibility(View.VISIBLE);
            holder.tv_edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(adapterCallback!=null)
                            adapterCallback.editproduct(dataEntitytemp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,qty,price,tv_edit,product_detail;

        ImageView image;
        RelativeLayout rl_row;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.product_name);
            tv_edit = (TextView) itemView.findViewById(R.id.tv_edit);
            price=(TextView)itemView.findViewById(R.id.tv_price);
            product_detail=(TextView)itemView.findViewById(R.id.product_detail);
            qty=(TextView)itemView.findViewById(R.id.tv_qty);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            rl_row = (RelativeLayout) itemView.findViewById(R.id.rl_row);

        }

    }

    public interface AdapterCallback {
        void onSubcategoryClick(MyProductResponse.DataEntity items);
        void onSubcategoryViewAllClick(MyProductResponse.DataEntity items);
        void editproduct(MyProductResponse.DataEntity items);
    }

}
