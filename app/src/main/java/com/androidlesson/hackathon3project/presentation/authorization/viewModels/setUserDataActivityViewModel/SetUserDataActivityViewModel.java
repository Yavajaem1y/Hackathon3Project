package com.androidlesson.hackathon3project.presentation.authorization.viewModels.setUserDataActivityViewModel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.useCase.SaveUserDataUseCase;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;

public class SetUserDataActivityViewModel extends ViewModel {
    private SaveUserDataUseCase saveUserDataUseCase;

    //Initialization use-cases
    public SetUserDataActivityViewModel(SaveUserDataUseCase saveUserDataUseCase) {
        this.saveUserDataUseCase = saveUserDataUseCase;
    }

    private MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();
    private MutableLiveData<String> ErrorMutableLiveData=new MutableLiveData<>();

    public void SaveUserData(UserData user){
        saveUserDataUseCase.execute(user, new StringCallBack() {
            @Override
            public void getString(String string) {
                ErrorMutableLiveData.setValue(string);
            }
        }, new UserDataCallback() {
            @Override
            public void getUserData(UserData userData) {
                if (userData!=null)
                    userDataMutableLiveData.setValue(userData);
            }
        });
    }

    public LiveData<UserData> getUserDataLiveData(){
        return userDataMutableLiveData;
    }
    public LiveData<String> getErrorLiveData(){
        return ErrorMutableLiveData;
    }
}
