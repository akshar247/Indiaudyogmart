package com.indiaudyogmart.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.KycInfoAdapter;
import com.indiaudyogmart.adpter.MyReviewAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.KycInforesponse;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.MyReviewResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class KycStatus extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.rv_kyc_info)
    RecyclerView kycinfo;
    KycInforesponse kycInforesponse;
    KycInfoAdapter kycInfoAdapter;
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

        setContentView(R.layout.activity_kyc_status);
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
            if(getSupportActionBar()!=null)
                getSupportActionBar().hide();
            if (CommonFunctions.checkConnection(KycStatus.this)) {
                getKycInfoList();
            }



            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });

            kycinfo.setLayoutManager(new LinearLayoutManager(KycStatus.this));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getKycInfoList() {
        try {
            if( CommonFunctions.checkConnection(KycStatus.this)) {
                CommonFunctions.createProgressBar(KycStatus.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(KycStatus.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.show_kyc_history;
                Map<String, String> mParams = new HashMap<>();
                Log.e("url", url);
                Log.e("mParams", mParams.toString());
                AndroidNetworking.get(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)

                        .setTag(url)
                        .setPriority(Priority.HIGH)
                        .build().getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            CommonFunctions.destroyProgressBar();
                            Log.e("res", response.toString());
                            Gson gson = new Gson();
                            kycInforesponse = gson.fromJson(response.toString(), KycInforesponse.class);
                            if (kycInforesponse.status) {
                                setdata();
                            } else {
                                Toast.makeText(KycStatus.this, kycInforesponse.message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            CommonFunctions.destroyProgressBar();
                            Toast.makeText(KycStatus.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
            if (kycInforesponse.data != null) {
                kycInfoAdapter=new KycInfoAdapter(this, kycInforesponse.data, new KycInfoAdapter.AdapterCallback() {

                    @Override
                    public void onSubcategoryClick(KycInforesponse.DataEntity items) {

                    }

                    @Override
                    public void onSubcategoryViewAllClick(KycInforesponse.DataEntity items) {

                    }
                },"");
                kycinfo.setAdapter(kycInfoAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}