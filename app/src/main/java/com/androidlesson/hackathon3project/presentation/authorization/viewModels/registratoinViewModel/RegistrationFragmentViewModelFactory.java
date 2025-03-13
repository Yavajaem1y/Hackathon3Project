package com.androidlesson.hackathon3project.presentation.authorization.viewModels.registratoinViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;

public class RegistrationFragmentViewModelFactory implements ViewModelProvider.Factory {
    private RegistrationUseCase registrationUseCase;
    private SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase;

    public RegistrationFragmentViewModelFactory(RegistrationUseCase registrationUseCase, SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        this.registrationUseCase = registrationUseCase;
        this.setAuthorizationDataToSPUseCase = setAuthorizationDataToSPUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RegistrationFragmentViewModel(setAuthorizationDataToSPUseCase, registrationUseCase);
    }
}
