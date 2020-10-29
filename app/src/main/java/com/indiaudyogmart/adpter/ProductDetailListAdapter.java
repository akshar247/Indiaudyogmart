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

import butterknife.BindView;

public class ProductDetailListAdapter extends RecyclerView.Adapter<ProductDetailListAdapter.MyViewHolder> {
    private Context mContext;
   AdapterCallback adapterCallback;
    String def_lang;
    @BindView(R.id.btn_get_price)
    Button getPrice;
    @BindView(R.id.btn_callnow)
    Button callNow;

    private List<DataEntity> mData=new ArrayList<>();

    public ProductDetailListAdapter(Context mContext, List<DataEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.mData=mData;
        this.adapterCallback = adapterCallback1;
        this.def_lang = lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.product_list_item,parent,false);
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

            holder.callNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(adapterCallback!=null)
                            adapterCallback.oncall(dataEntitytemp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
            holder.getPrice.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if(adapterCallback!=null)
                            adapterCallback.submit(dataEntitytemp);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(adapterCallback!=null)
                        adapterCallback.detail(dataEntitytemp);
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
        TextView price,companyName,location,availableLocation,price1,phone;
        CardView cardView;
        Button callNow,getPrice;
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
            cardView = (CardView) itemView.findViewById(R.id.product_cardview);
            getPrice=(Button) itemView.findViewById(R.id.btn_get_price);
            callNow=(Button) itemView.findViewById(R.id.btn_callnow);




        }
    }
    public interface AdapterCallback {
        void onSubcategoryClick(DataEntity items);
        void oncall(DataEntity items);
        void submit(DataEntity items);
        void detail(DataEntity items);
        void onSubcategoryViewAllClick(DataEntity items);
    }
}
