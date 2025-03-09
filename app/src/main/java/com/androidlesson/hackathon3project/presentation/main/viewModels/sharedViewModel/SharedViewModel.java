package com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel;


import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidlesson.domain.authorization.useCase.GetUserIdUseCase;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserDataUseCase;

public class SharedViewModel extends AndroidViewModel {
    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;
    private final MutableLiveData<UserData> currentUserDataMutableLiveData = new MutableLiveData<>();

    public SharedViewModel(Application application, ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase, GetUserIdUseCase getUserIdUseCase) {
        super(application);
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;
        observeCurrentUserData(getUserIdUseCase.execute());
    }

    private void observeCurrentUserData(String id) {
        observeCurrentUserDataUseCase.execute(id, new UserDataCallback() {
            @Override
            public void getUserData(UserData userData) {
                setCurrentUserData(userData);
            }
        });
    }

    public void setCurrentUserData(UserData userData) {
        if (userData != null) {
            if (userData.getUserSurname()!=null && userData.getUserSurname()!=null && userData.getUserId()!=null)
                currentUserDataMutableLiveData.setValue(userData);
        }
    }

    public LiveData<UserData> getCurrentUserDataLiveData() {
        return currentUserDataMutableLiveData;
    }
}
