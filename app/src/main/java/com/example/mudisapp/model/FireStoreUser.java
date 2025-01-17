package com.example.mudisapp.model;

public class FireStoreUser {
    private boolean isAdmin = false;
    private String uid;
    private String email;
    private String nickName;

    public FireStoreUser(){

    }

    public String getUid() {return uid;}
    public void setUid(String uid) {this.uid = uid;}

    public String getEmail() {return email;}
    public void setEmail(String email) {this.email = email;}

    public boolean getIsAdmin(){
        return isAdmin;
    }
    public void setIsAdmin(boolean value){
        this.isAdmin = value;
    }

    public String getNickName(){
        return nickName;
    }
    public void setNickName(String nickName){this.nickName = nickName;}
}





