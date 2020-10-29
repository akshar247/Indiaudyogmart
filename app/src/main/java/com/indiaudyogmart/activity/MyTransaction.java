package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.MyTransactionAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.MyTransactionResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyTransaction extends AppCompatActivity {
    @BindView(R.id.select_transactions)
    RecyclerView transaction;
    MyTransactionResponse myTransactionResponse;
    MyTransactionAdapter myTransactionAdapter;
    @BindView(R.id.iv_back)
    ImageView back;
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


        setContentView(R.layout.activity_my_transaction);
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
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            if(getSupportActionBar()!=null)
                getSupportActionBar().hide();
            if (CommonFunctions.checkConnection(MyTransaction.this)) {
                getMyTransactionList();
            }
            transaction.setLayoutManager(new LinearLayoutManager(MyTransaction.this));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMyTransactionList() {
        try {
            if( CommonFunctions.checkConnection(MyTransaction.this)) {
                CommonFunctions.createProgressBar(MyTransaction.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(MyTransaction.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_transaction_data;
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
                            myTransactionResponse= gson.fromJson(response.toString(), MyTransactionResponse.class);
                            if (myTransactionResponse.status) {
                                setdata();
                            } else {
                                Toast.makeText(MyTransaction.this, myTransactionResponse.message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            CommonFunctions.destroyProgressBar();
                            Toast.makeText(MyTransaction.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
            if (myTransactionResponse.data != null) {
                myTransactionAdapter=new MyTransactionAdapter(this, myTransactionResponse.data, new MyTransactionAdapter.AdapterCallback() {

                    @Override
                    public void onSubcategoryClick(MyTransactionResponse.DataEntity items) {

                    }

                    @Override
                    public void onSubcategoryViewAllClick(MyTransactionResponse.DataEntity items) {

                    }
                },"");
                transaction.setAdapter(myTransactionAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}