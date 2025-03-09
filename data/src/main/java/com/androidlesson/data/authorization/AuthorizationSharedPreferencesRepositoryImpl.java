package com.androidlesson.data.authorization;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.androidlesson.domain.authorization.repository.AuthorizationSharedPreferencesRepository;
import com.androidlesson.domain.main.models.UserData;

public class AuthorizationSharedPreferencesRepositoryImpl implements AuthorizationSharedPreferencesRepository {

    private SharedPreferences spDataBase;

    private final String SP_DATABASE_NAME="SP_DATABASE_NAME";
    private final String USER_AUTHORIZATION_SP="USER_AUTHORIZATION_SP";
    private final String USER_ID_FROM_SP="USER_ID_FROM_SP";
    private final String USER_SYSTEM_ID_FROM_SP="USER_SYSTEM_ID_FROM_SP";
    private final String USER_NAME_FROM_SP="USER_NAME_FROM_SP";
    private final String USER_SURNAME_FROM_SP="USER_SURNAME_FROM_SP";

    public AuthorizationSharedPreferencesRepositoryImpl(Context context) {
        spDataBase=context.getSharedPreferences(SP_DATABASE_NAME,Context.MODE_PRIVATE);
    }

    @Override
    public void setAuthorization(Boolean authorization) {
        SharedPreferences.Editor editor=spDataBase.edit();
        editor.putBoolean(USER_AUTHORIZATION_SP,authorization).apply();
    }

    @Override
    public Boolean getAuthorization() {
        return spDataBase.getBoolean(USER_AUTHORIZATION_SP,false);
    }

    @Override
    public UserData getUserData() {
        String id=spDataBase.getString(USER_ID_FROM_SP,"");
        String systemId=spDataBase.getString(USER_SYSTEM_ID_FROM_SP,"");
        String name=spDataBase.getString(USER_NAME_FROM_SP,"");
        String surname=spDataBase.getString(USER_SURNAME_FROM_SP,"");
        Log.d("id",id);
        Log.d("sysId",systemId);
        Log.d("name",name);
        Log.d("surname",surname);

        return new UserData(name,surname,systemId,id);
    }

    @Override
    public void setUserData(UserData userData) {
        SharedPreferences.Editor editor=spDataBase.edit();
        editor.putString(USER_ID_FROM_SP,userData.getUserId()).apply();
        editor.putString(USER_SYSTEM_ID_FROM_SP,userData.getUserSystemId()).apply();
        editor.putString(USER_NAME_FROM_SP,userData.getUserName()).apply();
        editor.putString(USER_SURNAME_FROM_SP,userData.getUserSurname()).apply();
    }

    @Override
    public void logOut() {
        SharedPreferences.Editor editor=spDataBase.edit();
        editor.clear().apply();
        Log.d("Authorization","SignOut in sp");
    }

    @Override
    public String getUserId() {
        return spDataBase.getString(USER_ID_FROM_SP,"");
    }
}
