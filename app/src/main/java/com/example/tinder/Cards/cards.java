package com.example.tinder.Cards;

public class cards {
        private String userid;
        private String name;
        private String profileimageurl;
        public cards(String userid,String name,String profileimageurl){
            this.userid=userid;
            this.name=name;
            this.profileimageurl=profileimageurl;
        }
        public String getUserid(){
            return userid;
        }
        public void setUserid(String userId){
            this.userid=userId;
        }
        public String getName(){
            return name;
        }
        public void setName(String Name){
            this.name=Name;
        }
        public String getProfileimageurl(){
        return profileimageurl;
    }
        public void setProfileimageurl(String profileimageurl){
        this.profileimageurl=profileimageurl;
    }
}

