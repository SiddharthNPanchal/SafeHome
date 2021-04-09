package com.example.si.model;

public class EverydayInOutEntries {
    String phoneNumber,secretaryPhoneNumber;

    public EverydayInOutEntries(String phoneNumber, String secretaryPhoneNumber) {
        this.phoneNumber = phoneNumber;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }

    public EverydayInOutEntries() {
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getSecretaryPhoneNumber() {
        return secretaryPhoneNumber;
    }

    public void setSecretaryPhoneNumber(String secretaryPhoneNumber) {
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }
}
