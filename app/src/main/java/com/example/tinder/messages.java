package com.example.tinder;

public class messages {
    String uID,message;
    Long timestamp;

    public messages(String uID, String message, Long timestamp) {
        this.uID = uID;
        this.message = message;
        this.timestamp = timestamp;
    }

    public messages(String uID, String message) {
        this.uID = uID;
        this.message = message;
    }
    public messages(){}

    public String getuID() {
        return uID;
    }

    public void setuID(String uID) {
        this.uID = uID;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Long timestamp) {
        this.timestamp = timestamp;
    }
}
