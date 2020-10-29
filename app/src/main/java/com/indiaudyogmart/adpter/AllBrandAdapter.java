package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.indiaudyogmart.R;
import com.indiaudyogmart.model.AllBrandResp;
import com.indiaudyogmart.model.AllBrandResp;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllBrandAdapter extends BaseAdapter {
    private Context mContext;
    private List<AllBrandResp.DataEntity> mData = new ArrayList<AllBrandResp.DataEntity>();
    AdapterCallback adapterCallback;
    LayoutInflater inflter;


    public AllBrandAdapter(Context mContext, List<AllBrandResp.DataEntity> mData, AdapterCallback adapterCallback1) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData = mData;
        inflter = (LayoutInflater.from(mContext));

    }



    public void updateList(List<AllBrandResp.DataEntity> temp) {
        try {
            mData=temp;
            notifyDataSetChanged();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        view = inflter.inflate(R.layout.item_city, null);

        try {
            TextView tv_city = (TextView) view.findViewById(R.id.tv_city);
            tv_city.setText(mData.get(position).name+"");
           /* tv_city.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if(position!=0)
                        {
                            if(adapterCallback!=null)
                                adapterCallback.onSubcategoryClick(mData.get(position));
                        }

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });*/
        } catch (Exception e) {
            e.printStackTrace();
        }
        return view;

    }

  
    public interface AdapterCallback {
        void onSubcategoryClick(AllBrandResp.DataEntity dataEntitytemp);
    }

 
}
