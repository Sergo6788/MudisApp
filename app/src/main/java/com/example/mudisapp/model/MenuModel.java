package com.example.mudisapp.model;

import android.view.Menu;

import androidx.annotation.NonNull;

import com.example.mudisapp.enums.MealType;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class MenuModel {
    private String image;
    private String name;
    private int price;
    private String type;
    private String id;

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


}
