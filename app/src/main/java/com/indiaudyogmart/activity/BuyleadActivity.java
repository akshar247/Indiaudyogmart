package com.indiaudyogmart.activity;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.BuyLeadAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.fragment.IndustriesChildFragment;
import com.indiaudyogmart.fragment.ProductDetailFragment;
import com.indiaudyogmart.model.BuyLeadResponse;
import com.indiaudyogmart.model.IndustriesResponse;
import com.indiaudyogmart.model.LeftLeadResponse;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class BuyleadActivity extends AppCompatActivity implements PaymentResultListener {
    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.leads)
    TextView leads;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;
    @BindView(R.id.rl_bidsLeft)
    RelativeLayout rlBidsLeft;
    @BindView(R.id.rl_tvcontain)
    RelativeLayout rlTvcontain;
    @BindView(R.id.ll_buyLeads)
    RelativeLayout llBuyLeads;
    @BindView(R.id.rv_buyLeads)
    RecyclerView rvBuyLeads;
    BuyLeadAdapter buyLeadAdapter;
    BuyLeadResponse buyLeadResponse;
    LeftLeadResponse leftLeadResponse;
    @BindView(R.id.tv_leads_left)
    TextView tvLeadsLeft;
    @BindView(R.id.tv_bids_left)
    TextView tvBidsLeft;
    @BindView(R.id.tv_remaining)
    TextView tvRemaining;
    @BindView(R.id.tv_description)
    TextView tvDescription;
    BuyLeadResponse.PackagesEntity selectedpayment;
    String lang;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Locale locale = new Locale(lang);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        setContentView(R.layout.activity_buylead);


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
            Checkout.preload(getApplicationContext());
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();
            rvBuyLeads.setLayoutManager(new GridLayoutManager(BuyleadActivity.this, 2));
            if (CommonFunctions.checkConnection(BuyleadActivity.this)) {
                getbuyleads();

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        try {
            getleaddata();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void getleaddata() {
        try {
            if (CommonFunctions.checkConnection(BuyleadActivity.this)) {
                CommonFunctions.createProgressBar(BuyleadActivity.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(BuyleadActivity.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.left_lead_by_seller;
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
                                    leftLeadResponse = gson.fromJson(response.toString(), LeftLeadResponse.class);
                                    if (leftLeadResponse.status) {
                                        setLeftLeaddata();
                                    } else {
                                        Toast.makeText(BuyleadActivity.this, buyLeadResponse.message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(BuyleadActivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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

    private void setLeftLeaddata() {
        try {
            if (leftLeadResponse.data != null)
            {
                tvBidsLeft.setText(leftLeadResponse.data.spLeadLimit+" " +getString(R.string.leads));
                tvLeadsLeft.setText(leftLeadResponse.data.spLeadLimit+" " +getString(R.string.leads));
                tvRemaining.setText(leftLeadResponse.data.leftDays+" " +getString(R.string.days));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getbuyleads() {
        try {
            if (CommonFunctions.checkConnection(BuyleadActivity.this)) {
                CommonFunctions.createProgressBar(BuyleadActivity.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(BuyleadActivity.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.my_package;
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
                                    buyLeadResponse = gson.fromJson(response.toString(), BuyLeadResponse.class);
                                    if (buyLeadResponse.status) {
                                        setdata();
                                    } else {
                                        Toast.makeText(BuyleadActivity.this, buyLeadResponse.message, Toast.LENGTH_SHORT).show();
                                    }
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(BuyleadActivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
            if (buyLeadResponse.data != null) {
                buyLeadAdapter = new BuyLeadAdapter(this, buyLeadResponse.data.packages, new BuyLeadAdapter.AdapterCallback() {

                    @Override
                    public void onSubcategoryClick(BuyLeadResponse.PackagesEntity dataEntitytemp) {

                        startpayment(dataEntitytemp);
                    }

                    @Override
                    public void OnSubcategoryViewAllClick(BuyLeadResponse.PackagesEntity dataEntitytemp) {

                    }
                });
                rvBuyLeads.setAdapter(buyLeadAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startpayment(BuyLeadResponse.PackagesEntity dataEntitytemp) {

        try {
            selectedpayment=dataEntitytemp;
            final Checkout checkout = new Checkout();
            final Activity activity = this;
            JSONObject options = new JSONObject();
            options.put("name", dataEntitytemp.type);
            options.put("description", "");
            options.put("image", "");
            options.put("currency", "INR");
            double total = Double.parseDouble(dataEntitytemp.price + "");
            total = total * 100;
            options.put("amount", total);//pass amount in currency subunits
            JSONObject preFill = new JSONObject();
            preFill.put("prefill.email", CommonFunctions.getloginresponse(this).data.email);
            preFill.put("prefill.contact", CommonFunctions.getloginresponse(this).data.contactno);
            options.put("preFill", preFill);
            checkout.open(activity, options);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Sucsess", Toast.LENGTH_SHORT).show();
        try {
            updatepaymentinfo(s,selectedpayment);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updatepaymentinfo(String payment_id , BuyLeadResponse.PackagesEntity selectedpayment) {
        try {
            CommonFunctions.createProgressBar(this, getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(this);
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.package_purchase+selectedpayment.id;
            Map<String, String> mParams = new HashMap<>();
            mParams.put(Constants.payment_id, payment_id );

            Log.e("url", url);
            Log.e("mParams", mParams.toString());
            AndroidNetworking.post(url)
                    .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                    .addBodyParameter(mParams)
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
                        if(response.getBoolean(Constants.status))
                        getleaddata();
                        else
                            Toast.makeText(BuyleadActivity.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(ANError anError) {
                    try {
                        CommonFunctions.destroyProgressBar();
                        Toast.makeText(BuyleadActivity.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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
    public void onPaymentError(int i, String s) {
        Toast.makeText(this, "Payment Faild", Toast.LENGTH_SHORT).show();
    }

}