package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.MessageDetailAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.ChatListDetailResponse;
import com.indiaudyogmart.model.ChatListResponse;
import com.indiaudyogmart.model.HomeData;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MessageDetail extends AppCompatActivity {

    String id;
    String sid;
    ChatListDetailResponse chatListDetailResponse;
    MessageDetailAdapter messageDetailAdapter;
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.tv_lastseen)
    TextView tvLastseen;
    @BindView(R.id.iv_refresh)
    ImageView ivRefresh;
    @BindView(R.id.iv_call)
    ImageView ivCall;
    @BindView(R.id.action1)
    RelativeLayout action1;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindView(R.id.rv_msgdetail)
    RecyclerView rvMsgdetail;
    @BindView(R.id.et_send)
    EditText etSend;
    @BindView(R.id.iv3)
    ImageView iv3;
    @BindView(R.id.button_send)
    RelativeLayout button;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        lang = CommonFunctions.getPreference(this, Constants.defalt_languge, "en");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());


        setContentView(R.layout.activity_message_details);
        init();
    }
    @Override
    protected void attachBaseContext(Context newBase) {
        //super.attachBaseContext(newBase);
        lang = CommonFunctions.getPreference(newBase, Constants.defalt_languge, "en");
        super.attachBaseContext(LocaleHelper.onAttach(newBase, lang));
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());
    }
    private void init() {
        try {
            ButterKnife.bind(this);

            if(getIntent().getExtras().containsKey(Constants.name))
            {
                    tvName.setText(getIntent().getExtras().getString(Constants.name));
            }
            if(getIntent().getExtras().containsKey(Constants.id))
            {
                id=(getIntent().getExtras().getString(Constants.id));
                getChatdetail();
            }
            if(getIntent().getExtras().containsKey(Constants.senderid))
            {
                 sid = (getIntent().getExtras().getString(Constants.senderid));
            }
            LinearLayoutManager layoutManager=new LinearLayoutManager(this);
          /*  layoutManager.setStackFromEnd(true);
            layoutManager.setSmoothScrollbarEnabled(true);
            layoutManager.setReverseLayout(true);*/
            rvMsgdetail.setLayoutManager(layoutManager);


                button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (etSend.getText().toString().trim().length() == 0){
                            Toast.makeText(MessageDetail.this, getString(R.string.err_message), Toast.LENGTH_SHORT).show();
                        }
                        else {
                            sendchat();
                            CommonFunctions.hideSoftKeyboard(MessageDetail.this,etSend);

                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(this, "" + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void sendchat() {
        try {
            if (CommonFunctions.checkConnection(MessageDetail.this)) {
                CommonFunctions.createProgressBar(MessageDetail.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(MessageDetail.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.send_chat_message;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.message, etSend.getText().toString().trim());
                mParams.put(Constants.receiver_id,sid+"");
                Log.e("url", url);
                Log.e("mParams", mParams.toString());
                AndroidNetworking.post(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                        .setTag(url)
                        .addBodyParameter(mParams)
                        .setPriority(Priority.HIGH)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    etSend.setText("");
                                    Log.e("res", response.toString());
                                    if(response.getBoolean(Constants.status)){
                                        getChatdetail();
                                    }
                                    else {
                                        Toast.makeText(MessageDetail.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(MessageDetail.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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

    private void getChatdetail() {
        try {
            if (CommonFunctions.checkConnection(MessageDetail.this)) {
                CommonFunctions.createProgressBar(MessageDetail.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(MessageDetail.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.chat_list_detail+id;
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
                                    chatListDetailResponse = gson.fromJson(response.toString(), ChatListDetailResponse.class);
                                    if (chatListDetailResponse.status) {
                                        setdata();
                                    } else {
                                        Toast.makeText(MessageDetail.this, chatListDetailResponse.message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(MessageDetail.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
        if (chatListDetailResponse.data != null) {
            messageDetailAdapter = new MessageDetailAdapter(MessageDetail.this, chatListDetailResponse.data, new MessageDetailAdapter.AdapterCallback() {
                public void onSubcategoryClick(ChatListDetailResponse.DataEntity items) {
                    try {


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                public void onSubcategoryViewAllClick(HomeData.PremiumCollectionEntity items) {

                }

            });
            rvMsgdetail.setAdapter(messageDetailAdapter);
            rvMsgdetail.smoothScrollToPosition(chatListDetailResponse.data.size());
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @OnClick(R.id.iv_back)
    public void onClick() {
        finish();
    }
}