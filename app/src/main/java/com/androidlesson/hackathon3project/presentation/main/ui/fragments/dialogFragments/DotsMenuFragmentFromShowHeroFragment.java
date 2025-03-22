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
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentManager;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.presentation.main.interfaces.OnDataPass;


public class DotsMenuFragmentFromShowHeroFragment extends DialogFragment {

    private LinearLayout ll_edit,ll_delete;

    private String heroId;

    public DotsMenuFragmentFromShowHeroFragment(String heroId) {
        this.heroId = heroId;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_fragment_dots_menu_from_show_hero_fragment, container, false);
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
                params.gravity = Gravity.TOP | Gravity.END;
                params.x = 20;
                params.y = 150;
                window.setAttributes(params);
            }
        }
    }

    private void initialization(View view){


        ll_edit=view.findViewById(R.id.ll_edit_data);
        ll_delete=view.findViewById(R.id.ll_delete);
    }

    private void setOnClickListener(){
        ll_delete.setOnClickListener(v->{
            FragmentManager fragmentManager = getParentFragmentManager();
            DeleteHeroDialogFragment dialogFragment = new DeleteHeroDialogFragment(heroId);
            dialogFragment.show(fragmentManager, "my_dialog");
            dismiss();
        });

        ll_edit.setOnClickListener(v->{

        });
    }
}

