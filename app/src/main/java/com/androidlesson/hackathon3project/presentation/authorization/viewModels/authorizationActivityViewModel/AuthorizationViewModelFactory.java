package com.androidlesson.hackathon3project.presentation.authorization.viewModels.authorizationActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;


public class AuthorizationViewModelFactory implements ViewModelProvider.Factory {
    private LoginUseCase loginUseCase;
    private RegistrationUseCase registrationUseCase;
    private SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase;

    public AuthorizationViewModelFactory(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        this.loginUseCase = loginUseCase;
        this.registrationUseCase = registrationUseCase;
        this.setAuthorizationDataToSPUseCase = setAuthorizationDataToSPUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new AuthorizationViewModel(loginUseCase,registrationUseCase,setAuthorizationDataToSPUseCase);
    }
}
