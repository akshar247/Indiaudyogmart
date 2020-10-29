package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.ProductDetailResponse.DataEntity;

import java.util.ArrayList;
import java.util.List;

public class ProductDetailAdapter extends RecyclerView.Adapter<ProductDetailAdapter.MyViewHolder> {
    private Context mContext;
   AdapterCallback adapterCallback;
    String def_lang;
    private List<DataEntity> mData=new ArrayList<>();

    public ProductDetailAdapter(Context mContext, List<DataEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.mData=mData;
        this.adapterCallback = adapterCallback1;
        this.def_lang = lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.product_details_item,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {



        try {
            final DataEntity dataEntitytemp = mData.get(position);


            holder.title.setText(dataEntitytemp.name+"");
            holder.companyName.setText(dataEntitytemp.sellerCompanyName+"");
            holder.location.setText(dataEntitytemp.sellerCompanyLocation+"");
            holder.location.setText(dataEntitytemp.sellerCompanyLocation+"");
            holder.phone.setText(dataEntitytemp.sellerContactno+"");



            int per=((dataEntitytemp.originalPrice-dataEntitytemp.price)*100)/dataEntitytemp.originalPrice;

            holder.price.setText(mContext.getString(R.string.rs_val)+dataEntitytemp.price+ "  " +per+" "+mContext.getString(R.string.discount_val));
            holder.price1.setText(mContext.getString(R.string.rs_val)+dataEntitytemp.originalPrice);


            String url = IndiaMartConfig.product_path + dataEntitytemp.productImages.get(0).image;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.image);

            holder.getPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(adapterCallback!=null)
                        adapterCallback.submit(dataEntitytemp);

                }
            });
            holder.callNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(adapterCallback!=null)
                        adapterCallback.call(dataEntitytemp);
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adapterCallback!=null)
                        adapterCallback.Detail(dataEntitytemp);
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
        TextView price,price1,companyName,location,availableLocation,phone;
        CardView cardView;
        Button getPrice,callNow;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.product_name);
            companyName=(TextView) itemView.findViewById(R.id.tv_compney_name);
            location=(TextView) itemView.findViewById(R.id.tv_loaction);
            availableLocation=(TextView) itemView.findViewById(R.id.tv_available_location);
            price=(TextView) itemView.findViewById(R.id.tv_price);
            price1=(TextView) itemView.findViewById(R.id.tv_price1);
            phone=(TextView) itemView.findViewById(R.id.tv_contact);
            image = (ImageView) itemView.findViewById(R.id.image);
            getPrice=(Button) itemView.findViewById(R.id.btn_get_price);
            callNow=(Button) itemView.findViewById(R.id.btn_callnow);
            cardView = (CardView) itemView.findViewById(R.id.product_cardview);


        }
    }
    public interface AdapterCallback {
        void onSubcategoryClick(DataEntity items);
        void call(DataEntity items);
        void submit(DataEntity items);
        void Detail(DataEntity items);
        void onSubcategoryViewAllClick(DataEntity items);
    }
}
