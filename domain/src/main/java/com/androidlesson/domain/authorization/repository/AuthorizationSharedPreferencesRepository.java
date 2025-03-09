package com.androidlesson.domain.authorization.repository;

import com.androidlesson.domain.main.models.UserData;

public interface AuthorizationSharedPreferencesRepository {
    public void setAuthorization(Boolean authorization);
    public Boolean getAuthorization();
    public UserData getUserData();
    public void setUserData(UserData userData);
    public void logOut();
    public String getUserId();;
}
