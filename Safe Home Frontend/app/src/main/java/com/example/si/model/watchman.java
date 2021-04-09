package com.example.si.model;

public class watchman {
    String userPhoneNumber,userName,password,userTypeId;

    public watchman(String userPhoneNumber, String userName, String password, String userTypeId) {
        this.userPhoneNumber = userPhoneNumber;
        this.userName = userName;
        this.password = password;
        this.userTypeId = userTypeId;
    }

    public watchman() {

    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserTypeId() {
        return userTypeId;
    }

    public void setUserTypeId(String userTypeId) {
        this.userTypeId = userTypeId;
    }
}
