package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.IndustriesChildResponse;
import com.indiaudyogmart.model.IndustriesChildResponse.DataEntity;

import java.util.ArrayList;
import java.util.List;

public class IndustriesChildAdapter extends RecyclerView.Adapter<IndustriesChildAdapter.MyViewHolder> {
    private Context mContext;
   AdapterCallback adapterCallback;
    String def_lang;
    private List<DataEntity> mData=new ArrayList<>();

    public IndustriesChildAdapter(Context mContext, List<DataEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.mData=mData;
        this.adapterCallback = adapterCallback1;
        this.def_lang = lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater=LayoutInflater.from(mContext);
        view=mInflater.inflate(R.layout.industries_item,parent,false);
        return new MyViewHolder(view);
    }

    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {



        try {
            final IndustriesChildResponse.DataEntity dataEntitytemp = mData.get(position);


            holder.title.setText(dataEntitytemp.name);
            String url = IndiaMartConfig.category_path + dataEntitytemp.image;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.image);

            holder.image.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(adapterCallback!= null){

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
        TextView title;
        ImageView image;
        RelativeLayout relativelayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            title = (TextView) itemView.findViewById(R.id.title);
            image = (ImageView) itemView.findViewById(R.id.image);
            relativelayout = (RelativeLayout) itemView.findViewById(R.id.rl_industries);
        }
    }
    public interface AdapterCallback {
        void onSubcategoryClick(IndustriesChildResponse.DataEntity items);

        void onSubcategoryViewAllClick(IndustriesChildResponse.DataEntity items);
    }
}
