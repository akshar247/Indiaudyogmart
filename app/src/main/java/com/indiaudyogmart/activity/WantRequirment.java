package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class WantRequirment extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.transactions)
    TextView transactions;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindView(R.id.tv_qty)
    TextView tvQty;
    @BindView(R.id.et_qty)
    EditText etQty;
    @BindView(R.id.rl_qty)
    RelativeLayout rlQty;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.et_unit)
    EditText etUnit;
    @BindView(R.id.rl_unit)
    RelativeLayout rlUnit;
    @BindView(R.id.tv_appox_value)
    TextView tvAppoxValue;
    @BindView(R.id.et_approx_value)
    EditText etApproxValue;
    @BindView(R.id.rl_approx_value)
    RelativeLayout rlApproxValue;
    @BindView(R.id.tv_requirement_time)
    TextView tvRequirementTime;
    @BindView(R.id.sp_requirement_time)
    Spinner spRequirementTime;
    @BindView(R.id.rl_requirement_time)
    RelativeLayout rlRequirementTime;
    @BindView(R.id.tv_your_location)
    TextView tvYourLocation;
    @BindView(R.id.sp_your_location)
    Spinner sp_your_location;
    @BindView(R.id.rl_your_location)
    RelativeLayout rlYourLocation;
    @BindView(R.id.tv_buying_details)
    TextView tvBuyingDetails;
    @BindView(R.id.et_buying_details)
    EditText etBuyingDetails;
    @BindView(R.id.rl_buying_details)
    RelativeLayout rlBuyingDetails;
    @BindView(R.id.check_terms_condition)
    CheckBox checkTermsCondition;
    @BindView(R.id.tv_terms_condition)
    TextView tvTermsCondition;
    @BindView(R.id.rl_terms_cond)
    RelativeLayout rlTermsCond;
    @BindView(R.id.btn_sendreq)
    Button btnSendreq;
    String pname;
    @BindView(R.id.tv_country)
    TextView tvCountry;
    @BindView(R.id.tv_country_val)
    TextView tv_country_val;
    @BindView(R.id.rl_country)
    RelativeLayout rlCountry;
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


        setContentView(R.layout.activity_want_requirment);
        ButterKnife.bind(this);
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
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
            if (getIntent().getExtras() != null && getIntent().getExtras().containsKey(Constants.name)) {
                pname = getIntent().getExtras().getString(Constants.name);

            }
            btnSendreq.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    senddata();
                }
            });
            ivBack.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
            tv_country_val.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        Intent allcity=new Intent(WantRequirment.this, AllCityActivity.class);
                        startActivity(allcity);
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
    protected void onResume() {
        super.onResume();
        try {
            tv_country_val.setText(CommonFunctions.getPreference(WantRequirment.this,Constants.selectedcity,"SURAT"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void senddata() {
        try {
            if (CommonFunctions.checkConnection(this)) {
                if (TextUtils.isEmpty(etQty.getText().toString())) {
                    Toast.makeText(this, R.string.error_qty, Toast.LENGTH_SHORT).show();
                    etQty.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etUnit.getText().toString())) {
                    Toast.makeText(this, R.string.error_unit, Toast.LENGTH_SHORT).show();
                    etUnit.requestFocus();
                    return;
                } else if (TextUtils.isEmpty(etApproxValue.getText().toString())) {
                    Toast.makeText(this, R.string.error_approx, Toast.LENGTH_SHORT).show();
                    etApproxValue.requestFocus();
                    return;
                }  else if (TextUtils.isEmpty(etBuyingDetails.getText().toString())) {
                    Toast.makeText(this, R.string.error_detail, Toast.LENGTH_SHORT).show();
                    etBuyingDetails.requestFocus();
                    return;
                }
                else if (TextUtils.isEmpty(tv_country_val.getText().toString())) {
                    Toast.makeText(this, R.string.error_country, Toast.LENGTH_SHORT).show();
                    tv_country_val.requestFocus();
                    return;
                } else {
                    sendrequirment();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendrequirment() {
        try {
            if (CommonFunctions.checkConnection(WantRequirment.this)) {
                CommonFunctions.createProgressBar(WantRequirment.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(WantRequirment.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.store_wyw;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.ww_qty, etQty.getText().toString().trim());
                mParams.put(Constants.ww_unit, etUnit.getText().toString().trim());
                mParams.put(Constants.wwy_appro_value, etApproxValue.getText().toString().trim());
                mParams.put(Constants.ww_rtime, spRequirementTime.getSelectedItem().toString());
                mParams.put(Constants.wwy_location, tv_country_val.getText().toString().trim());
                mParams.put(Constants.ww_location, sp_your_location.getSelectedItem().toString());
                mParams.put(Constants.ww_text, etBuyingDetails.getText().toString().trim());

                mParams.put(Constants.search_text, pname);
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

                                    if (response.getBoolean(Constants.status)) {
                                        Toast.makeText(WantRequirment.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                        finish();
                                    } else {
                                        Toast.makeText(WantRequirment.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(WantRequirment.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
}