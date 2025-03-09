package com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.GetUserIdUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserDataUseCase;

public class SharedViewModelFactory implements ViewModelProvider.Factory {
    private final Application application;
    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;
    private final GetUserIdUseCase getUserIdUseCase;

    public SharedViewModelFactory(Application application, ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase, GetUserIdUseCase getUserIdUseCase) {
        this.application = application;
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;
        this.getUserIdUseCase = getUserIdUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(SharedViewModel.class)) {
            return (T) new SharedViewModel(application, observeCurrentUserDataUseCase, getUserIdUseCase);
        }
        throw new IllegalArgumentException("Unknown ViewModel class");
    }
}
