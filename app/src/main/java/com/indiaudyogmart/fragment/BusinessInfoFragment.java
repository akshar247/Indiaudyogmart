package com.indiaudyogmart.fragment;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.Gson;
import com.indiaudyogmart.R;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LoginResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BusinessInfoFragment extends Fragment {

    View rootView;
    @BindView(R.id.iv_1)
    ImageView iv1;
    @BindView(R.id.iv_2)
    ImageView iv2;
    @BindView(R.id.tv_mart)
    TextView tvMart;
    @BindView(R.id.tv_des)
    TextView tvDes;
    @BindView(R.id.rl_2)
    RelativeLayout rl2;
    @BindView(R.id.tv_details)
    TextView tvDetails;
    @BindView(R.id.et_business_name)
    EditText etBusinessName;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.et_gst)
    EditText etGst;
    @BindView(R.id.fl_2)
    FrameLayout fl2;
    @BindView(R.id.et_business_address)
    EditText etBusinessAddress;
    @BindView(R.id.fl_3)
    FrameLayout fl3;
    @BindView(R.id.et_contact_per_name)
    EditText etContactPerName;
    @BindView(R.id.fl_4)
    FrameLayout fl4;
    @BindView(R.id.et_pancard)
    EditText et_pancard;
    @BindView(R.id.fl_5)
    FrameLayout fl5;
    @BindView(R.id.rl_3)
    RelativeLayout rl3;
    @BindView(R.id.btn_edit_profile)
    Button btnEditProfile;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_business_info, container, false);
        init();
        return rootView;
    }


    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            if (CommonFunctions.checkConnection(getActivity())) {
                getprofile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getprofile() {
        try {
            CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

            LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
            String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_user_data;
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
                        LoginResponse loginResponse1 = gson.fromJson(response.toString(), LoginResponse.class);
                        if (loginResponse1.status)
                        {
                            CommonFunctions.setPreference(getActivity(), Constants.userdata, gson.toJson(loginResponse1));
                            setdata();
                        }
                        else
                        {
                            setdata();
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
            etBusinessName.setText(CommonFunctions.getloginresponse(getActivity()).data.companyName + "");
            etGst.setText(CommonFunctions.getloginresponse(getActivity()).data.gstNo + "");
            etBusinessAddress.setText(CommonFunctions.getloginresponse(getActivity()).data.address + "");
            etContactPerName.setText(CommonFunctions.getloginresponse(getActivity()).data.name + "");
            et_pancard.setText(CommonFunctions.getloginresponse(getActivity()).data.panNo + "");

            etBusinessName.setEnabled(false);
            etGst.setEnabled(false);
            etContactPerName.setEnabled(false);
            et_pancard.setEnabled(false);

            btnEditProfile.setText(R.string.profile_label3);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @OnClick(R.id.btn_edit_profile)
    public void onViewClicked() {
        try {
            if(btnEditProfile.getText().toString().equalsIgnoreCase(getString(R.string.profile_label3)))
            {
                etBusinessName.setEnabled(true);
                etGst.setEnabled(true);
                etContactPerName.setEnabled(true);
                et_pancard.setEnabled(true);
                btnEditProfile.setText(R.string.saveprofile);
            }
            else
            {
                saveuserdata();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveuserdata() {
        try {
            if (etContactPerName.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), R.string.err_name, Toast.LENGTH_SHORT).show();
                etContactPerName.requestFocus();
                return;
            }

            else if (etBusinessName.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), R.string.err_businessname, Toast.LENGTH_SHORT).show();
                etBusinessName.requestFocus();
                return;
            }
            else if (etBusinessAddress.getText().toString().length() == 0)
            {
                Toast.makeText(getActivity(), R.string.err_businessaddress, Toast.LENGTH_SHORT).show();
                etBusinessAddress.requestFocus();
                return;
            }
            else if (etGst.getText().toString().length() == 0 )
            {

                Toast.makeText(getActivity(), R.string.err_gst, Toast.LENGTH_SHORT).show();
                etGst.requestFocus();
                return;
            }
            else if (et_pancard.getText().toString().length() == 0 )
            {

                Toast.makeText(getActivity(), R.string.err_pannumber, Toast.LENGTH_SHORT).show();
                et_pancard.requestFocus();
                return;
            }
            else
            {

                    CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));

                    LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                    String url = IndiaMartConfig.WEBURL + IndiaMartConfig.add_company_details;
                    Map<String, String> mParams = new HashMap<>();
                    //   mParams.put(Constants.contactno, tvNumber.getText().toString().trim());
                    //    mParams.put(Constants.email, tvEmail.getText().toString().trim());
                    mParams.put(Constants.company_name, etBusinessName.getText().toString().trim());
                    mParams.put(Constants.company_address, etBusinessAddress.getText().toString().trim());
                    mParams.put(Constants.gst_no, etGst.getText().toString().trim());
                    mParams.put(Constants.pan_no, et_pancard.getText().toString().trim());
                    Log.e("url", url);
                    Log.e("mParams", mParams.toString());

                    AndroidNetworking.post(url)
                            .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                            .addBodyParameter(mParams)
                            .setTag(url)
                            .setPriority(Priority.HIGH)
                            .build().getAsJSONObject(new JSONObjectRequestListener() {
                        @Override
                        public void onResponse(JSONObject response) {

                            try {
                                CommonFunctions.destroyProgressBar();
                                Log.e("res", response.toString());
                                getprofile();
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
}
