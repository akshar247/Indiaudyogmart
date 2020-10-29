package com.indiaudyogmart.adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.HomeData;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeProductDealAdapter extends RecyclerView.Adapter<HomeProductDealAdapter.MyViewHolder> {

    private Activity activity;

    List<HomeData.ProductDealsEntity> dataEntity = new ArrayList<>();

    AdapterCallback adapterCallback;
    String def_lang;
    HomeData homeData;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.tv_subcategori_tname)
        TextView tvSubcategoriTname;
        @BindView(R.id.btn_get_quotes)
        Button btnGetQuotes;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public HomeProductDealAdapter(Activity mContext, List<HomeData.ProductDealsEntity> dataEntity, HomeData homeData, AdapterCallback adapterCallback1, String lang) {
        this.activity = mContext;
        this.dataEntity = dataEntity;

        this.adapterCallback = adapterCallback1;
        this.def_lang = lang;
        this.homeData = homeData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_prod_category, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            final HomeData.ProductDealsEntity dataEntitytemp = dataEntity.get(position);


            holder.tvSubcategoriTname.setText(dataEntitytemp.productName + "");
            String url = IndiaMartConfig.product_path + "/" + dataEntitytemp.image;
            int roundvalu = (int) activity.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(activity)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.productImg);



            holder.productImg.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        adapterCallback.onSubcategoryViewAllClick(dataEntitytemp);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            holder.btnGetQuotes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
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
        return dataEntity.size();
    }

    public interface AdapterCallback {
        void onSubcategoryClick(HomeData.ProductDealsEntity items);

        void onSubcategoryViewAllClick(HomeData.ProductDealsEntity items);
    }


    

}