package com.example.tinder.matches;

public class Matchesobject {
    private String name;
    private String profileimageurl;
    public Matchesobject(String name,String profileimageurl){
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
