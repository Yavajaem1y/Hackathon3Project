package com.androidlesson.hackathon3project.presentation.authorization.viewModels.mainActivityViewModel;

import android.util.Log;

import androidx.fragment.app.Fragment;
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
import com.androidlesson.hackathon3project.presentation.authorization.fragments.LoginFragment;
import com.androidlesson.hackathon3project.presentation.authorization.fragments.RegistrationFragment;
import com.androidlesson.hackathon3project.presentation.main.ui.fragments.MainFragment;

public class MainActivityViewModel extends ViewModel {
    private final CheckUserAuthorizationUseCase checkUserAuthorizationUseCase;
    private final CheckUserDataFromSPUseCase checkUserDataFromSPUseCase;
    private final CheckUserDataFromFBUseCase checkUserDataFromFBUseCase;
    private final SaveUserDataToSPUseCase saveUserDataToSPUseCase;
    private final LogOutUseCase logOutUseCase;

    private final MutableLiveData<Fragment> selectedFragmentMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<UserData> userDataMutableLiveData = new MutableLiveData<>();

    public MainActivityViewModel(CheckUserAuthorizationUseCase checkUserAuthorizationUseCase, CheckUserDataFromSPUseCase checkUserDataFromSPUseCase, CheckUserDataFromFBUseCase checkUserDataFromFBUseCase, SaveUserDataToSPUseCase saveUserDataToSPUseCase, LogOutUseCase logOutUseCase) {
        this.checkUserAuthorizationUseCase = checkUserAuthorizationUseCase;
        this.checkUserDataFromSPUseCase = checkUserDataFromSPUseCase;
        this.checkUserDataFromFBUseCase = checkUserDataFromFBUseCase;
        this.saveUserDataToSPUseCase = saveUserDataToSPUseCase;
        this.logOutUseCase = logOutUseCase;

        checkUserAuthorization();
    }

    public void checkUserAuthorization() {
        if (checkUserAuthorizationUseCase.execute()) {
            UserData userData = checkUserDataFromSPUseCase.execute();
            if (userData != null) {
                if (userData.getUserId().isEmpty()) {
                    checkUserDataFromFBUseCase.execute(new UserDataCallback() {
                        @Override
                        public void getUserData(UserData userData) {
                            if (userData != null) {
                                saveUserDataToSPUseCase.execute(userData);
                                userDataMutableLiveData.setValue(userData);
                                selectedFragmentMutableLiveData.setValue(new MainFragment());
                            }
                        }
                    });
                }
                else {
                    userDataMutableLiveData.setValue(userData);
                    selectedFragmentMutableLiveData.setValue(new MainFragment());
                }
            }
            else {
                Log.d("MainActivityViewModel","User data == null");
            }
        }
    }

    public void logOut () {
        logOutUseCase.execute();
    }

    public void go_to_login(){
        selectedFragmentMutableLiveData.setValue(new LoginFragment());
    }

    public void go_to_registration(){
        selectedFragmentMutableLiveData.setValue(new RegistrationFragment());
    }

    public void removeFragment(){
        selectedFragmentMutableLiveData.setValue(null);
    }

    public LiveData<Fragment> getSelectedFragmentMutableLiveData() {
        return selectedFragmentMutableLiveData;
    }

    public LiveData<UserData> getUserDataLiveData () {
        return userDataMutableLiveData;
    }
}
