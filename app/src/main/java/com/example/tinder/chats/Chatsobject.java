package com.example.tinder.chats;

public class Chatsobject {
    private String message;
    private Boolean currentuser;

    public Chatsobject(String message,Boolean currentuser){
        this.message=message;
        this.currentuser=currentuser;
    }
    public String getmessage(){ return message;}
    public void setMessage(String message){ this.message=message;}

    public Boolean getcurrentuser(){ return currentuser;}
    public void setcurrentuser(Boolean currentuser){ this.currentuser=currentuser;}
}
