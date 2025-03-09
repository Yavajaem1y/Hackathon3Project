package com.androidlesson.hackathon3project.di;

import android.app.Application;
import android.content.Context;

import com.androidlesson.domain.authorization.useCase.CheckUserAuthorizationUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromFBUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromSPUseCase;
import com.androidlesson.domain.authorization.useCase.GetUserIdUseCase;
import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.authorization.useCase.SaveUserDataUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;
import com.androidlesson.domain.main.useCase.LogOutUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserDataUseCase;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.userSessionCheckerActivityViewModel.UserSessionCheckerActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.authorizationActivityViewModel.AuthorizationViewModelFactory;
import com.androidlesson.hackathon3project.presentation.authorization.viewModels.setUserDataActivityViewModel.SetUserDataActivityViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.mainActivityViewModel.MainFragmentViewModelFactory;
import com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel.SharedViewModelFactory;

import dagger.Module;
import dagger.Provides;

@Module
public class AppModule {
    private final Application application;

    public AppModule(Application application) {
        this.application = application;
    }

    @Provides
    public Application provideApplication() {
        return application;
    }

    @Provides
    public Context provideContext() {
        return application.getApplicationContext(); // ✅ Теперь Dagger дает глобальный Context
    }

    @Provides
    public SharedViewModelFactory provideSharedViewModelFactory(ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase, GetUserIdUseCase getUserIdUseCase) {
        return new SharedViewModelFactory(application, observeCurrentUserDataUseCase, getUserIdUseCase);
    }

    @Provides
    public AuthorizationViewModelFactory provideAuthorizationViewModelFactory(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        return new AuthorizationViewModelFactory(loginUseCase, registrationUseCase, setAuthorizationDataToSPUseCase);
    }

    @Provides
    public UserSessionCheckerActivityViewModelFactory provideUserSessionCheckerActivityViewModelFactory(LogOutUseCase logOutUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase, CheckUserDataFromFBUseCase checkUserDataFromFBUseCase, CheckUserDataFromSPUseCase checkUserDataFromSPUseCase, CheckUserAuthorizationUseCase checkUserAuthorizationUseCase){
        return new UserSessionCheckerActivityViewModelFactory(logOutUseCase, saveUserDataToSPUseCase, checkUserDataFromFBUseCase, checkUserDataFromSPUseCase, checkUserAuthorizationUseCase);
    }

    @Provides
    public SetUserDataActivityViewModelFactory provideSetUserDataActivityViewModelFactory(SaveUserDataUseCase saveUserDataUseCase) {
        return new SetUserDataActivityViewModelFactory(saveUserDataUseCase);
    }

    @Provides
    public MainFragmentViewModelFactory provideMainActivityViewModelFactory() {
        return new MainFragmentViewModelFactory();
    }
}

