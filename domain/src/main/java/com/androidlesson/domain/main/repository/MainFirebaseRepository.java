package com.androidlesson.domain.main.repository;

import com.androidlesson.domain.main.interfaces.UserDataCallback;

public interface MainFirebaseRepository {
    public void observeCurrentUserData(UserDataCallback userDataCallback);
}
