package com.example.mudisapp.model;

import android.view.Menu;

import com.example.mudisapp.enums.MealType;

public class MenuModel {
    private String image;
    private String name;
    private int price;
    private String type;

    public MenuModel(String image, String name, int price, MealType type){
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type.name();
    }
    public MenuModel(){

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
    public String getType(){return type;}

}
