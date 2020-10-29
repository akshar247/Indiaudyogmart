package com.indiaudyogmart.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;


import com.indiaudyogmart.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedbackDialogFragment extends DialogFragment {
    View rootView;
@BindView(R.id.close)
ImageView close;


    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.feedback_dialog, container, false);
        init();
        return rootView;

    }

    private void init() {
        try {
            ButterKnife.bind(this, rootView);
            close.setOnClickListener(new View.OnClickListener() {

                public void onClick(View view) {
                    Toast.makeText(getActivity(),"hii",Toast.LENGTH_SHORT).show();
                }
            });


        } catch (Exception e) {
            e.printStackTrace();
        }
    }



}
