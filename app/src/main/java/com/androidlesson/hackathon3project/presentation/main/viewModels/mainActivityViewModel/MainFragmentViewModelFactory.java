package com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.main.useCase.LogOutUseCase;

public class MainFragmentViewModelFactory implements ViewModelProvider.Factory {

    public MainFragmentViewModelFactory() {
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainFragmentViewModel();
    }
}
