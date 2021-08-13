package com.example.tinder.conncetions;

public class connectionobject {
    private String name;
    private String profileimageurl;
    public connectionobject(String name,String profileimageurl){
        this.name=name;
        this.profileimageurl=profileimageurl;
    }
    public String getName(){
        return name;
    }
    public void setName(String Name){
        this.name=Name;
    }

    public String getProfileimageurl() {
        return profileimageurl;
    }

    public void setProfileimageurl(String profileimageurl) {
        this.profileimageurl = profileimageurl;
    }
}
