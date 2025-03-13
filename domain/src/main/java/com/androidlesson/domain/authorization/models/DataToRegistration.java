package com.androidlesson.domain.authorization.models;

public class DataToRegistration {
    private String email,password,repassword,userName,userSurname,userId;

    public DataToRegistration(String email, String password, String repassword, String userName, String userSurname, String userId) {
        this.email = email;
        this.password = password;
        this.repassword = repassword;
        this.userName = userName;
        this.userSurname = userSurname;
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRepassword() {
        return repassword;
    }

    public void setRepassword(String repassword) {
        this.repassword = repassword;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserSurname() {
        return userSurname;
    }

    public void setUserSurname(String userSurname) {
        this.userSurname = userSurname;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
