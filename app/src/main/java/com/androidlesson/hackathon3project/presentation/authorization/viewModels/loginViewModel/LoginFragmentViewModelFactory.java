package com.androidlesson.hackathon3project.presentation.authorization.viewModels.loginViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;

public class LoginFragmentViewModelFactory implements ViewModelProvider.Factory {
    private LoginUseCase loginUseCase;
    private SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase;

    public LoginFragmentViewModelFactory(LoginUseCase loginUseCase, SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        this.loginUseCase = loginUseCase;
        this.setAuthorizationDataToSPUseCase = setAuthorizationDataToSPUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new LoginFragmentViewModel(setAuthorizationDataToSPUseCase,loginUseCase);
    }
}
