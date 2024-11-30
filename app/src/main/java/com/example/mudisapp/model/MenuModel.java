package com.example.mudisapp.model;

import android.view.Menu;

public class MenuModel {
    private String image;
    private String name;
    private int price;
    private boolean isReady;


    public MenuModel(String image, String name, int price){
        this.image = image;
        this.name = name;
        this.price = price;
        this.isReady = true;
    }
    public MenuModel(){

    }
    public MenuModel(String image, String name, int price, boolean isReady){
        this.image = image;
        this.name = name;
        this.price = price;
        this.isReady = isReady;

    }
    public int getPrice(){
        return price;
    }
    public String getName(){
        return name;
    }
    public String getImage(){
        return image;
    }
    public boolean isReady(){
        return  isReady;
    }


}
