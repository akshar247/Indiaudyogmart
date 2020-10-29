package com.indiaudyogmart.activity;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableRow;
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
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class QuotePopup extends AppCompatActivity {
    @BindView(R.id.iv_back)
    ImageView back;
    String productname, productunit,pro_pic,price,productid;
    @BindView(R.id.rl_quote)
    RelativeLayout rlQuote;
    @BindView(R.id.hr1)
    TableRow hr1;
    @BindView(R.id.tv_pro)
    TextView tvPro;
    @BindView(R.id.et_pro)
    TextView etPro;
    @BindView(R.id.rl_pro)
    RelativeLayout rlPro;
    @BindView(R.id.tv_category)
    TextView tvCategory;
    @BindView(R.id.et_category)
    Spinner etCategory;
    @BindView(R.id.rl_cat)
    RelativeLayout rlCat;
    @BindView(R.id.tv_qty)
    TextView tvQty;
    @BindView(R.id.et_qty)
    EditText etQty;
    @BindView(R.id.iv_qty)
    ImageView ivQty;
    @BindView(R.id.rl_qty)
    RelativeLayout rlQty;
    @BindView(R.id.tv_unit)
    TextView tvUnit;
    @BindView(R.id.tv_unit_name)
    TextView tvUnitName;
    @BindView(R.id.rl_unit)
    RelativeLayout rlUnit;
    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.et_price)
    EditText etPrice;
    @BindView(R.id.rl_price)
    RelativeLayout rlPrice;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.et_email)
    EditText etEmail;
    @BindView(R.id.rl_email)
    RelativeLayout rlEmail;
    @BindView(R.id.tv_mobile)
    TextView tvMobile;
    @BindView(R.id.et_mobile)
    EditText etMobile;
    @BindView(R.id.rl_mobile)
    RelativeLayout rlMobile;
    @BindView(R.id.tv_sample)
    TextView tvSample;
    @BindView(R.id.ck_yes)
    CheckBox ckYes;
    @BindView(R.id.ck_no)
    CheckBox ckNo;
    @BindView(R.id.rl_Sample)
    RelativeLayout rlSample;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.et_des)
    EditText etDes;
    @BindView(R.id.rl_des)
    RelativeLayout rlDes;
    @BindView(R.id.btn_sumbit)
    Button btnSumbit;
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


        setContentView(R.layout.activity_quote_popup);

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
            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.prod_name))
            {
                productname=getIntent().getExtras().getString(Constants.prod_name);
                etPro.setText(productname+"");
            }
            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.product_id))
            {
                productid=getIntent().getExtras().getString(Constants.product_id);

            }

            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.prod_unit))
            {
                productunit=getIntent().getExtras().getString(Constants.prod_unit);
                tvUnitName.setText(productunit+"");
            }
            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.price))
            {
                price=getIntent().getExtras().getString(Constants.price);
                etPrice.setText(price+"");
            }
            if(getIntent().getExtras()!=null && getIntent().getExtras().containsKey(Constants.pro_pic))
            {
                pro_pic=getIntent().getExtras().getString(Constants.pro_pic);

            }

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            etEmail.setText(CommonFunctions.getloginresponse(this).data.email+"");
            etMobile.setText(CommonFunctions.getloginresponse(this).data.contactno+"");
            ckYes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if(isChecked)
                            ckNo.setChecked(false);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            ckNo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    try {
                        if(isChecked)
                            ckYes.setChecked(false);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_sumbit)
    public void onViewClicked() {
        try {
            if(CommonFunctions.checkConnection(this))
            {
                if(TextUtils.isEmpty(etQty.getText().toString()))
                {
                    Toast.makeText(this, R.string.error_add_quote1, Toast.LENGTH_SHORT).show();
                    etQty.requestFocus();
                    return;
                }
               else  if(TextUtils.isEmpty(etPrice.getText().toString()))
                {
                    Toast.makeText(this, R.string.error_add_quote2, Toast.LENGTH_SHORT).show();
                    etPrice.requestFocus();
                    return;
                }
                else  if(TextUtils.isEmpty(etEmail.getText().toString()))
                {
                    Toast.makeText(this, R.string.error_add_quote3, Toast.LENGTH_SHORT).show();
                    etEmail.requestFocus();
                    return;
                }
                else  if(TextUtils.isEmpty(etMobile.getText().toString()))
                {
                    Toast.makeText(this, R.string.error_add_quote4, Toast.LENGTH_SHORT).show();
                    etMobile.requestFocus();
                    return;
                }
                else
                {
                    sendquote();
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendquote() {
        try {
            if (CommonFunctions.checkConnection(QuotePopup.this)) {
                CommonFunctions.createProgressBar(QuotePopup.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(QuotePopup.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.add_quote_by_customer+productid;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.cust_contactno, etMobile.getText().toString().trim());
                mParams.put(Constants.cust_email, etEmail.getText().toString().trim());
                mParams.put(Constants.product_description, etDes.getText().toString().trim());
                mParams.put(Constants.cust_qty, etQty.getText().toString().trim());
                mParams.put(Constants.cust_unit, tvUnit.getText().toString().trim());
                mParams.put(Constants.cust_price, etPrice.getText().toString().trim());
                mParams.put(Constants.image_pt, pro_pic+"");

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

                                    if(response.getBoolean(Constants.status)){
                                        finish();
                                    }
                                    else {
                                        Toast.makeText(QuotePopup.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(QuotePopup.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }
                        });

            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}