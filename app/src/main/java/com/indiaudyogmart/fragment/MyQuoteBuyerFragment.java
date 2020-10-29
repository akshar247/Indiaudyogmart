package com.indiaudyogmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.LeadDetails;
import com.indiaudyogmart.activity.QuoteDetails;
import com.indiaudyogmart.adpter.MyLeadSellerAdapter;
import com.indiaudyogmart.adpter.MyQuoteBuyerAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.MyLeadSellerResponse;
import com.indiaudyogmart.model.MyQuoteBuyerResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyQuoteBuyerFragment extends Fragment {
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.select_lead)
    RecyclerView lead;
    MyQuoteBuyerResponse myLeadSellerResponse;
    MyQuoteBuyerAdapter myLeadSellerAdapter;
    View rootView;
    @BindView(R.id.rl_back)
    RelativeLayout rlBack;
    @BindView(R.id.transactions)
    TextView transactions;
    @BindView(R.id.rl_actionbar)
    RelativeLayout rlActionbar;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_my_quote, container, false);
        init();
        return rootView;
    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            rlActionbar.setVisibility(View.GONE);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        getActivity().onBackPressed();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            if (CommonFunctions.checkConnection(getActivity())) {
                getMyLeadList();
            }
            lead.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMyLeadList() {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_quotes_list_by_customer;
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
                            myLeadSellerResponse = gson.fromJson(response.toString(), MyQuoteBuyerResponse.class);
                            if (myLeadSellerResponse.status) {
                                setdata();
                            } else {
                                Toast.makeText(getActivity(), myLeadSellerResponse.message, Toast.LENGTH_SHORT).show();
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
            if (myLeadSellerResponse.data != null) {
                myLeadSellerAdapter = new MyQuoteBuyerAdapter(getActivity(), myLeadSellerResponse.data, new MyQuoteBuyerAdapter.AdapterCallback() {

                    @Override
                    public void onSubcategoryClick(MyQuoteBuyerResponse.DataEntity items) {
                        try {
                            Intent i = new Intent(getActivity(), QuoteDetails.class);
                            i.putExtra(Constants.id, items.send_quote_request_id+"");


                            getActivity().startActivity(i);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(MyQuoteBuyerResponse.DataEntity items) {

                    }
                }, "");
                lead.setAdapter(myLeadSellerAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}