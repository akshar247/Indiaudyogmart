package com.indiaudyogmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.MessageDetail;
import com.indiaudyogmart.adpter.MessageAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.ChatListResponse;
import com.indiaudyogmart.model.HomeData;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.MessageItem;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MessageFragment extends Fragment {
    View rootView;
    ChatListResponse chatListResponse;
    MessageAdapter messageAdapter;
    @BindView(R.id.message_item)
    RecyclerView recyclerview;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_message, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            getchatlist();
            recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 1));


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getchatlist() {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.chat_list;
                Map<String, String> mParams = new HashMap<>();
                Log.e("url", url);
                Log.e("mParams", mParams.toString());
                AndroidNetworking.get(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                        .setTag(url)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Log.e("res", response.toString());
                                    Gson gson = new Gson();
                                    chatListResponse = gson.fromJson(response.toString(), ChatListResponse.class);
                                    if (chatListResponse.status) {
                                        setdata();
                                    } else {
                                        Toast.makeText(getActivity(), chatListResponse.message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(getActivity(), R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setdata() {
        try {
            if (chatListResponse.data != null) {
                messageAdapter=new MessageAdapter(getActivity(), chatListResponse.data, new MessageAdapter.AdapterCallback() {

                    @Override
                    public void onSubcategoryClick(ChatListResponse.DataEntity items) {
                        try {

                            Intent i=new Intent(getActivity(), MessageDetail.class);
                            i.putExtra(Constants.name ,items.name);
                            i.putExtra(Constants.id,items.id+"");
                            i.putExtra(Constants.senderid,items.id+"");
                            startActivity(i);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                    public void onSubcategoryViewAllClick(HomeData.PremiumCollectionEntity items) {

                    }

                });
                recyclerview.setAdapter(messageAdapter);
            }

        }
        catch (Exception e) {
    e.printStackTrace();
}
}
}

