package com.androidlesson.hackathon3project.presentation.authorization.viewModels.setUserDataActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.SaveUserDataUseCase;

public class SetUserDataActivityViewModelFactory implements ViewModelProvider.Factory {
    private SaveUserDataUseCase saveUserDataUseCase;

    public SetUserDataActivityViewModelFactory(SaveUserDataUseCase saveUserDataUseCase) {
        this.saveUserDataUseCase = saveUserDataUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SetUserDataActivityViewModel(saveUserDataUseCase);
    }
}
