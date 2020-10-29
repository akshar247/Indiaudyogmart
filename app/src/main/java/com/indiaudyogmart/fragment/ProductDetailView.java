package com.indiaudyogmart.fragment;

import android.Manifest;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.activity.QuotePopup;
import com.indiaudyogmart.adpter.ProductPageFlipperAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.dashboard.DashboardActivity;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.ProductResponse;
import com.indiaudyogmart.utils.AutoScrollViewPager;
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
import butterknife.OnClick;

public class ProductDetailView extends Fragment {
    View rootView;
    private static final int AUTO_SCROLL_THRESHOLD_IN_MILLI = 10000;
    ProductPageFlipperAdapter productPageFlipperAdapter;
    ProductResponse productDetailResponse;
    @BindView(R.id.home_top_ViewPager)
    AutoScrollViewPager homeTopViewPager;
    @BindView(R.id.rel1)
    RelativeLayout rel1;
    @BindView(R.id.product_name)
    TextView productName;
    @BindView(R.id.iv_brand)
    ImageView ivBrand;
    @BindView(R.id.tv_compney_name)
    TextView tvCompneyName;
    @BindView(R.id.rel_2)
    RelativeLayout rel2;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.tv_loaction)
    TextView tvLoaction;
    @BindView(R.id.rel_3)
    RelativeLayout rel3;
    @BindView(R.id.iv_available)
    ImageView ivAvailable;
    @BindView(R.id.tv_available_location)
    TextView tvAvailableLocation;
    @BindView(R.id.product_description)
    TextView product_description;

    @BindView(R.id.rel_4)
    RelativeLayout rel4;

    @BindView(R.id.tv_price)
    TextView tvPrice;
    @BindView(R.id.tv_price1)
    TextView     tv_price1;
    @BindView(R.id.rel_5)
    RelativeLayout rel5;
    @BindView(R.id.ll_varinet)
    LinearLayout llVarinet;
    @BindView(R.id.btn_callnow)
    Button btnCallnow;
    @BindView(R.id.btn_get_price)
    Button btnGetPrice;
    @BindView(R.id.rel_6)
    RelativeLayout rel6;
    @BindView(R.id.product_cardview)
    CardView productCardview;
    @BindView(R.id.tv_contact)
    TextView phone;

    String productid;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_product_detail_view, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);

            productCardview.setVisibility(View.GONE);
            if (getArguments() != null && getArguments().containsKey(Constants.product_id)) {
                productid = getArguments().getString(Constants.product_id);
                getproductdetails(productid);
            }

            if (CommonFunctions.getloginresponse(getActivity()).data.roleid == 1)
            {
                btnCallnow.setVisibility(View.VISIBLE);
                btnGetPrice.setVisibility(View.VISIBLE);
            }
            else
              {
                  btnCallnow.setVisibility(View.GONE);
                  btnGetPrice.setVisibility(View.GONE);
               }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getproductdetails(String id) {

        try {

            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_products_details + id;
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
                        productDetailResponse = gson.fromJson(response.toString(), ProductResponse.class);
                        if (productDetailResponse.status) {
                            setData();
                        } else {
                            Toast.makeText(getActivity(), productDetailResponse.message, Toast.LENGTH_SHORT).show();
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

    private void setData() {
        try {
            productCardview.setVisibility(View.VISIBLE);
            productName.setText(productDetailResponse.data.name + "");
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                product_description.setText(Html.fromHtml(productDetailResponse.data.detail + "", Html.FROM_HTML_MODE_COMPACT));
            } else {
                product_description.setText(Html.fromHtml(productDetailResponse.data.detail + ""));
            }


            tvCompneyName.setText(productDetailResponse.data.sellerCompanyName + "");
            phone.setText(productDetailResponse.data.sellerContactno + "");

            if(productDetailResponse.data.sellerCompanyLocation!=null)
            tvAvailableLocation.setText(productDetailResponse.data.sellerCompanyLocation + "");
            else
                tvAvailableLocation.setText( "");

            float per=((productDetailResponse.data.originalPrice-productDetailResponse.data.price)*100)/productDetailResponse.data.originalPrice;

            tvPrice.setText(getString(R.string.rs_val)+ " "+productDetailResponse.data.price + "  " +per+" "+getString(R.string.discount_val));
            tv_price1.setText(getString(R.string.rs_val)+ " "+productDetailResponse.data.originalPrice );
            //image.setImageResource(productDetailResponse.data.get(0).image+"");
            if (productDetailResponse.data.productImages != null) {
                productPageFlipperAdapter = new ProductPageFlipperAdapter(getActivity(), productDetailResponse.data.productImages);
                homeTopViewPager.setAdapter(productPageFlipperAdapter);

                homeTopViewPager.startAutoScroll();
                homeTopViewPager.setInterval(AUTO_SCROLL_THRESHOLD_IN_MILLI);
                // enable recycling using true
                homeTopViewPager.setCycle(true);
            }

            if (productDetailResponse.data.productVariantOptions != null && productDetailResponse.data.productVariantOptions.size() > 0) {
                for (int i = 0; i < productDetailResponse.data.productVariantOptions.size(); i++) {
                    View specification = LayoutInflater.from(getActivity()).inflate(R.layout.item_specification, null, false);

                    TextView tv_title = specification.findViewById(R.id.tv_title);
                    TextView tv_descipton = specification.findViewById(R.id.tv_descipton);
                    tv_title.setText(productDetailResponse.data.productVariantOptions.get(i).variantName);
                    tv_descipton.setText(productDetailResponse.data.productVariantOptions.get(i).name);
                    llVarinet.addView(specification);
                }

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    @OnClick({R.id.btn_callnow, R.id.btn_get_price})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_callnow:
                calltoseller(productDetailResponse);
                break;
            case R.id.btn_get_price:
                try {
                    if (CommonFunctions.getloginresponse(getActivity()).data.roleid == 1)
                    {
                        Intent i=new Intent(getActivity(), QuotePopup.class);
                        i.putExtra(Constants.product_id,productDetailResponse.data.productId+"");
                        i.putExtra(Constants.prod_name,productDetailResponse.data.name+"");
                        i.putExtra(Constants.prod_unit,productDetailResponse.data.unitName);
                        i.putExtra(Constants.pro_pic,productDetailResponse.data.image+"");
                        i.putExtra(Constants.price,productDetailResponse.data.price+"");
                        i.putExtra(Constants.price,productDetailResponse.data.sellerContactno+"");
                        startActivity(i);
                    }
                   /* Intent i=new Intent(getActivity(), QuotePopup.class);
                    i.putExtra(Constants.product_id,productDetailResponse.data.productId+"");
                    i.putExtra(Constants.prod_name,productDetailResponse.data.name+"");
                    i.putExtra(Constants.prod_unit,productDetailResponse.data.unitName);
                    i.putExtra(Constants.pro_pic,productDetailResponse.data.image+"");
                    i.putExtra(Constants.price,productDetailResponse.data.price+"");
                    startActivity(i);*/
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
        }
    }

    private void calltoseller(ProductResponse productDetailResponse) {
        try {
            Dexter.withContext(getActivity())
                    .withPermission(Manifest.permission.CALL_PHONE)
                    .withListener(new PermissionListener() {


                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            try {
                                Intent callIntent = new Intent(Intent.ACTION_CALL);
                                callIntent.setData(Uri.parse("tel:"+productDetailResponse.data.sellerContactno));
                                startActivity(callIntent);
                            } catch (Exception e) {
                                e.printStackTrace();
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