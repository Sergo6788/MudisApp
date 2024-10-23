package com.example.mudisapp.model;

public class FireStoreUser {
    private boolean isAdmin;


    public FireStoreUser (boolean isAdmin){
        this.isAdmin = isAdmin;
    }
    public boolean getIsAdmin(){
        return isAdmin;
    }
    public void setIsAdmin(boolean value){
        this.isAdmin = value;
    }

}
