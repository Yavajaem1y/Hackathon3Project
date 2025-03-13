package com.androidlesson.hackathon3project.presentation.authorization.viewModels.registratoinViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.useCase.RegistrationUseCase;
import com.androidlesson.domain.authorization.useCase.SetAuthorizationDataToSPUseCase;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;


public class RegistrationFragmentViewModel extends ViewModel {
    private RegistrationUseCase registrationUseCase;
    private SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase;

    private MutableLiveData<String> errorCallbackMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();

    public RegistrationFragmentViewModel(SetAuthorizationDataToSPUseCase setAuthorizationDataToSPUseCase, RegistrationUseCase registrationUseCase) {
        this.setAuthorizationDataToSPUseCase = setAuthorizationDataToSPUseCase;
        this.registrationUseCase = registrationUseCase;
    }

    public void registration(DataToRegistration data){
        registrationUseCase.execute(data, new StringCallBack() {
            @Override
            public void getString(String string) {
                errorCallbackMutableLiveData.setValue(string);
            }
        }, new UserDataCallback() {
            @Override
            public void getUserData(UserData userData) {
                setAuthorizationDataToSPUseCase.execute();
                userDataMutableLiveData.setValue(userData);
            }
        });
    }

    public LiveData<String> getErrorCallbackMutableLiveData() {
        return errorCallbackMutableLiveData;
    }

    public LiveData<UserData> getUserDataMutableLiveData() {
        return userDataMutableLiveData;
    }
}
