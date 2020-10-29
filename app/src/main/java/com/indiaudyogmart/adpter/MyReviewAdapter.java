package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.indiaudyogmart.model.MyReviewResponse;

import java.util.List;

public class MyReviewAdapter extends RecyclerView.Adapter<MyReviewAdapter.MyViewHolder>{
    private Context mContext;
    private List<MyReviewResponse.DataEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;

    public MyReviewAdapter(Context mContext, List<MyReviewResponse.DataEntity> mData, MyReviewAdapter.AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData=mData;
        this.def_lang=lang;
    }

    public MyReviewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.myreviews_item,parent,false);
        return new MyReviewAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final MyReviewResponse.DataEntity dataEntitytemp = mData.get(position);
            holder.title.setText(dataEntitytemp.product.name+"");
            holder.orderid.setText("");
            holder.buyername.setText("");
            holder.qty.setText(dataEntitytemp.product.qty+"");
            holder.rating.setRating(Float.parseFloat(dataEntitytemp.rating+""));
            holder.price.setText(dataEntitytemp.product.price+"");
            String url = IndiaMartConfig.review_image_path + dataEntitytemp.product.productImages.get(0).image;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.image);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView title,orderid,buyername,qty,price;
        ImageView image;
        RatingBar rating;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.product_name);
            orderid=(TextView)itemView.findViewById(R.id.tv_order_id);
            buyername=(TextView)itemView.findViewById(R.id.tv_buyer_name);
            qty=(TextView)itemView.findViewById(R.id.tv_qty);
            price=(TextView)itemView.findViewById(R.id.tv_price);
            rating=(RatingBar) itemView.findViewById(R.id.ratingBar);
            image = (ImageView) itemView.findViewById(R.id.product_img);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.cv_supplier);

        }

    }
    public interface AdapterCallback {
        void onSubcategoryClick(MyReviewResponse.DataEntity items);

        void onSubcategoryViewAllClick(MyReviewResponse.DataEntity items);
    }
}
