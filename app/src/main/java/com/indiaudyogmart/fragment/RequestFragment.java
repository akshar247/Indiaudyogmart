package com.indiaudyogmart.fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.indiaudyogmart.adpter.RequestAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.model.LoginResponse;
import com.indiaudyogmart.model.RequestResponse;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RequestFragment extends Fragment {

    @BindView(R.id.select_request)
    RecyclerView lead;
    @BindView(R.id.iv_back)
    ImageView back;
    RequestResponse requestResponse;
    RequestAdapter requestAdapter;
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
        rootView = inflater.inflate(R.layout.fragment_request, container, false);
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
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.get_wyw_data;
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
                            requestResponse = gson.fromJson(response.toString(), RequestResponse.class);
                            if (requestResponse.status) {
                                setdata();
                            } else {
                                Toast.makeText(getActivity(), requestResponse.message, Toast.LENGTH_SHORT).show();
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
            if (requestResponse.data != null) {
                requestAdapter = new RequestAdapter(getActivity(), requestResponse.data, new RequestAdapter.AdapterCallback() {

                    @Override
                    public void onSubcategoryClick(RequestResponse.DataEntity items) {
                        try {
                         /*   Intent i = new Intent(getActivity(), SendDialogFragment.class);
                            i.putExtra(Constants.id, items.sellerId+"");
                            getActivity().startActivity(i);*/
                            openfeedbackdiologue(items.id);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onSubcategoryViewAllClick(RequestResponse.DataEntity items) {

                    }
                }, "");
                lead.setAdapter(requestAdapter);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openfeedbackdiologue(int id) {
        try {
            final Dialog dialog = new Dialog(getActivity(), R.style.Dialog_No_Border);
            dialog.setContentView(R.layout.send_dialog);
            final ImageView iv_cloase = dialog.findViewById(R.id.close);
            final Button finish = dialog.findViewById(R.id.btn_finish);
            final EditText edtcomment = dialog.findViewById(R.id.edt_feedback);
            dialog.setCancelable(true);
            dialog.setCanceledOnTouchOutside(false);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.copyFrom(dialog.getWindow().getAttributes());
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            DisplayMetrics displayMetrics = getResources().getDisplayMetrics();
            int width = displayMetrics.widthPixels;
            int height = displayMetrics.heightPixels;
            dialog.getWindow().setAttributes(lp);
            iv_cloase.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        dialog.cancel();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            finish.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    try {
                        if (edtcomment.getText().toString().trim().length() == 0) {
                            Toast.makeText(getActivity(), getString(R.string.err_message), Toast.LENGTH_SHORT).show();
                        } else {
                            sendcomment(edtcomment.getText().toString().trim(),id,dialog);
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

    private void sendcomment(String send, int id, Dialog dialog) {
        try {
            if (CommonFunctions.checkConnection(getActivity())) {
                CommonFunctions.createProgressBar(getActivity(), getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.update_wyw+id;
                Map<String, String> mParams = new HashMap<>();
                mParams.put(Constants.message, send);
                Log.e("url", url);
                Log.e("mParams", mParams.toString());
                AndroidNetworking.post(url)
                        .addHeaders(Constants.headerkey, loginResponse.data.encryptUserId)
                        .setTag(url)
                        .setPriority(Priority.HIGH)
                        .addPathParameter(mParams)
                        .addBodyParameter(mParams)
                        .build()
                        .getAsJSONObject(new JSONObjectRequestListener() {
                            @Override
                            public void onResponse(JSONObject response) {
                                try {
                                    CommonFunctions.destroyProgressBar();
                                    dialog.cancel();
                                    Log.e("res", response.toString());
                                    if (response.getBoolean(Constants.status)) {
                                        Toast.makeText(getActivity(), response.getString(Constants.message), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(getActivity(), response.getString(Constants.message), Toast.LENGTH_SHORT).show();
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
}