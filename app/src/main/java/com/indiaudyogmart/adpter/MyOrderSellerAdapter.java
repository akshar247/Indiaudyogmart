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
import com.indiaudyogmart.model.MyOrderSellerResponse;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyOrderSellerAdapter extends RecyclerView.Adapter<MyOrderSellerAdapter.MyViewHolder> {

    private Context mContext;
    private List<MyOrderSellerResponse.MartOrdersEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;

    public MyOrderSellerAdapter(Context mContext, List<MyOrderSellerResponse.MartOrdersEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData = mData;
        this.def_lang = lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.myorder_item, parent, false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {


        try {
            final MyOrderSellerResponse.MartOrdersEntity dataEntitytemp = mData.get(position);

            holder.productName.setText(dataEntitytemp.rfqpName + "");
            holder.orderid.setText(dataEntitytemp.orderId + "");
            holder.buyerName.setText(dataEntitytemp.custName + "");
            holder.tvQty.setText(dataEntitytemp.qty + "");
            holder.tvPrice.setText(dataEntitytemp.price + "");
            String url = IndiaMartConfig.order_image_path + dataEntitytemp.rfqpProductImage;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.productImg);
            holder.btnDelivered.setVisibility(View.GONE);
            holder.btnAccept.setVisibility(View.VISIBLE);
            holder.btnReject.setVisibility(View.VISIBLE);
            holder.btnDelivered.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (adapterCallback != null) {

                        adapterCallback.onSubcategoryClick(dataEntitytemp);

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
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.orderid)
        TextView orderid;
        @BindView(R.id.tv_order_id)
        TextView tvOrderId;
        @BindView(R.id.product_name)
        TextView productName;
        @BindView(R.id.buyer_name)
        TextView buyerName;
        @BindView(R.id.tv_buyer_name)
        TextView tvBuyerName;
        @BindView(R.id.tv_qty)
        TextView tvQty;
        @BindView(R.id.tv_price)
        TextView tvPrice;
        @BindView(R.id.btn_delivered)
        Button btnDelivered;
        @BindView(R.id.btn_accept)
        Button btnAccept;
        @BindView(R.id.btn_reject)
        Button btnReject;
        @BindView(R.id.rl_1)
        RelativeLayout rl1;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);

        }
    }

    public interface AdapterCallback {
        void onSubcategoryClick(MyOrderSellerResponse.MartOrdersEntity items);

        void onSubcategoryViewAllClick(MyOrderSellerResponse.MartOrdersEntity items);
    }

    static
    class ViewHolder {


        ViewHolder(View view) {

        }
    }
}