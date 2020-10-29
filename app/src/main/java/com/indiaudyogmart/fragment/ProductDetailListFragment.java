package com.indiaudyogmart.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
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
import com.indiaudyogmart.activity.QuoteDetails;
import com.indiaudyogmart.activity.QuotePopup;
import com.indiaudyogmart.adpter.ProductDetailListAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.ProductDetailResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProductDetailListFragment extends Fragment {
  ProductDetailResponse productDetailResponse;
  ProductDetailListAdapter productDetailListAdapter;



    @BindView(R.id.product_item)
    RecyclerView recyclerview;
    View rootView;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.product_detail_recycler, container, false);
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

                recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 1));

    } catch (Exception e) {
        e.printStackTrace();
    }


    }


    private void getCategory(String cateID) {
        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.getProduct_details+cateID;
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
                       productDetailResponse = gson.fromJson(response.toString(), ProductDetailResponse.class);
                        if (loginResponse.status) {
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
            if (productDetailResponse.data != null) {

                productDetailListAdapter=new ProductDetailListAdapter(getActivity(), productDetailResponse.data, new ProductDetailListAdapter.AdapterCallback() {
                    @Override
                    public void onSubcategoryClick(ProductDetailResponse.DataEntity items) {

                    }
                    @Override
                    public void oncall(ProductDetailResponse.DataEntity items) {
                        try {
                            calltoseller(items);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void submit(ProductDetailResponse.DataEntity items) {
                        Intent i=new Intent(getActivity(), QuotePopup.class);
                        i.putExtra(Constants.product_id,items.productId+"");
                        i.putExtra(Constants.prod_name,items.name+"");
                        i.putExtra(Constants.prod_unit,items.unitName);
                        i.putExtra(Constants.pro_pic,items.image+"");
                        i.putExtra(Constants.price,items.price+"");
                        i.putExtra(Constants.phone,items.sellerContactno+"");
                        startActivity(i);
                    }

                    @Override
                    public void detail(ProductDetailResponse.DataEntity items) {

                    }

                    @Override
                    public void onSubcategoryViewAllClick(ProductDetailResponse.DataEntity items) {

                    }


                },"");

                recyclerview.setAdapter(productDetailListAdapter);

            }
            }

            catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void calltoseller(ProductDetailResponse.DataEntity items) {

        try {
            Dexter.withContext(getActivity())
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {


                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+items.sellerContactno));
                                startActivity(callIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
                                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
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
