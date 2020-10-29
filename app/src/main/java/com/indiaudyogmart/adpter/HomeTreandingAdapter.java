package com.indiaudyogmart.adpter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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

public class HomeTreandingAdapter extends RecyclerView.Adapter<HomeTreandingAdapter.MyViewHolder> {

    private Activity activity;

    List<HomeData.TrendingCategoryEntity> dataEntity = new ArrayList<>();

    AdapterCallback adapterCallback;
    String def_lang;
    HomeData homeData;
    public class MyViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_product_name)
        TextView tvProductName;
        @BindView(R.id.tr1)
        TableRow tr1;
        @BindView(R.id.product_img)
        ImageView productImg;
        @BindView(R.id.tv_d1)
        TextView tvD1;
        @BindView(R.id.tv_d2)
        TextView tvD2;
        @BindView(R.id.tv_d3)
        TextView tvD3;
        @BindView(R.id.tv_d4)
        TextView tvD4;
        @BindView(R.id.tv_d5)
        TextView tvD5;
        @BindView(R.id.viewmore)
        TextView viewmore;
        @BindView(R.id.rl_orange)
        CardView rlOrange;

        public MyViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    public HomeTreandingAdapter(Activity mContext, List<HomeData.TrendingCategoryEntity> dataEntity,  HomeData homeData, AdapterCallback adapterCallback1, String lang) {
        this.activity = mContext;
        this.dataEntity = dataEntity;

        this.adapterCallback = adapterCallback1;
        this.def_lang = lang;
        this.homeData = homeData;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.tranding_item, parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        try {

            final HomeData.TrendingCategoryEntity dataEntitytemp = dataEntity.get(position);


                holder.tvProductName.setText(dataEntitytemp.trendingcategory.name + "");
                String url= IndiaMartConfig.home_category+dataEntitytemp.trendingcategory.image;
            int roundvalu = (int) activity.getResources().getDimension(R.dimen.roundcorner);
                RequestOptions requestOptions = new RequestOptions()
                        .apply(RequestOptions.placeholderOf(R.drawable.no_image));
                Glide.with(activity)
                        .load(url)
                        .apply(requestOptions)
                        .into(holder.productImg);

             holder.tvD1.setText("");
            holder.tvD2.setText("");
            holder.tvD3.setText("");
            holder.tvD4.setText("");
            holder.tvD5.setText("");


            for(int i=0;i<dataEntitytemp.trendingcategory.childs.size();i++)
            {
                switch (i)
                {
                    case 0:holder.tvD1.setText(dataEntitytemp.trendingcategory.childs.get(i).name+"");
                        holder.tvD1.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if(adapterCallback!=null)
                                    adapterCallback.subcategorychildclick(dataEntitytemp.trendingcategory.childs.get(0));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    break;
                    case 1:holder.tvD2.setText(dataEntitytemp.trendingcategory.childs.get(i).name+"");
                        holder.tvD2.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if(adapterCallback!=null)
                                        adapterCallback.subcategorychildclick(dataEntitytemp.trendingcategory.childs.get(1));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });break;
                    case 2:holder.tvD3.setText(dataEntitytemp.trendingcategory.childs.get(i).name+"");
                        holder.tvD3.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if(adapterCallback!=null)
                                        adapterCallback.subcategorychildclick(dataEntitytemp.trendingcategory.childs.get(2));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });break;
                    case 3:holder.tvD4.setText(dataEntitytemp.trendingcategory.childs.get(i).name+"");
                        holder.tvD4.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if(adapterCallback!=null)
                                        adapterCallback.subcategorychildclick(dataEntitytemp.trendingcategory.childs.get(3));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                        break;
                    case 4:holder.tvD5.setText(dataEntitytemp.trendingcategory.childs.get(i).name+"");
                        holder.tvD5.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                try {
                                    if(adapterCallback!=null)
                                        adapterCallback.subcategorychildclick(dataEntitytemp.trendingcategory.childs.get(4));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                    break;

                }
            }

                holder.productImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        try {
                            adapterCallback.onSubcategoryClick(dataEntitytemp);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
                holder.viewmore.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        try {
                            adapterCallback.onSubcategoryViewAllClick(dataEntitytemp);
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
        void onSubcategoryClick(HomeData.TrendingCategoryEntity items);

        void onSubcategoryViewAllClick(HomeData.TrendingCategoryEntity items);
        void subcategorychildclick(HomeData.ChildsEntity items);

    }


    static
    class ViewHolder {


        ViewHolder(View view) {
            ButterKnife.bind(this, view);
        }
    }
}