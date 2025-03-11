package com.androidlesson.hackathon3project.presentation.authorization.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.androidlesson.hackathon3project.R;
import com.androidlesson.hackathon3project.databinding.FragmentRegistrationBinding;

public class RegistrationFragment extends Fragment {

    private FragmentRegistrationBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding=FragmentRegistrationBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }
}