package com.example.si.model;

public class Blocks {
    String blockName,secretaryPhoneNumber,numberOfFloor;
    public Blocks(){}
    public Blocks(String blockName, String secretaryPhoneNumber, String numberOfFloor) {
        this.blockName = blockName;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
        this.numberOfFloor = numberOfFloor;
    }

    public String getblockName() {
        return blockName;
    }

    public void setblockName(String blockName) {
        this.blockName = blockName;
    }

    public String getSecretaryPhoneNumber() {
        return secretaryPhoneNumber;
    }

    public void setSecretaryPhoneNumber(String secretaryPhoneNumber) {
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }

    public String getNumberOfFloor() {
        return numberOfFloor;
    }

    public void setNumberOfFloor(String numberOfFloor) {
        this.numberOfFloor = numberOfFloor;
    }
}
