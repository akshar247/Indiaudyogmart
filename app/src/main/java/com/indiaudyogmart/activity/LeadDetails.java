package com.indiaudyogmart.activity;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.MyLeadSellerMessageAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LeadDetailResp;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.utils.LocaleHelper;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LeadDetails extends AppCompatActivity {
    @BindView(R.id.iv_close)
    ImageView back;

    LeadDetailResp leadDetailsSellerResponse;
    @BindView(R.id.tv_leaddetails)
    TextView tvLeaddetails;
    @BindView(R.id.rl_header)
    RelativeLayout rlHeader;
    @BindView(R.id.iv_product)
    ImageView ivProduct;
    @BindView(R.id.tv_name)
    TextView tvName;
    @BindView(R.id.btn_type)
    Button btnType;
    @BindView(R.id.tv_date)
    TextView tvDate;
    @BindView(R.id.date)
    TextView date;
    @BindView(R.id.tv_dis)
    TextView tvDis;
    @BindView(R.id.tv_quataion)
    TextView tvQuataion;
    @BindView(R.id.tv_qty_title)
    TextView tvQtyTitle;
    @BindView(R.id.tv_bid_price_title)
    TextView tvBidPriceTitle;
    @BindView(R.id.tv_sample_title)
    TextView tvSampleTitle;
    @BindView(R.id.tv_shipping)
    TextView tvShipping;
    @BindView(R.id.firstRow)
    TableRow firstRow;
    @BindView(R.id.tv_qty)
    TextView tvQty;
    @BindView(R.id.tv_bid_price)
    TextView tvBidPrice;
    @BindView(R.id.tv_sample)
    TextView tvSample;
    @BindView(R.id.tv_shipping_day)
    TextView tvShippingDay;
    @BindView(R.id.secondrow)
    TableRow secondrow;
    @BindView(R.id.tl_leaddata)
    TableLayout tlLeaddata;
    @BindView(R.id.rl_leadedata)
    RelativeLayout rlLeadedata;
    @BindView(R.id.rl_leaddetails)
    RelativeLayout rlLeaddetails;
    @BindView(R.id.rv_details)
    RecyclerView rvDetails;
    @BindView(R.id.et_search)
    EditText etSearch;
    @BindView(R.id.ll_button)
    LinearLayout ll_button;
    @BindView(R.id.ll_bottom)
    LinearLayout ll_bottom;
    String quoteid = "";
    JSONObject resp = new JSONObject();
    MyLeadSellerMessageAdapter myLeadSellerMessageAdapter;
    @BindView(R.id.tv_send)
    TextView tvSend;
    @BindView(R.id.tv_approve)
    TextView tvApprove;
    @BindView(R.id.tv_reject)
    TextView tvReject;
    LeadDetailResp.QuoteDataEntity quoteDataEntity;
    LeadDetailResp.SellerDataEntity sellerDataEntity;
    List<LeadDetailResp.MessagesEntity> messagesEntityList = null;
    @BindView(R.id.iv_call)
    ImageView ivCall;
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


        setContentView(R.layout.activity_lead_details);


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
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    private void init() {
        try {
            ButterKnife.bind(this);
            if (getSupportActionBar() != null)
                getSupportActionBar().hide();


            if (getIntent() != null && getIntent().getExtras().containsKey(Constants.id)) {
                quoteid = getIntent().getExtras().getString(Constants.id);
                getCategory(quoteid);
            }

            rvDetails.setLayoutManager(new LinearLayoutManager(LeadDetails.this));

            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    finish();
                }
            });
            tvLeaddetails.setText(R.string.leaddetails);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void getCategory(String id) {
        try {

            CommonFunctions.createProgressBar(LeadDetails.this, getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(LeadDetails.this);
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_lead_detail_by_seller + id;
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
                        resp = response;
                        if (resp.getBoolean(Constants.status)) {
                            setdata();
                        } else {
                            Toast.makeText(LeadDetails.this, loginResponse.message, Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(ANError anError) {
                    try {
                        CommonFunctions.destroyProgressBar();
                        Toast.makeText(LeadDetails.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setdata() {
        try {
            Gson gson = new Gson();
            if (resp.getJSONObject(Constants.data) != null && resp.getJSONObject(Constants.data).has(Constants.quote_data)) {
                quoteDataEntity = gson.fromJson(resp.getJSONObject(Constants.data).getJSONArray(Constants.quote_data).getJSONObject(0).toString(), LeadDetailResp.QuoteDataEntity.class);
                sellerDataEntity = gson.fromJson(resp.getJSONObject(Constants.data).getJSONObject(Constants.seller_data).toString(), LeadDetailResp.SellerDataEntity.class);
                Type listtype = new TypeToken<List<LeadDetailResp.MessagesEntity>>() {
                }.getType();

                if (resp.getJSONObject(Constants.data).get(Constants.messages) instanceof JSONArray)
                    messagesEntityList = gson.fromJson(resp.getJSONObject(Constants.data).getJSONArray(Constants.messages).toString(), listtype);


                tvName.setText(quoteDataEntity.custName + "");
                btnType.setText(getString(R.string.buyer) + "");
                date.setText(quoteDataEntity.createdAt.split("T")[0] + "");
                tvDis.setText(quoteDataEntity.noteForCustomer!=null ? quoteDataEntity.noteForCustomer+ "":"");
                tvQty.setText(quoteDataEntity.rfqpQty + "");
                tvBidPrice.setText(quoteDataEntity.rfqpPriceperqty + "");
                tvSample.setText(quoteDataEntity.rfqpIssample!=null ?quoteDataEntity.rfqpIssample+"": "");
                tvShippingDay.setText(" ");


                String url = IndiaMartConfig.product_path + quoteDataEntity.rfqpProductImage;
                int roundvalu = (int) getResources().getDimension(R.dimen.roundcorner);
                RequestOptions requestOptions = new RequestOptions()
                        .apply(RequestOptions.placeholderOf(R.drawable.no_image));
                Glide.with(this)
                        .load(url)
                        .apply(requestOptions)
                        .into(ivProduct);


                if (messagesEntityList != null && messagesEntityList.size() > 0) {
                    myLeadSellerMessageAdapter = new MyLeadSellerMessageAdapter(this, messagesEntityList, new MyLeadSellerMessageAdapter.AdapterCallback() {
                        @Override
                        public void onSubcategoryClick(LeadDetailResp.MessagesEntity items) {

                        }

                        @Override
                        public void onSubcategoryViewAllClick(LeadDetailResp.MessagesEntity items) {

                        }
                    }, "");
                    rvDetails.setAdapter(myLeadSellerMessageAdapter);
                }
                if (messagesEntityList != null && messagesEntityList.size() > 0) {
                    tvQuataion.setVisibility(View.GONE);
                } else {
                    tvQuataion.setVisibility(View.VISIBLE);
                    tvApprove.setVisibility(View.GONE);
                    tvReject.setVisibility(View.GONE);
                }

                tvQuataion.setVisibility(View.VISIBLE);
                tvApprove.setVisibility(View.GONE);
                tvReject.setVisibility(View.GONE);

                if (quoteDataEntity.sqrActiveClose.equalsIgnoreCase(Constants.active)) {
                    if (messagesEntityList != null && messagesEntityList.size() > 0) {
                        for (LeadDetailResp.MessagesEntity messagesEntity : messagesEntityList) {
                            if(messagesEntity.historySendQuoteRequestId!=0) {
                                if (messagesEntity.hsqrStatus != null) {
                                    if (messagesEntity.hsqrStatus.equalsIgnoreCase(Constants.processing)) {
                                        tvReject.setVisibility(View.VISIBLE);
                                        tvApprove.setVisibility(View.GONE);
                                    //    tvQuataion.setVisibility(View.GONE);
                                    } else if (!messagesEntity.hsqrStatus.equalsIgnoreCase(Constants.approved)
                                            && !messagesEntity.hsqrStatus.equalsIgnoreCase(Constants.rejected)) {
                                        tvApprove.setVisibility(View.VISIBLE);
                                    //    tvQuataion.setVisibility(View.GONE);

                                    } else if (!messagesEntity.hsqrStatus.equalsIgnoreCase(Constants.rejected)) {
                                        tvReject.setVisibility(View.VISIBLE);

                                    }

                                } else {
                                    tvQuataion.setVisibility(View.VISIBLE);
                                    tvApprove.setVisibility(View.GONE);
                                    tvReject.setVisibility(View.GONE);
                                }
                            }
                        }
                    }
                }

                if(quoteDataEntity.sqrStatus!=null)
                {
                    if(quoteDataEntity.sqrStatus.equalsIgnoreCase(Constants.rejected) || quoteDataEntity.sqrStatus.equalsIgnoreCase(Constants.new_status))
                    {
                        tvQuataion.setVisibility(View.VISIBLE);
                        tvApprove.setVisibility(View.GONE);
                        tvReject.setVisibility(View.GONE);
                    }
                    else
                    {
                        tvQuataion.setVisibility(View.GONE);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    hsqr_status != "approved" && hsqr_status!= "rejected"  && sqr_active_close == "active" (Show Approved button)

    hsqr_status != "rejected"  && sqr_active_close == "active" (Show reject button)




     */
    @OnClick(R.id.tv_quataion)
    public void sendquatuionbyseller() {
        try {
            final Dialog dialog = new Dialog(LeadDetails.this, R.style.Dialog_No_Border);
            dialog.setContentView(R.layout.send_quote_popup);

            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            dialog.getWindow().setAttributes(lp);

            final ImageView iv_back = dialog.findViewById(R.id.iv_back);
            final Button btn_sumbit = dialog.findViewById(R.id.btn_sumbit);
            final EditText et_quentity = dialog.findViewById(R.id.et_quentity);
            final EditText et_quentityprice = dialog.findViewById(R.id.et_quentityprice);
            final EditText et_sampleunit = dialog.findViewById(R.id.et_sampleunit);
            final EditText et_chargeunit = dialog.findViewById(R.id.et_chargeunit);
            final EditText et_time = dialog.findViewById(R.id.et_time);
            final EditText et_note = dialog.findViewById(R.id.et_note);
            final Spinner sp_sample = dialog.findViewById(R.id.sp_sample);
            final Spinner sp_charge = dialog.findViewById(R.id.sp_charge);
            sp_charge.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            sp_sample.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    ((TextView) parent.getChildAt(0)).setTextColor(Color.BLACK);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            iv_back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        dialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            btn_sumbit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (CommonFunctions.checkConnection(LeadDetails.this)) {
                            if (TextUtils.isEmpty(et_quentity.getText().toString())) {
                                Toast.makeText(LeadDetails.this, R.string.error_add_quote1, Toast.LENGTH_SHORT).show();
                                et_quentity.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(et_quentityprice.getText().toString())) {
                                Toast.makeText(LeadDetails.this, R.string.error_add_quote2, Toast.LENGTH_SHORT).show();
                                et_quentityprice.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(et_sampleunit.getText().toString())) {
                                Toast.makeText(LeadDetails.this, R.string.error_add_quote6, Toast.LENGTH_SHORT).show();
                                et_sampleunit.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(et_chargeunit.getText().toString())) {
                                Toast.makeText(LeadDetails.this, R.string.error_add_quote7, Toast.LENGTH_SHORT).show();
                                et_chargeunit.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(et_time.getText().toString())) {
                                Toast.makeText(LeadDetails.this, R.string.error_add_quote7, Toast.LENGTH_SHORT).show();
                                et_time.requestFocus();
                                return;
                            } else if (TextUtils.isEmpty(et_note.getText().toString())) {
                                Toast.makeText(LeadDetails.this, R.string.error_add_quote8, Toast.LENGTH_SHORT).show();
                                et_note.requestFocus();
                                return;
                            } else {
                                Map<String, String> mParams = new HashMap<>();
                                mParams.put(Constants.qty, et_quentity.getText().toString().trim());
                                mParams.put(Constants.price, et_quentityprice.getText().toString().trim());
                                mParams.put(Constants.sampleno, et_sampleunit.getText().toString().trim());
                                mParams.put(Constants.hassamplecharge, sp_charge.getSelectedItemPosition() == 0 ? "1" : "0");

                                mParams.put(Constants.samplecharge, sp_charge.getSelectedItemPosition() == 0 ? et_chargeunit.getText().toString().trim() : "0");

                                mParams.put(Constants.shippingtime, et_time.getText().toString().trim());
                                mParams.put(Constants.noteforcustomer, et_note.getText().toString().trim());
                                sendquote(mParams,dialog);
                            }


                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            dialog.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendquote(Map<String, String> mParams,Dialog dialog) {
        try {
            if (CommonFunctions.checkConnection(LeadDetails.this)) {
                CommonFunctions.createProgressBar(LeadDetails.this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(LeadDetails.this);
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.send_quote_by_seller + quoteid + "/" + CommonFunctions.getloginresponse(this).data.id;


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
                                    dialog.cancel();
                                    if (response.getBoolean(Constants.status)) {
                                        getCategory(quoteid);
                                    } else {
                                        Toast.makeText(LeadDetails.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    }

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(ANError anError) {
                                try {
                                    dialog.cancel();
                                    CommonFunctions.destroyProgressBar();
                                    Toast.makeText(LeadDetails.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
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


    @OnClick({R.id.tv_send, R.id.tv_approve, R.id.tv_reject})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_send:
                sendmessage();
                break;
            case R.id.tv_approve:
                sendaaprove(Constants.accept);
                break;
            case R.id.tv_reject:
                sendaaprove(Constants.reject);
                break;
        }
    }

    private void sendaaprove(String status) {
        try {
            if (CommonFunctions.checkConnection(LeadDetails.this)) {
                if (etSearch.getText().toString().length() == 0) {
                    Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
                } else {

                    int lastid = 0;
                    if (quoteDataEntity.sqrActiveClose.equalsIgnoreCase(Constants.active)) {
                        if (messagesEntityList != null && messagesEntityList.size() > 0) {
                            for (LeadDetailResp.MessagesEntity messagesEntity : messagesEntityList) {
                                if (messagesEntity.historySendQuoteRequestId != 0)
                                    lastid = messagesEntity.historySendQuoteRequestId;
                            }
                        }
                    } else {
                        Toast.makeText(this, R.string.error_quote10, Toast.LENGTH_SHORT).show();
                        return;
                    }

                    CommonFunctions.createProgressBar(LeadDetails.this, getString(R.string.msg_please_wait));
                    LoginResponse loginResponse = CommonFunctions.getloginresponse(LeadDetails.this);
                    String url = IndiaMartConfig.WEBURL + IndiaMartConfig.send_lead_request_by_seller + quoteid + "/" + lastid + "/" + CommonFunctions.getloginresponse(this).data.id;

                    Map<String, String> mParams = new HashMap<>();
                    mParams.put(Constants.action, status);
                    mParams.put(Constants.message, etSearch.getText().toString().trim());
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
                                        etSearch.setText("");
                                        CommonFunctions.hideSoftKeyboard(LeadDetails.this, etSearch);
                                        if (response.getBoolean(Constants.status)) {
                                            getCategory(quoteid);
                                        } else {
                                            Toast.makeText(LeadDetails.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    try {
                                        CommonFunctions.destroyProgressBar();
                                        Toast.makeText(LeadDetails.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendmessage() {
        try {
            if (CommonFunctions.checkConnection(LeadDetails.this)) {
                if (etSearch.getText().toString().length() == 0) {
                    Toast.makeText(this, R.string.error_message, Toast.LENGTH_SHORT).show();
                } else {
                    CommonFunctions.createProgressBar(LeadDetails.this, getString(R.string.msg_please_wait));
                    LoginResponse loginResponse = CommonFunctions.getloginresponse(LeadDetails.this);
                    String url = IndiaMartConfig.WEBURL + IndiaMartConfig.send_lead_request_by_seller + quoteid + "/0" + "/" + CommonFunctions.getloginresponse(this).data.id;

                    Map<String, String> mParams = new HashMap<>();
                    mParams.put(Constants.action, Constants.send_msg);
                    mParams.put(Constants.message, etSearch.getText().toString().trim());
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
                                        etSearch.setText("");
                                        CommonFunctions.hideSoftKeyboard(LeadDetails.this, etSearch);
                                        if (response.getBoolean(Constants.status)) {
                                            getCategory(quoteid);
                                        } else {
                                            Toast.makeText(LeadDetails.this, response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                        }

                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }

                                @Override
                                public void onError(ANError anError) {
                                    try {
                                        CommonFunctions.destroyProgressBar();
                                        Toast.makeText(LeadDetails.this, R.string.msg_server_error, Toast.LENGTH_SHORT).show();
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            });
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.iv_call)
    public void onViewClicked() {
        try {
            Dexter.withContext(this)
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {


                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+quoteDataEntity.custContactno));
                                startActivity(callIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(LeadDetails.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                            }

                        }

                        @Override
                        public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                        }

                        @Override
                        public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {

                        }

                    }).check();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}