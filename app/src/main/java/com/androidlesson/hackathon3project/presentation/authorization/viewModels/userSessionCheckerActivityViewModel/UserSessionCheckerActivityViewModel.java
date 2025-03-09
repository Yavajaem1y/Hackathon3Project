package com.androidlesson.hackathon3project.presentation.authorization.viewModels.userSessionCheckerActivityViewModel;

import android.app.Activity;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidlesson.domain.authorization.useCase.CheckUserAuthorizationUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromFBUseCase;
import com.androidlesson.domain.authorization.useCase.CheckUserDataFromSPUseCase;
import com.androidlesson.domain.authorization.useCase.SaveUserDataToSPUseCase;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.LogOutUseCase;
import com.androidlesson.hackathon3project.presentation.authorization.activities.AuthorizationActivity;
import com.androidlesson.hackathon3project.presentation.authorization.activities.SetUserDataActivity;

public class UserSessionCheckerActivityViewModel extends ViewModel {
    private final CheckUserAuthorizationUseCase checkUserAuthorizationUseCase;
    private final CheckUserDataFromSPUseCase checkUserDataFromSPUseCase;
    private final CheckUserDataFromFBUseCase checkUserDataFromFBUseCase;
    private final SaveUserDataToSPUseCase saveUserDataToSPUseCase;
    private final LogOutUseCase logOutUseCase;

    private final MutableLiveData<Integer> activityToActionMutableLiveData=new MutableLiveData<>();
    private final MutableLiveData<UserData> userDataMutableLiveData=new MutableLiveData<>();

    public UserSessionCheckerActivityViewModel(CheckUserAuthorizationUseCase checkUserAuthorizationUseCase, CheckUserDataFromSPUseCase checkUserDataFromSPUseCase, CheckUserDataFromFBUseCase checkUserDataFromFBUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase, LogOutUseCase logOutUseCase) {
        this.checkUserAuthorizationUseCase = checkUserAuthorizationUseCase;
        this.checkUserDataFromSPUseCase = checkUserDataFromSPUseCase;
        this.checkUserDataFromFBUseCase = checkUserDataFromFBUseCase;
        this.saveUserDataToSPUseCase = saveUserDataToSPUseCase;
        this.logOutUseCase = logOutUseCase;
    }

    public void checkUserAuthorization(){
        if (!(checkUserAuthorizationUseCase.execute())){
            activityToActionMutableLiveData.setValue(0);
        }
        else {
            UserData userData=checkUserDataFromSPUseCase.execute();
            if (userData!=null){
                userDataMutableLiveData.setValue(userData);
                checkUserDataFromFBUseCase.execute(new UserDataCallback() {
                    @Override
                    public void getUserData(UserData userData) {
                        if (userData!=null){
                            saveUserDataToSPUseCase.execute(userData);
                            userDataMutableLiveData.setValue(userData);
                            activityToActionMutableLiveData.setValue(2);
                        }
                        else{
                            activityToActionMutableLiveData.setValue(1);
                        }
                    }
                });
            }
            else activityToActionMutableLiveData.setValue(2);
        }
    }

    public void logOut(){
        logOutUseCase.execute();
    }

    public LiveData<Integer> getActivityToActionLiveData(){return activityToActionMutableLiveData;}
    public LiveData<UserData> getUserDataLiveData(){return userDataMutableLiveData;}
}
