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
import com.indiaudyogmart.adpter.IndustriesAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.IndustriesResponse;
import com.indiaudyogmart.model.LoginResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IndustriesFragment extends Fragment {
   IndustriesResponse industriesResponse;
   IndustriesAdapter industriesAdapter;
    @BindView(R.id.industries_item)
    RecyclerView recyclerview;
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_industries, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            if (CommonFunctions.checkConnection(getActivity())) {
                getCategory();

            }

                recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 3));

    } catch (Exception e) {
        e.printStackTrace();
    }


    }

    private void getCategory() {
        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.getCategory_Mart;
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
                        industriesResponse = gson.fromJson(response.toString(), IndustriesResponse.class);
                        if (industriesResponse.status) {
                            setdata();
                        } else {
                            Toast.makeText(getActivity(), loginResponse.message, Toast.LENGTH_SHORT).show();
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

            if (industriesResponse.data!= null) {

                industriesAdapter=new IndustriesAdapter(getActivity(), industriesResponse.data, new IndustriesAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(IndustriesResponse.DataEntity items) {

                        if(items.childs!=null && items.childs.size() > 0){

                            IndustriesChildFragment industriesChildFragment=new IndustriesChildFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString(Constants.cat_id,items.id+"");
                            industriesChildFragment.setArguments(bundle);
                            ((DashboardActivity)getActivity()).addFragment(industriesChildFragment);

                        }

                    }

                    @Override
                    public void onSubcategoryViewAllClick(IndustriesResponse.DataEntity items) {

                    }
                },"");
                recyclerview.setAdapter(industriesAdapter);
            }

            } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
