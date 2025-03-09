package com.androidlesson.hackathon3project.presentation.authorization.viewModels.authorizationActivityViewModel;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;

public class AuthorizationViewModel extends ViewModel {
    private LoginUseCase loginUseCase;
    private RegistrationUseCase registrationUseCase;
    private SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase;

    private MutableLiveData<Boolean> goToMainActivityLiveData = new MutableLiveData<>();
    private MutableLiveData<Integer> loginVisibilityLiveData = new MutableLiveData<Integer>();
    private MutableLiveData<Integer> registrationVisibilitySceneLiveData = new MutableLiveData<Integer>();
    private MutableLiveData<String> errorAuthorizationLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> progressBarSceneLiveData = new MutableLiveData<>();

    private BooleanCallBack callbackAuthorizaiton = new BooleanCallBack() {
        @Override
        public void getBoolean(Boolean bool) {
            if (bool) {
                setAuthorizationDataToSPUseCase.execute();
                goToMainActivityLiveData.setValue(true);
                progressBarSceneLiveData.setValue(false);
            } else {
                goToMainActivityLiveData.setValue(false);
                progressBarSceneLiveData.setValue(false);
            }
        }
    };

    private StringCallBack authorizationError=new StringCallBack() {
        @Override
        public void getString(String string) {
            errorAuthorizationLiveData.setValue(string);
            progressBarSceneLiveData.setValue(false);
        }
    };


    public AuthorizationViewModel(LoginUseCase loginUseCase, RegistrationUseCase registrationUseCase, SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase) {
        this.loginUseCase = loginUseCase;
        this.registrationUseCase = registrationUseCase;
        this.setAuthorizationDataToSPUseCase = setAuthorizationDataToSPUseCase;
    }

    public void login(DataToLogin user){
        progressBarSceneLiveData.setValue(true);
        loginUseCase.execute(user,callbackAuthorizaiton,authorizationError);
    }

    public void registration(DataToRegistration user){
        progressBarSceneLiveData.setValue(true);
        registrationUseCase.execute(user,callbackAuthorizaiton,authorizationError);
    }

    public void loginScene(){
        loginVisibilityLiveData.setValue(View.VISIBLE);
        registrationVisibilitySceneLiveData.setValue(View.INVISIBLE);
    }

    public void registerScene(){
        loginVisibilityLiveData.setValue(View.INVISIBLE);
        registrationVisibilitySceneLiveData.setValue(View.VISIBLE);
    }

    public LiveData<Boolean> getGoToMainActivityLiveData() {
        return goToMainActivityLiveData;
    }

    public LiveData<Integer> getLoginVisibilityLiveData() {
        return loginVisibilityLiveData;
    }

    public LiveData<Integer> getRegistrationVisibilitySceneLiveData() {
        return registrationVisibilitySceneLiveData;
    }

    public LiveData<String> getErrorAuthorizationLiveData() {
        return errorAuthorizationLiveData;
    }

    public LiveData<Boolean> getProgressBarSceneLiveData() {
        return progressBarSceneLiveData;
    }


}
