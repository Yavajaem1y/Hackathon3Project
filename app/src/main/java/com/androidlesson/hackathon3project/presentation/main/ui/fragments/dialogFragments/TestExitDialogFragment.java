package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.androidlesson.hackathon3project.R;


public class TestExitDialogFragment extends DialogFragment {

    private TextView b_exit,b_cancellation;

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.dialog_fragment_test_exit, container, false);
        b_exit=view.findViewById(R.id.b_exit);
        b_cancellation=view.findViewById(R.id.b_cancellation);

        b_exit.setOnClickListener(v->{
            dismiss();
            requireActivity().finish();
        });
        b_cancellation.setOnClickListener(v->{
            dismiss();
        });

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getDialog() != null) {
            Dialog dialog = getDialog();
            Window window = dialog.getWindow();
            if (window != null) {
                window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                window.setBackgroundDrawableResource(android.R.color.transparent);

                WindowManager.LayoutParams params = window.getAttributes();
                params.gravity = Gravity.CENTER;
                window.setAttributes(params);
            }
        }
    }
}

