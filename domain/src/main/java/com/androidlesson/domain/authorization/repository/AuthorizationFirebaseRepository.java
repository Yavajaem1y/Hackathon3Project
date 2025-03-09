package com.androidlesson.domain.authorization.repository;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.UserData;

public interface AuthorizationFirebaseRepository {
    public void registration(DataToRegistration dataToRegistration, BooleanCallBack booleanCallBack, StringCallBack errorCallback);
    public void login(DataToLogin dataToLogin, BooleanCallBack booleanCallBack, StringCallBack errorCallback);
    public void checkAvailableIds(String id, BooleanCallBack booleanCallBack);
    public void saveUserData(UserData userData, UserDataCallback userDataCallback);
    public void getBasicId(StringCallBack stringCallBack);
    public void getCurrentUserData(UserDataCallback userDataCallback);
    public void logOut();
}
