package com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.androidlesson.domain.authorization.useCase.CheckUserAuthorizationUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromFBUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromSPUseCase;
import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.main.useCase.LogOutUseCase;

public class MainActivityViewModelFactory implements ViewModelProvider.Factory {

    private CheckUserAuthorizationUseCase checkUserAuthorizationUseCase;
    private CheckUserDataFromSPUseCase checkUserDataFromSPUseCase;
    private CheckUserDataFromFBUseCase checkUserDataFromFBUseCase;
    private SaveUserDataToSPUseCase saveUserDataToSPUseCase;
    private final LogOutUseCase logOutUseCase;

    public MainActivityViewModelFactory(LogOutUseCase logOutUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase, CheckUserDataFromFBUseCase checkUserDataFromFBUseCase, CheckUserDataFromSPUseCase checkUserDataFromSPUseCase, CheckUserAuthorizationUseCase checkUserAuthorizationUseCase) {
        this.logOutUseCase = logOutUseCase;
        this.saveUserDataToSPUseCase = saveUserDataToSPUseCase;
        this.checkUserDataFromFBUseCase = checkUserDataFromFBUseCase;
        this.checkUserDataFromSPUseCase = checkUserDataFromSPUseCase;
        this.checkUserAuthorizationUseCase = checkUserAuthorizationUseCase;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new MainActivityViewModel(checkUserAuthorizationUseCase,checkUserDataFromSPUseCase,checkUserDataFromFBUseCase,saveUserDataToSPUseCase,logOutUseCase);
    }
}
