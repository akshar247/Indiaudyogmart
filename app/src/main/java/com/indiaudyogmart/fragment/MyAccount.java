package com.indiaudyogmart.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.MyLead;
import com.indiaudyogmart.activity.MyOrderSeller;
import com.indiaudyogmart.activity.MyProduct;
import com.indiaudyogmart.activity.MyReviews;
import com.indiaudyogmart.activity.MyTransaction;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.MyAccountResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MyAccount extends Fragment {

    @BindView(R.id.myproduct)
    TextView myproduct;
    @BindView(R.id.number2)
    TextView myorder;
    @BindView(R.id.number4)
    TextView myreviews;
    @BindView(R.id.number5)
    TextView mytransactions;
    @BindView(R.id.number6)
    TextView myleads;
    @BindView(R.id.iv_back)
    ImageView back;
    MyAccountResponse myAccountResponse;

    View rootView;
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_my_account, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this,rootView);
            back.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                  getActivity().onBackPressed();
                }
            });
            if(CommonFunctions.checkConnection(getActivity()));
            {
                getAccountList();
            }



        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private void getAccountList() {
        try {
            if( CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_home_page_by_seller;
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
                            myAccountResponse = gson.fromJson(response.toString(), MyAccountResponse.class);
                            if (myAccountResponse.status) {

                                setdata();
                            } else {
                                Toast.makeText(getActivity(), myAccountResponse.message, Toast.LENGTH_SHORT).show();
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
            myproduct.setText(myAccountResponse.data.myProducts+"");
            myorder.setText(myAccountResponse.data.myOrders+"");
            myleads.setText(myAccountResponse.data.myLead+"");
            myreviews.setText(myAccountResponse.data.myReviews+"");
            mytransactions.setText(myAccountResponse.data.myTansactions+"");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick({R.id.rl_1, R.id.rl_2, R.id.rl_6, R.id.rl_4, R.id.rl_5})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_1:
                ((DashboardActivity)getActivity()).addFragment(new MyProduct());
                break;
            case R.id.rl_2:
               Intent order=new Intent(getActivity(), MyOrderSeller.class);
                startActivity(order);
                break;
            case R.id.rl_4:
                Intent review=new Intent(getActivity(), MyReviews.class);
                startActivity(review);
                break;
            case R.id.rl_5:
                Intent trascation=new Intent(getActivity(), MyTransaction.class);
                startActivity(trascation);
                break;
            case R.id.rl_6:
                ((DashboardActivity)getActivity()).addFragment(new MyLeadSellerFragment());
                break;

        }
    }


}