package com.androidlesson.hackathon3project.presentation.main.viewModels.editUserProfileFragmentViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.main.useCase.EditUserDataUseCase;

public class EditUserProfileFragmentViewModelFactory implements ViewModelProvider.Factory {
    private EditUserDataUseCase editUserDataUseCase;
    private SaveUserDataToSPUseCase saveUserDataToSPUseCase;


    public EditUserProfileFragmentViewModelFactory(EditUserDataUseCase editUserDataUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase) {
        this.editUserDataUseCase = editUserDataUseCase;
        this.saveUserDataToSPUseCase = saveUserDataToSPUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EditUserProfileFragmentViewModel(editUserDataUseCase, saveUserDataToSPUseCase);
    }
}
