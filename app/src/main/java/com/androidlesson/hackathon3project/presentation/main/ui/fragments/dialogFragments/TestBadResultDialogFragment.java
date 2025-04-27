package com.androidlesson.hackathon3project.presentation.main.ui.fragments.dialogFragments;

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
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.app.App;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModel;
import com.androidlesson.hackathon3project.presentation.main.viewModels.testActivityViewModel.TestActivityViewModelFactory;

import javax.inject.Inject;


public class TestBadResultDialogFragment extends DialogFragment {

    private int score;
    private int total;

    private TextView resultText,closeButton;

    private TestActivityViewModel vm;
    @Inject
    TestActivityViewModelFactory vmFactory;

    public TestBadResultDialogFragment(int score, int total) {
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
        View view = inflater.inflate(R.layout.dialog_fragment_test_bad_result, container, false);

        ((App) requireActivity().getApplication()).appComponent.injectTestBadResultDialogFragment(this);
        vm = new ViewModelProvider(requireActivity(), vmFactory).get(TestActivityViewModel.class);

        score=vm.getCorrectAnswersMutableLiveData().getValue();
        total=vm.getQuestionsMutableLiveData().getValue().size();

        resultText = view.findViewById(R.id.result_text);
        closeButton = view.findViewById(R.id.close_button);

        resultText.setText("Правильных ответов: " + score + " из " + total);

        closeButton.setOnClickListener(v -> {
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

