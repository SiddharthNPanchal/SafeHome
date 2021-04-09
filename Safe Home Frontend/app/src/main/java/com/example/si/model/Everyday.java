package com.example.si.model;

public class Everyday {
    String everydayPhoneNumber,name,categoryId,secretaryPhoneNumber;

    public Everyday(String everydayPhoneNumber, String name, String categoryId, String secretaryPhoneNumber) {
        this.everydayPhoneNumber = everydayPhoneNumber;
        this.name = name;
        this.categoryId = categoryId;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }
    public  Everyday(){}

    public void setEverydayPhoneNumber(String everydayPhoneNumber) {
        this.everydayPhoneNumber = everydayPhoneNumber;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public void setSecretaryPhoneNumber(String secretaryPhoneNumber) {
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }

    public String getEverydayPhoneNumber() {
        return everydayPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public String getSecretaryPhoneNumber() {
        return secretaryPhoneNumber;
    }
}
