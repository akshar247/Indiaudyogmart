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
import com.indiaudyogmart.model.MyQuoteBuyerResponse;
import com.indiaudyogmart.model.MyQuoteBuyerResponse;

import java.util.List;

public class MyQuoteBuyerAdapter extends RecyclerView.Adapter<MyQuoteBuyerAdapter.MyViewHolder> {
    private Context mContext;
    private List<MyQuoteBuyerResponse.DataEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;


    public MyQuoteBuyerAdapter(Context mContext, List<MyQuoteBuyerResponse.DataEntity> mData, MyQuoteBuyerAdapter.AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
        this.def_lang=lang;
    }

    public MyQuoteBuyerAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.myleads_item,parent,false);
        return new MyQuoteBuyerAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final MyQuoteBuyerResponse.DataEntity dataEntitytemp = mData.get(position);
            holder.title.setText(dataEntitytemp.rfqpName+"");
            holder.buyername.setText(dataEntitytemp.quoteCustomerName+"");
            holder.des.setText(dataEntitytemp.rfqpDescription+"");
            holder.price.setText(dataEntitytemp.rfqpPriceperqty+""+mContext.getString(R.string.inr)+" / "+dataEntitytemp.rfqpQty+""+mContext.getString(R.string.piese));
            String url = IndiaMartConfig.lead_image_path + dataEntitytemp.rfqpProductImage;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.image);
            holder.button.setText(mContext.getString(R.string.view));
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

        TextView title,buyername,des,price;
        ImageView image;
        Button button;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.product_name);
            buyername=(TextView)itemView.findViewById(R.id.tv_seller_name);
            des=(TextView)itemView.findViewById(R.id.des);
            price=(TextView)itemView.findViewById(R.id.tv_price);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            button=(Button)itemView.findViewById(R.id.btn_delivered);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_main);

        }

    }
    public interface AdapterCallback {
        void onSubcategoryClick(MyQuoteBuyerResponse.DataEntity items);

        void onSubcategoryViewAllClick(MyQuoteBuyerResponse.DataEntity items);
    }
}
