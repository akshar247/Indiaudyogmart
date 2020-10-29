package com.indiaudyogmart.adpter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TableRow;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LeadDetailResp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MyLeadSellerMessageAdapter extends RecyclerView.Adapter<MyLeadSellerMessageAdapter.MyViewHolder> {
    private Context mContext;
    private List<LeadDetailResp.MessagesEntity> mData;
    AdapterCallback adapterCallback;
    String def_lang;


    public MyLeadSellerMessageAdapter(Context mContext, List<LeadDetailResp.MessagesEntity> mData, AdapterCallback adapterCallback1, String lang) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData = mData;
        this.def_lang = lang;
    }

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        view = mInflater.inflate(R.layout.lead_details_item_3, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final LeadDetailResp.MessagesEntity dataEntitytemp = mData.get(position);
            holder.tvName.setText(dataEntitytemp.sender.name + "");
            if(dataEntitytemp.sender.id==CommonFunctions.getloginresponse(mContext).data.id)
            holder.btn_type.setText(mContext.getString(R.string.seller));
            else
                holder.btn_type.setText(mContext.getString(R.string.buyer));

            holder.date.setText(dataEntitytemp.date.split("T")[0] + "" + "");


            holder.tvDate.setText("");

            if(dataEntitytemp.historySendQuoteRequestId==0)
            holder.tvDis.setText(dataEntitytemp.message+"");
            else
                holder.tvDis.setText(dataEntitytemp.hnoteForCustomer+"");

            if(dataEntitytemp.status.equalsIgnoreCase("0"))
            {
                holder.tvStatus.setVisibility(View.GONE);
                holder.tvStatus.setText("");
            }
            else {
                holder.tvStatus.setVisibility(View.VISIBLE);
                if (dataEntitytemp.historySendQuoteRequestId == 0)
                {
                    holder.tvStatus.setText(dataEntitytemp.status);
                    holder.tvStatus.setBackgroundColor(ContextCompat.getColor(mContext,R.color.green));
                }
                else
                {
                    holder.tvStatus.setText(dataEntitytemp.hsqrStatus);
                    if(dataEntitytemp.hsqrStatus.equalsIgnoreCase(Constants.Rejected))
                    {
                        holder.tvStatus.setBackgroundColor(ContextCompat.getColor(mContext,R.color.red));
                    }
                    else
                    {
                        holder.tvStatus.setBackgroundColor(ContextCompat.getColor(mContext,R.color.green));
                    }
                }
            }


            String url = IndiaMartConfig.profile_pic_path + dataEntitytemp.sender.profilepic;
            int roundvalu = (int) mContext.getResources().getDimension(R.dimen.roundcorner);
            RequestOptions requestOptions = new RequestOptions()
                    .apply(RequestOptions.placeholderOf(R.drawable.no_image));
            Glide.with(mContext)
                    .load(url)
                    .apply(requestOptions)
                    .into(holder.iv_profile);

            if(dataEntitytemp.historySendQuoteRequestId!=0)
            {
                holder.rlQuote.setVisibility(View.VISIBLE);
                holder.qutQty.setText(dataEntitytemp.hquoteQty+"");
                holder.bidPrice.setText(dataEntitytemp.htotalPrice+"");

                holder.samples.setText(dataEntitytemp.hnoOfSample>=1?mContext.getString(R.string.dialog_yes):mContext.getString(R.string.dialog_no));
                holder.tv_shipping_day.setText(dataEntitytemp.hnoOfSample>1?dataEntitytemp.hshippingTime+" "+mContext.getString(R.string.days):"");

            }
            else
            {
                holder.rlQuote.setVisibility(View.GONE);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.iv_profile)
        CircleImageView iv_profile;
        @BindView(R.id.tv_name)
        TextView tvName;
        @BindView(R.id.btn_type)
        TextView btn_type;

        @BindView(R.id.tv_date)
        TextView tvDate;
        @BindView(R.id.date)
        TextView date;
        @BindView(R.id.tv_dis)
        TextView tvDis;
        @BindView(R.id.tv_status)
        TextView tvStatus;
        @BindView(R.id.tv_qut_qty)
        TextView tvQutQty;
        @BindView(R.id.tv_bid_price)
        TextView tvBidPrice;
        @BindView(R.id.tv_samples)
        TextView tvSamples;
        @BindView(R.id.firstRow)
        TableRow firstRow;
        @BindView(R.id.qut_qty)
        TextView qutQty;
        @BindView(R.id.bid_price)
        TextView bidPrice;
        @BindView(R.id.samples)
        TextView samples;
        @BindView(R.id.tv_shipping_day)
        TextView tv_shipping_day;

        @BindView(R.id.secondrow)
        TableRow secondrow;
        @BindView(R.id.rl_quote)
        RelativeLayout rlQuote;
        @BindView(R.id.rl_orange)
        RelativeLayout rlOrange;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }

    }

    public interface AdapterCallback {
        void onSubcategoryClick(LeadDetailResp.MessagesEntity items);

        void onSubcategoryViewAllClick(LeadDetailResp.MessagesEntity items);
    }

    static
    class ViewHolder {

        ViewHolder(View view) {

        }
    }
}
