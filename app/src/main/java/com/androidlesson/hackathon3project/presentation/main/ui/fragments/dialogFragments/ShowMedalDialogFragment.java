package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Dialog;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.androidlesson.domain.main.models.Medal;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.models.MedalsHolder;


public class ShowMedalDialogFragment extends DialogFragment {

    private TextView close_button,tv_medal_name,tv_medal_info;
    private ImageView iv_medal;

    private int medalId;

    public ShowMedalDialogFragment(int medalId) {
        this.medalId = medalId;
    }

    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.dialog_fragment_show_medal, container, false);
        close_button=view.findViewById(R.id.close_button);
        iv_medal=view.findViewById(R.id.iv_medal);
        tv_medal_name=view.findViewById(R.id.tv_medal_name);
        tv_medal_info=view.findViewById(R.id.tv_medal_info);

        Medal medal=new MedalsHolder().getMedalById(medalId);

        iv_medal.setImageResource(medal.getMedal_image());
        tv_medal_info.setText(medal.getInfo());
        tv_medal_name.setText(medal.getName());

        close_button.setOnClickListener(v->{
            dismiss();
            requireActivity().finish();
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

