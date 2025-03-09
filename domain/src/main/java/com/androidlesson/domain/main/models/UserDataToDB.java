package com.androidlesson.domain.main.models;

public class UserDataToDB {
    private String userSystemId, userName, userSurname;

    public UserDataToDB(String userSystemId, String userName, String userSurname) {
        this.userSystemId = userSystemId;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public String getUserSystemId() {
        return userSystemId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }
}
