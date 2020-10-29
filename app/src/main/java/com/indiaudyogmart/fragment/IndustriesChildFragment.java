package com.indiaudyogmart.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.IndustriesChildAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.IndustriesChildResponse;
import com.indiaudyogmart.model.LoginResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndustriesChildFragment extends Fragment {
   IndustriesChildResponse industriesChildResponse;
   IndustriesChildAdapter industriesChildAdapter;


    @BindView(R.id.industries_item)
    RecyclerView recyclerview;
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_industries_child, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            if (CommonFunctions.checkConnection(getActivity())) {
                if(getArguments()!= null && getArguments().containsKey(Constants.cat_id)){
                    String cate_id=getArguments().getString(Constants.cat_id);
                    getCategory(cate_id);

                }


            }

                recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));

    } catch (Exception e) {
        e.printStackTrace();
    }


    }


    private void getCategory(String cateID) {
        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.getCategoryChild_Mart+cateID;
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
                        industriesChildResponse = gson.fromJson(response.toString(), IndustriesChildResponse.class);
                        if (industriesChildResponse.status) {
                            setdata();
                        } else {
                            Toast.makeText(getActivity(), industriesChildResponse.message, Toast.LENGTH_SHORT).show();
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




        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void setdata() {
        try {
            if (industriesChildResponse.data != null) {
            industriesChildAdapter =new IndustriesChildAdapter(getActivity(), industriesChildResponse.data, new IndustriesChildAdapter.AdapterCallback() {
                @Override
                public void onSubcategoryClick(IndustriesChildResponse.DataEntity items) {

                    if (items.id != 0) {

                        ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                        Bundle bundle = new Bundle();
                        bundle.putString(Constants.cat_id, items.id + "");
                        productDetailFragment.setArguments(bundle);
                        ((DashboardActivity) getActivity()).addFragment(productDetailFragment);

                    }



                }

                @Override
                public void onSubcategoryViewAllClick(IndustriesChildResponse.DataEntity items) {

                }
            },"");
            recyclerview.setAdapter(industriesChildAdapter);
            }
        }         catch (Exception e) {
            e.printStackTrace();
        }
    }
}
