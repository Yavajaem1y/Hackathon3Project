package com.androidlesson.domain.main.models;

public class UserDataToEdit {
    private String userId, userName, userSurname, userEmail, currentEmail, currentId, userSystemId;
    private byte[] userAvatarImage;

    public UserDataToEdit(String userId, String userName, String userSurname, String userEmail, String currentEmail, String currentId, String userSystemId) {
        this.userId = userId;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userEmail = userEmail;
        this.currentEmail = currentEmail;
        this.currentId = currentId;
        this.userSystemId=userSystemId;
    }

    public String getCurrentEmail() {
        return currentEmail;
    }

    public String getUserSystemId() {
        return userSystemId;
    }

    public String getCurrentId() {
        return currentId;
    }

    public String getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public byte[] getUserAvatarImage() {
        return userAvatarImage;
    }

    public void setUserAvatarImage(byte[] userAvatarImage) {
        this.userAvatarImage = userAvatarImage;
    }
}
