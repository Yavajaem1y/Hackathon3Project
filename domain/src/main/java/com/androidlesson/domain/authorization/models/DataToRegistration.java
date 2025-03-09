package com.androidlesson.domain.authorization.models;

public class DataToRegistration {
    private String email,password,repassword;

    public DataToRegistration(String email, String password, String repassword) {
        this.email = email;
        this.password = password;
        this.repassword = repassword;
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
}
