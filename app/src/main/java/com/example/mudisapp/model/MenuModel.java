package com.example.mudisapp.model;

public class MenuModel {
    private String image;
    private String name;
    private Double price;

    public MenuModel(String image, String name, Double price){
        this.image = image;
        this.name = name;
        this.price = price;
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

}
