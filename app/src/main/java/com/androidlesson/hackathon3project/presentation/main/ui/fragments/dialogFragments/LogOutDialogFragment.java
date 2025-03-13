package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.interfaces.OnDataPass;


public class LogOutDialogFragment extends DialogFragment {

    private OnDataPass onDataPass;

    private TextView b_cancellation, b_logout;

    @Override
    public void onAttach(@NonNull Activity activity) {
        super.onAttach(activity);
        onDataPass=(OnDataPass) activity;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_logout, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        initialization(view);

        setOnClickListener();

        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Dialog dialog = getDialog();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);

                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        }
    }


    private void initialization(View view){


        b_cancellation=view.findViewById(R.id.b_cancellation);
        b_logout =view.findViewById(R.id.b_log_out);
    }

    private void setOnClickListener(){
        b_logout.setOnClickListener(v->{
            onDataPass.onDataPass("sda");
            dismiss();
        });

        b_cancellation.setOnClickListener(v->{
            dismiss();
        });
    }
}

