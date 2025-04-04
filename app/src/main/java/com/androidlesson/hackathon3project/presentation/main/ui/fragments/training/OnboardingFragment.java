package com.androidlesson.hackathon3project.presentation.main.ui.fragments.training;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.androidlesson.hackathon3project.R;
public class OnboardingFragment extends Fragment {

    private static final String ARG_IMAGE = "image";
    private static final String ARG_TEXT1 = "text1";
    private static final String ARG_TEXT2 = "text2";
    private static final String ARG_TEXT3 = "text3";
    private static final String ARG_IS_LAST_PAGE = "isLastPage";

    public static OnboardingFragment newInstance(int imageRes, String text1, String text2, @Nullable String text3, boolean isLastPage) {
        OnboardingFragment fragment = new OnboardingFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_IMAGE, imageRes);
        args.putString(ARG_TEXT1, text1);
        args.putString(ARG_TEXT2, text2);
        args.putBoolean(ARG_IS_LAST_PAGE, isLastPage);
        if (text3 != null) {
            args.putString(ARG_TEXT3, text3);
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_onboarding, container, false);

        ImageView imageView = view.findViewById(R.id.imageView);
        TextView textView1 = view.findViewById(R.id.textView1);
        TextView textView2 = view.findViewById(R.id.textView2);
        TextView textView3 = view.findViewById(R.id.textView3);
        ImageView buttonFinish = view.findViewById(R.id.b_iv_finish);

        if (getArguments() != null) {
            imageView.setImageResource(getArguments().getInt(ARG_IMAGE));
            textView1.setText(getArguments().getString(ARG_TEXT1));
            textView2.setText(getArguments().getString(ARG_TEXT2));
            String thirdText = getArguments().getString(ARG_TEXT3);
            if (thirdText != null) {
                textView3.setText(thirdText);
                textView3.setVisibility(View.VISIBLE);
            } else {
                textView3.setVisibility(View.INVISIBLE);
            }
            boolean isLastPage = getArguments().getBoolean(ARG_IS_LAST_PAGE);
            if (isLastPage) {
                buttonFinish.setVisibility(View.VISIBLE);
                buttonFinish.setOnClickListener(v -> getActivity().finish());
            } else {
                buttonFinish.setVisibility(View.GONE);
            }
        }

        return view;
    }
}