package com.indiaudyogmart.activity;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.speech.RecognizerIntent;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.indiaudyogmart.R;
import com.indiaudyogmart.adpter.AutoCompleteAdapter;
import com.indiaudyogmart.config.CommonFunctions;
import com.indiaudyogmart.config.Constants;
import com.indiaudyogmart.config.IndiaMartConfig;
import com.indiaudyogmart.fragment.SearchFragment;
import com.indiaudyogmart.model.LoginResponse;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PostRequirments extends Fragment {

    String lang;
    @BindView(R.id.tv_requirement)
    TextView tvRequirement;
    @BindView(R.id.fl_1)
    FrameLayout fl1;
    @BindView(R.id.btn_next)
    Button btnNext;
    @BindView(R.id.iv33)
    ImageView micc;
    @BindView(R.id.et_search1)
    AutoCompleteTextView text;
    List<String> productsearch=new ArrayList<>();
    AutoCompleteAdapter autoSuggestAdapter;
    private static final int TRIGGER_AUTO_COMPLETE = 100;
    private static final long AUTO_COMPLETE_DELAY = 300;
    private Handler handler;
    View rootView;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.activity_post_requirments, container, false);
        init();
        return rootView;

    }
    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        if (CommonFunctions.checkConnection(getActivity())) {
                        if (TextUtils.isEmpty(text.getText().toString())) {
                            Toast.makeText(getActivity(),R.string.error_post,Toast.LENGTH_SHORT).show();
                            text.requestFocus();
                            return;
                        }
                        else
                        {
                            Intent next=new Intent(getActivity(),WantRequirment.class);
                            next.putExtra(Constants.name,text.getText().toString());
                            startActivity(next);
                        }
                    }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }
            });

            micc.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        openmic();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            text.setOnEditorActionListener(new AutoCompleteTextView.OnEditorActionListener() {
                public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                    if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                        Log.i("", "Here you can write the code");
                        try {
                            if(v.getText()!=null && v.getText().length()>0)
                            {
                                SearchFragment searchFragment=new SearchFragment();
                                Bundle bundle=new Bundle();
                                bundle.putString(Constants.search_term,v.getText().toString());
                                searchFragment.setArguments(bundle);
                                text.setText("");
                                CommonFunctions.hideSoftKeyboard(getActivity(),text);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        return true;
                    }
                    return false;
                }
            });

            autoSuggestAdapter = new AutoCompleteAdapter(getActivity(),
                    android.R.layout.simple_dropdown_item_1line);
            text.setThreshold(2);
            text.setAdapter(autoSuggestAdapter);
            text.setOnItemClickListener(
                    new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            text.setText(autoSuggestAdapter.getObject(position));
                        }
                    });

            text.addTextChangedListener(new TextWatcher() {

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    handler.removeMessages(TRIGGER_AUTO_COMPLETE);
                    handler.sendEmptyMessageDelayed(TRIGGER_AUTO_COMPLETE,
                            AUTO_COMPLETE_DELAY);

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count,
                                              int after) {
                    // TODO Auto-generated method stub
                }

                @Override
                public void afterTextChanged(Editable s) {
                    // TODO Auto-generated method stub
                }
            });
            handler = new Handler(new Handler.Callback() {
                @Override
                public boolean handleMessage(Message msg) {
                    if (msg.what == TRIGGER_AUTO_COMPLETE) {
                        if (!TextUtils.isEmpty(text.getText())) {
                            getdata(text.getText().toString());
                        }
                    }
                    return false;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void openmic() {
        try {
            Dexter.withContext(getActivity())
                    .withPermission(Manifest.permission.RECORD_AUDIO)
                    .withListener(new PermissionListener() {


                        @Override
                        public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                            try {


                                Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                                startActivityForResult(intent, Constants.RECOGNIZER_REQ_CODE);

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

    private void getdata(String search) {
        try {
            if(CommonFunctions.checkConnection(getActivity()))
            {
            //    CommonFunctions.createProgressBar(this, getString(R.string.msg_please_wait));
                LoginResponse loginResponse = CommonFunctions.getloginresponse(getActivity());
                String url = IndiaMartConfig.WEBURL + IndiaMartConfig.search_product_wyw+search;
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
                            if(response.getBoolean(Constants.status))
                            {
                                JSONArray data=response.getJSONArray(Constants.data);
                                productsearch.clear();
                                for(int i=0;i<data.length();i++)
                                {
                                    productsearch.add(data.getJSONObject(i).getString(Constants.name));
                                }
                                if(productsearch.size()>0)
                                {
                                    autoSuggestAdapter.setData(productsearch);
                                    autoSuggestAdapter.notifyDataSetChanged();
                                }
                            }
                            else
                            {

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


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case Constants.RECOGNIZER_REQ_CODE:

                if (resultCode == Activity.RESULT_OK && null != data) {


                    try {
                        String search_term = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS).get(0);
                        text.setText(search_term+"");


                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                }
                break;

        }
    }
}