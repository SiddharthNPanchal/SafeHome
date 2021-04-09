package com.example.si.model;

public class Flat {

    String flatNumber,blockName,secretaryPhoneNumber,floorNumber;

    public Flat(){

    }
    public String getflatNumber() {
        return flatNumber;
    }

    public void setflatNumber(String flatNumber) {
        this.flatNumber = flatNumber;
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

    public String getFloorNumber() {
        return floorNumber;
    }

    public void setFloorNumber(String floorNumber) {
        this.floorNumber = floorNumber;
    }

    public Flat(String flatNumber, String blockName, String secretaryPhoneNumber, String floorNumber) {
        this.flatNumber = flatNumber;
        this.blockName = blockName;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
        this.floorNumber = floorNumber;
    }
}
