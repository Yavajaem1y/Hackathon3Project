package com.androidlesson.hackathon3project.presentation.main.viewModels.sharedViewModel;


import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.authorization.useCase.GetUserIdUseCase;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.useCase.GetUserEmailUseCase;
import com.androidlesson.domain.main.useCase.ObserveCurrentUserDataUseCase;

public class SharedViewModel extends AndroidViewModel {
    private final ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase;
    private final GetUserEmailUseCase getUserEmailUseCase;

    private final MutableLiveData<UserData> currentUserDataMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> userEmailMutableLiveData = new MutableLiveData<>();
    private final MutableLiveData<String> removedHeroDataIdMutableLiveData = new MutableLiveData<>();

    public SharedViewModel(Application application, ObserveCurrentUserDataUseCase observeCurrentUserDataUseCase, GetUserIdUseCase getUserIdUseCase, GetUserEmailUseCase getUserEmailUseCase) {
        super(application);
        this.observeCurrentUserDataUseCase = observeCurrentUserDataUseCase;
        this.getUserEmailUseCase=getUserEmailUseCase;
        observeCurrentUserData();
    }

    private void observeCurrentUserData() {
        observeCurrentUserDataUseCase.execute(new UserDataCallback() {
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

    public void getUserEmail(){
        getUserEmailUseCase.execute(new StringCallBack() {
            @Override
            public void getString(String string) {
                if (!string.isEmpty())
                    userEmailMutableLiveData.setValue(string);
            }
        });
    }

    public void removeHeroData(String heroId) {
        removedHeroDataIdMutableLiveData.setValue(heroId);
    }

    public LiveData<UserData> getCurrentUserDataLiveData() {
        return currentUserDataMutableLiveData;
    }

    public LiveData<String> getUserEmailMutableLiveData() {
        return userEmailMutableLiveData;
    }

    public LiveData<String> getRemovedHeroDataIdMutableLiveData() {
        return removedHeroDataIdMutableLiveData;
    }
}
