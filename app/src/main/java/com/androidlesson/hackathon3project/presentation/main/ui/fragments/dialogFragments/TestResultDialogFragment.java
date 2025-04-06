package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.DialogFragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.androidlesson.domain.main.models.HeroDataToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.adapters.AddImageAdapter;
import com.androidlesson.hackathon3project.presentation.main.ui.activities.TestActivity;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.addHeroFragmentViewModel.AddHeroFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModelFactory;
import com.bumptech.glide.Glide;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;


public class TestResultDialogFragment extends DialogFragment {

    private int score;
    private int total;

    private TextView resultText,closeButton;

    private TestActivityViewModel vm;
    @Inject
    TestActivityViewModelFactory vmFactory;

    public TestResultDialogFragment (int score, int total) {
        this.score = score;
        this.total = total;
    }

    @Nullable
    @Override
    public View onCreateView(
            @NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState
    ) {
        View view = inflater.inflate(R.layout.dialog_fragment_test_result, container, false);

        ((App) requireActivity().getApplication()).appComponent.injectTestResultDialogFragment(this);
        vm = new ViewModelProvider(requireActivity(), vmFactory).get(TestActivityViewModel.class);

        score=vm.getCorrectAnswersMutableLiveData().getValue();
        total=vm.getQuestionsMutableLiveData().getValue().size();

        resultText = view.findViewById(R.id.result_text);
        closeButton = view.findViewById(R.id.close_button);

        resultText.setText("Правильных ответов: " + score + " из " + total);

        if (total==10){
            closeButton.setText("Получить награду");
        }
        else closeButton.setText("Продолжить");

        closeButton.setOnClickListener(v -> {
            if (total==10){
                dismiss();
                if (score>=9) {
                    vm.addMedalToUser(vm.pointId/10);
                    ShowMedalDialogFragment resultDialog = new ShowMedalDialogFragment(vm.pointId/10);
                    resultDialog.setCancelable(false);
                    resultDialog.show(getParentFragmentManager(), "TestExit");
                }
                else {
                    requireActivity().finish();
                }
            }
            else {
                dismiss();
                requireActivity().finish();
            }
            vm.unlockTheNextPoint();

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

