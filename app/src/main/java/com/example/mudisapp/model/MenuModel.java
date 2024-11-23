package com.example.mudisapp.model;

public class MenuModel {
    private String image;
    private String name;
    private Double price;
    private boolean isReady;


    public MenuModel(String image, String name, Double price){
        this.image = image;
        this.name = name;
        this.price = price;
        this.isReady = true;
    }
    public MenuModel(String image, String name, Double price, boolean isReady){
        this.image = image;
        this.name = name;
        this.price = price;
        this.isReady = isReady;

    }
    public Double getPrice(){
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
