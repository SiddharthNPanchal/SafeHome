package com.example.si.model;

public class Notice {
        String id,secretaryPhoneNumber,message,title;
        String date;

    public Notice(String id, String secretaryPhoneNumber, String message, String date) {
        this.id = id;
        this.secretaryPhoneNumber = secretaryPhoneNumber;
        this.message = message;
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Notice() {

    }

    public void setId(String id) {
        this.id = id;
    }

    public void setSecretaryPhoneNumber(String secretaryPhoneNumber) {
        this.secretaryPhoneNumber = secretaryPhoneNumber;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setDate(String date) {
        this.date = date;

    }


    public String getId() {
        return id;
    }

    public String getSecretaryPhoneNumber() {
        return secretaryPhoneNumber;
    }

    public String getMessage() {
        return message;
    }

    public String getDate() {
        return date;
    }
}
