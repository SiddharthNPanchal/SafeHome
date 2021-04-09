package com.example.si.model;

public class UserBlockEveryDay {

    String blockNumber,flatName,secretaryPhoneNumber,everydayPhoneNumber,name,categoryId,categoryName;

    public UserBlockEveryDay() {

    }

    public String getBlockNumber() {
        return blockNumber;
    }

    public void setBlockNumber(String blockNumber) {
        this.blockNumber = blockNumber;
    }

    public String getFlatName() {
        return flatName;
    }

    public void setFlatName(String flatName) {
        this.flatName = flatName;
    }

    public String getSecretaryPhoneNumber() {
        return secretaryPhoneNumber;
    }

    public void setSecretaryPhoneNumber(String secretaryPhoneNumber) {
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }

    public String getEverydayPhoneNumber() {
        return everydayPhoneNumber;
    }

    public void setEverydayPhoneNumber(String everydayPhoneNumber) {
        this.everydayPhoneNumber = everydayPhoneNumber;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public UserBlockEveryDay(String blockNumber, String flatName, String secretaryPhoneNumber, String everydayPhoneNumber, String name, String categoryId, String categoryName) {
        this.blockNumber = blockNumber;
        this.flatName = flatName;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
        this.everydayPhoneNumber = everydayPhoneNumber;
        this.name = name;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }
}
