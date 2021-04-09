package com.example.si.model;

public class UserFlat {

    String userPhoneNumber,blockName,flatNumber,secretaryPhoneNumber;
    String name;

    public UserFlat(String userPhoneNumber, String blockName, String flatNumber, String secretaryPhoneNumber, String name) {
        this.userPhoneNumber = userPhoneNumber;
        this.blockName = blockName;
        this.flatNumber = flatNumber;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserFlat(){}

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getblockName() {
        return blockName;
    }

    public void setblockName(String blockName) {
        this.blockName = blockName;
    }

    public String getflatNumber() {
        return flatNumber;
    }

    public void setflatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
    }

    public String getSecretaryPhoneNumber() {
        return secretaryPhoneNumber;
    }

    public void setSecretaryPhoneNumber(String secretaryPhoneNumber) {
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }

}
