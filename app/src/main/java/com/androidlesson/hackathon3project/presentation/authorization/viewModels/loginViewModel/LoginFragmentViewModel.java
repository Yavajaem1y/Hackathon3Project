package com.androidlesson.hackathon3project.presentation.authorization.viewModels.loginViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.domain.authorization.useCase.LoginUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;

public class LoginFragmentViewModel extends ViewModel {
    private LoginUseCase loginUseCase;
    private SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase;

    private MutableLiveData<String> errorMessageMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<Boolean> resultMutableLiveData=new MutableLiveData<>();

    public LoginFragmentViewModel(SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase, LoginUseCase loginUseCase) {
        this.setAuthorizationDataToSPUseCase = setAuthorizationDataToSPUseCase;
        this.loginUseCase = loginUseCase;
    }

    public void login(DataToLogin data){
        loginUseCase.execute(data, new BooleanCallBack() {
            @Override
            public void getBoolean(Boolean bool) {
                if (bool) setAuthorizationDataToSPUseCase.execute();
                resultMutableLiveData.setValue(bool);
            }
        }, new StringCallBack() {
            @Override
            public void getString(String string) {
                errorMessageMutableLiveData.setValue(string);
            }
        });
    }

    public LiveData<String> getErrorMessageMutableLiveData() {
        return errorMessageMutableLiveData;
    }

    public LiveData<Boolean> getResultMutableLiveData() {
        return resultMutableLiveData;
    }
}
