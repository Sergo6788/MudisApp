package com.example.mudisapp.model;

import com.example.mudisapp.enums.MealType;


public class MenuModel {
    private String image;
    private String name;
    private int price;
    private String type;
    private String id;
    private boolean ready;

    public MenuModel(String id, String image, String name, int price, MealType type){
        this.id = id;
        this.image = image;
        this.name = name;
        this.price = price;
        this.type = type.name();
    }

    private MenuModel(String image, String name, String price, String type){
        this.image = image;
        this.name = name;
        this.price = Integer.parseInt(price);
        this.type = type;
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

    public String getId(){return id;}
    public void setId(String id){this.id = id;}

    public boolean getReady(){return ready;}
    public void setReady(boolean ready){this.ready = ready;}


}
