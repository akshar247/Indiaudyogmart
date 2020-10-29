package com.indiaudyogmart.adpter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.ChatListDetailResponse;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

public class MessageDetailAdapter extends RecyclerView.Adapter<MessageDetailAdapter.MyViewHolder> {
    private Activity activity;
    private Context mContext;
    private List<ChatListDetailResponse.DataEntity> mData = new ArrayList<ChatListDetailResponse.DataEntity>();
    AdapterCallback adapterCallback;

    public MessageDetailAdapter(Context mContext, List<ChatListDetailResponse.DataEntity> mData, AdapterCallback adapterCallback1) {
        this.mContext = mContext;
        this.adapterCallback = adapterCallback1;
        this.mData = mData;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater mInflater = LayoutInflater.from(mContext);
        View view = mInflater.inflate(R.layout.item_message_detail, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        try {
            final ChatListDetailResponse.DataEntity dataEntitytemp = mData.get(position);

            if (CommonFunctions.getloginresponse(mContext).data.id == dataEntitytemp.sender.id) {

                holder.rlRight.setVisibility(View.VISIBLE);
                holder.rlLeft.setVisibility(View.GONE);
                holder.tvMsgReciver.setText(dataEntitytemp.message + "");
                String url = IndiaMartConfig.user_image+dataEntitytemp.sender.profilepic;
                int roundvalu = (int) activity.getResources().getDimension(R.dimen.roundcorner);
                RequestOptions requestOptions = new RequestOptions()
                        .apply(RequestOptions.placeholderOf(R.drawable.no_image));
                Glide.with(activity)
                        .load(url)
                        .apply(requestOptions)
                        .into(holder.ivProfileReciver);
            }
            else {
                holder.rlRight.setVisibility(View.GONE);
                holder.rlLeft.setVisibility(View.VISIBLE);
                holder.tvMsg.setText(dataEntitytemp.message + "");
                String url =  IndiaMartConfig.user_image+dataEntitytemp.receiver.profilepic;
                int roundvalu = (int) activity.getResources().getDimension(R.dimen.roundcorner);
                RequestOptions requestOptions = new RequestOptions()
                        .apply(RequestOptions.placeholderOf(R.drawable.no_image));
                Glide.with(activity)
                        .load(url)
                        .apply(requestOptions)
                        .into(holder.ivProfile);
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
        CircleImageView ivProfile;
        @BindView(R.id.tv_msg)
        TextView tvMsg;
        @BindView(R.id.btn_pay)
        Button btnPay;
        @BindView(R.id.rl_button)
        RelativeLayout rlButton;
        @BindView(R.id.fl_button)
        FrameLayout flButton;
        @BindView(R.id.iv_ticks)
        ImageView ivTicks;
        @BindView(R.id.seen_time)
        TextView seenTime;
        @BindView(R.id.rl1)
        RelativeLayout rl1;
        @BindView(R.id.rl_left)
        RelativeLayout rlLeft;
        @BindView(R.id.iv_profile_reciver)
        CircleImageView ivProfileReciver;
        @BindView(R.id.tv_msg_reciver)
        TextView tvMsgReciver;
        @BindView(R.id.btn_callnow)
        Button btnCallnow;
        @BindView(R.id.rl_button_reciver)
        RelativeLayout rlButtonReciver;
        @BindView(R.id.fl_button_reciver)
        FrameLayout flButtonReciver;
        @BindView(R.id.iv_ticks_reciver)
        ImageView ivTicksReciver;
        @BindView(R.id.seen_time_reciver)
        TextView seenTimeReciver;
        @BindView(R.id.rl_reciver)
        RelativeLayout rlReciver;
        @BindView(R.id.rl_right)
        RelativeLayout rlRight;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    public static class AdapterCallback {
        void onSubcategoryClick(ChatListDetailResponse.DataEntity items) {

        }

        void onSubcategoryViewAllClick(ChatListDetailResponse.DataEntity items) {

        }

    }
}
