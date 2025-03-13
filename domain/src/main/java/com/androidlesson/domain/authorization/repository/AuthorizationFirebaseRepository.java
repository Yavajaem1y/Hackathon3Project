package com.androidlesson.domain.authorization.repository;

import com.androidlesson.domain.authorization.interfaces.BooleanCallBack;
import com.androidlesson.domain.authorization.models.DataToLogin;
import com.androidlesson.domain.authorization.models.DataToRegistration;
import com.androidlesson.domain.authorization.interfaces.StringCallBack;
import com.androidlesson.domain.main.interfaces.UserDataCallback;
import com.androidlesson.domain.main.models.ImageToDb;
import com.androidlesson.domain.main.models.UserData;
import com.androidlesson.domain.main.models.UserDataToEdit;

public interface AuthorizationFirebaseRepository {
    public void registration(DataToRegistration dataToRegistration, BooleanCallBack booleanCallBack, StringCallBack errorCallback);
    public void login(DataToLogin dataToLogin, BooleanCallBack booleanCallBack, StringCallBack errorCallback);
    public void checkAvailableIds(String id, BooleanCallBack booleanCallBack);
    public void saveUserData(UserData userData, UserDataCallback userDataCallback);
    public void getBasicId(StringCallBack stringCallBack);
    public void getCurrentUserData(UserDataCallback userDataCallback);
    public void logOut();
    public void editEmail(String email, BooleanCallBack booleanCallBack);
    public void editId(String oldId, String newId, BooleanCallBack booleanCallBack);
    public void editUserData(UserDataToEdit userDataToEdit, UserDataCallback userDataCallback);
    public void getUserEmail(StringCallBack stringCallBack);
    public void addImageAvatar(ImageToDb imageToDb, BooleanCallBack booleanCallBack);
}
