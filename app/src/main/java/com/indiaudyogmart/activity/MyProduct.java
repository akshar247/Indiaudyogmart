package com.indiaudyogmart.activity;

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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.MyLeadSellerAdapter;
import com.indiaudyogmart.adpter.MyProductAdapter;
import com.indiaudyogmart.adpter.SupplierAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.fragment.ProductDetailFragment;
import com.indiaudyogmart.fragment.ProductDetailView;
import com.indiaudyogmart.model.ImportantSupplierResponse;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.MyLeadSellerResponse;
import com.indiaudyogmart.model.MyProductResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyProduct extends Fragment {

    @BindView(R.id.select_product)
    RecyclerView product;
    @BindView(R.id.iv_back)
    ImageView back;
    @BindView(R.id.tv_addproduct)
    TextView tv_addproduct;
    String lang;
    MyProductResponse myProductResponse;
    MyProductAdapter myProductAdapter;
    View rootView;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_my_product, container, false);
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

                    getMyProductList();


            product.setLayoutManager(new LinearLayoutManager(getActivity()));

            tv_addproduct.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {

                        WebViewActivity webViewActivity=new WebViewActivity();
                        Bundle bundle=new Bundle();
                        webViewActivity.setArguments(bundle);
                        String url=IndiaMartConfig.app_addproduct+CommonFunctions.getloginresponse(getActivity()).data.id;
                        bundle.putString(Constants.url,url);
                        ((DashboardActivity)getActivity()).addFragment(webViewActivity);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getMyProductList() {
        try {
            if( CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_products_by_seller+CommonFunctions.getloginresponse(getActivity()).data.id;
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
                            myProductResponse = gson.fromJson(response.toString(), MyProductResponse.class);
                            if (myProductResponse.status) {
                                setdata();
                            } else {
                                Toast.makeText(getActivity(), myProductResponse.message, Toast.LENGTH_SHORT).show();
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
            if (myProductResponse.data != null) {
                myProductAdapter=new MyProductAdapter(getActivity(), myProductResponse.data, new MyProductAdapter.AdapterCallback() {


                    @Override
                    public void onSubcategoryClick(MyProductResponse.DataEntity items) {
                        try {
                            ProductDetailView productDetailFragment = new ProductDetailView();
                            Bundle bundle = new Bundle();
                            if(items.productId==null)
                            bundle.putString(Constants.product_id, items.id + "");
                            else
                                bundle.putString(Constants.product_id, items.productId + "");
                            productDetailFragment.setArguments(bundle);
                            ((DashboardActivity) getActivity()).addFragment(productDetailFragment);


                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(MyProductResponse.DataEntity items) {

                    }

                    @Override
                    public void editproduct(MyProductResponse.DataEntity items) {
                        try {
                            WebViewActivity webViewActivity=new WebViewActivity();
                            Bundle bundle=new Bundle();
                            String prodid="";
                            if(items.productId==null)
                                prodid= items.id+"";
                            else
                                prodid= items.productId;
                            String url=IndiaMartConfig.app_mart_product_edit+CommonFunctions.getloginresponse(getActivity()).data.id+"/"+prodid;
                            bundle.putString(Constants.url,url);
                            webViewActivity.setArguments(bundle);
                            ((DashboardActivity)getActivity()).addFragment(webViewActivity);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                },"");
                product.setAdapter(myProductAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}