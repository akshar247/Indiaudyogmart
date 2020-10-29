package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
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
import com.indiaudyogmart.adpter.AllCityAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.AllCityResp;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AllCityActivity extends AppCompatActivity {
    String lang;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.rv_city)
    RecyclerView rvCity;
    AllCityResp allCityResp;
    AllCityAdapter allCityAdapter;
    @BindView(R.id.tv_nodata)
    TextView tvNodata;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

		 lang = CommonFunctions.getPreference(this, Constants.defalt_languge, "en");
        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_all_city);
        init();
    }

    private void init() {
        try {
            ButterKnife.bind(this);

            if (getSupportActionBar() != null)
                getSupportActionBar().hide();

            if (CommonFunctions.checkConnection(this))
                getcitydata();
            rvCity.setLayoutManager(new LinearLayoutManager(this));
            etSearch.addTextChangedListener(new TextWatcher() {
                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {

                    // filter your list from your input
                    if (s.length() == 0) {
                        allCityAdapter.updateList(allCityResp.data);
                    } else
                        filter(s.toString());
                    //you can use runnable postDelayed like 500 ms to delay search text
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void filter(String search) {
        try {
            List<AllCityResp.DataEntity> temp = new ArrayList();
            for (AllCityResp.DataEntity d : allCityResp.data) {
                //or use .equal(text) with you want equal match
                //use .toLowerCase() for better matches
                if (d.name.contains(search)) {
                    temp.add(d);
                }
            }
            //update recyclerview
            allCityAdapter.updateList(temp);
            if(temp.size()>0)
            {
                tvNodata.setVisibility(View.GONE);
            }
            else
            {
                tvNodata.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getcitydata() {
        try {
            if (CommonFunctions.checkConnection(this)) {
                CommonFunctions.createProgressBar(this, getString(R.string.msg_please_wait));

                LoginResponse loginResponse = CommonFunctions.getloginresponse(this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_cities;
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
                            allCityResp = gson.fromJson(response.toString(), AllCityResp.class);
                            if (allCityResp.status) {
                                setadadapter();
                            } else {
                                Toast.makeText(AllCityActivity.this, "", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(ANError anError) {
                        try {
                            CommonFunctions.destroyProgressBar();
                            Toast.makeText(AllCityActivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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

    private void setadadapter() {
        try {
            allCityAdapter = new AllCityAdapter(this, allCityResp.data, new AllCityAdapter.AdapterCallback() {
                @Override
                public void onSubcategoryClick(AllCityResp.DataEntity dataEntitytemp) {
                    try {
                        CommonFunctions.setPreference(AllCityActivity.this, Constants.selectedcity, dataEntitytemp.name);
                        CommonFunctions.setPreference(AllCityActivity.this, Constants.selectedcityid, dataEntitytemp.id);
                        finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            rvCity.setAdapter(allCityAdapter);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
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
}