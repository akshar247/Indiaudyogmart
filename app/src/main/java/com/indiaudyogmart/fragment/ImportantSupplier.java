package com.indiaudyogmart.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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
import com.indiaudyogmart.adpter.SupplierAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.ImportantSupplierResponse;
import com.indiaudyogmart.model.IndustriesResponse;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.SupplierItem;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ImportantSupplier extends Fragment {

    List<SupplierItem> lstOffer;
    @BindView(R.id.select_supplier)
    RecyclerView recyclerview1;
    @BindView(R.id.iv_close)
    ImageView imageView;
    ImportantSupplierResponse importantSupplierResponse;
    SupplierAdapter supplierAdapter;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_important_supplier, container, false);
        init();
        return rootView;
    }

   

    private void init() {
        try {
            ButterKnife.bind(this,rootView);
            if (CommonFunctions.checkConnection(getActivity())) {
                getSupplierList();
            }
          recyclerview1.setLayoutManager(new LinearLayoutManager(getActivity()));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getSupplierList() {
        try {
           if( CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
               LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
               String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_important_suppliers_list;
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
                           importantSupplierResponse = gson.fromJson(response.toString(), ImportantSupplierResponse.class);
                           if (importantSupplierResponse.status) {
                               setdata();
                           } else {
                               Toast.makeText(getActivity(), importantSupplierResponse.message, Toast.LENGTH_SHORT).show();
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
               imageView.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {

                       getActivity().onBackPressed();
                   }
               });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setdata() {
        try {
            if (importantSupplierResponse.data != null) {
                supplierAdapter=new SupplierAdapter(getActivity(), importantSupplierResponse.data, new SupplierAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(ImportantSupplierResponse.DataEntity items) {
                        try {

                        if(CommonFunctions.checkConnection(getActivity()))
                            getgategory(items.categoryId+"");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(ImportantSupplierResponse.DataEntity items) {

                    }
                });
                recyclerview1.setAdapter(supplierAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void getgategory(String cateid) {
        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.getCategoryChild_Mart+cateid;
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
                        IndustriesResponse industriesResponse = gson.fromJson(response.toString(), IndustriesResponse.class);
                        if (industriesResponse.status)
                        {
                            IndustriesChildFragment industriesChildFragment=new IndustriesChildFragment();
                            Bundle bundle=new Bundle();
                            bundle.putString(Constants.cat_id, cateid +"");
                            industriesChildFragment.setArguments(bundle);
                            ((DashboardActivity)getActivity()).addFragment(industriesChildFragment);
                        }
                        else
                        {
                            ProductDetailFragment productDetailFragment = new ProductDetailFragment();
                            Bundle bundle = new Bundle();
                            bundle.putString(Constants.cat_id, cateid + "");
                            productDetailFragment.setArguments(bundle);
                            ((DashboardActivity) getActivity()).addFragment(productDetailFragment);
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


        }catch (Exception e) {
            e.printStackTrace();
        }
    }




}