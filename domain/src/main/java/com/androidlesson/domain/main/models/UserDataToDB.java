package com.androidlesson.domain.main.models;

public class UserDataToDB {
    private String userSystemId, userName, userSurname;
    private int currentPoint,isFirstTime;

    public UserDataToDB(String userSystemId, String userName, String userSurname) {
        this.userSystemId = userSystemId;
        this.userName = userName;
        this.userSurname = userSurname;
    }

    public int getIsFirstTime() {
        return isFirstTime;
    }

    public void setIsFirstTime(int isFirstTime) {
        this.isFirstTime = isFirstTime;
    }

    public int getCurrentPoint() {
        return currentPoint;
    }

    public void setCurrentPoint(int currentPoint) {
        this.currentPoint = currentPoint;
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
