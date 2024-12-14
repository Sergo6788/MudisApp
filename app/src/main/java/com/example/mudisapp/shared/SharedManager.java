package com.example.mudisapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mudisapp.enums.PaymentMethod;
import com.example.mudisapp.model.MenuModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SharedManager {
    private Gson gson;
    private SharedPreferences sharedPreferences;

    public SharedManager(Context baseContext) {
        this.sharedPreferences = baseContext.getSharedPreferences("MudisApp", Context.MODE_PRIVATE);
        this.gson = new Gson();
    }

    public boolean isUserAuthorized() {
        return sharedPreferences.getBoolean("isUserAuthorized", false);
    }

    public void userAuthorize() {
        sharedPreferences.edit().putBoolean("isUserAuthorized", true).apply();
    }

    public void userLogout() {
        sharedPreferences.edit().putBoolean("isUserAuthorized", false).apply();
    }

    public void saveUID(String uid) {
        sharedPreferences.edit().putString("userUID", uid).apply();
    }

    public String getUID() {
        return sharedPreferences.getString("userUID", "");
    }


    public void savePaymentMethod(PaymentMethod paymentMethod) {
        if (paymentMethod == PaymentMethod.APPLE_PAY) {
            sharedPreferences.edit().putString("paymentMethod", "Apple Pay").apply();
        } else if (paymentMethod == PaymentMethod.GOOGLE_PAY) {
            sharedPreferences.edit().putString("paymentMethod", "Google Pay").apply();
        } else if (paymentMethod == PaymentMethod.CASH) {
            sharedPreferences.edit().putString("paymentMethod", "Cash on delivery").apply();
        }
    }

    public String getPaymentMethod() {
        return sharedPreferences.getString("paymentMethod", "Cash on delivery");
    }



    public void saveFavorite(MenuModel menuItem, boolean isDelete) {
        HashMap<MenuModel, Integer> listFavorites = getListFavorite();
        if(!isDelete){
            listFavorites.put(menuItem, 0);
        }
        else
            listFavorites.remove(menuItem);
        sharedPreferences.edit().putString("listFavorites", gson.toJson(listFavorites)).apply();
    }

    public HashMap<MenuModel, Integer> getListFavorite() {
        Type listType = new TypeToken<HashMap<MenuModel, Integer>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString("listFavorites",
                gson.toJson(new HashMap<MenuModel, Integer>())), listType);
    }
    public boolean isMealInFavoriteList(MenuModel menuItem){
        HashMap<MenuModel, Integer> listFavorites = getListFavorite();
        return listFavorites.get(menuItem) == null;
    }



    public void saveToCart(MenuModel menuItem, boolean isNeedToDelete) {
        HashMap<MenuModel, Integer> cartList = getCartList();

        if(isNeedToDelete) {
            cartList.remove(menuItem);
        }
        else {
            for(MenuModel dish : cartList.keySet()){
                if(dish.getName().equals(menuItem.getName())){
                    break;
                }
            }
            cartList.put(menuItem, 1);
        }

        sharedPreferences.edit().putString("listCart", gson.toJson(cartList)).apply();
    }
    public void cleanCart() {
        HashMap<MenuModel, Integer> cartList = getCartList();
        cartList.clear();

        sharedPreferences.edit().putString("listCart", gson.toJson(cartList)).apply();
    }

    public HashMap<MenuModel, Integer> getCartList() {
        String json = sharedPreferences.getString("listCart", null);
        Type listType = new TypeToken<HashMap<MenuModel, Integer>>() {}.getType();
        return json == null ? new HashMap<>() : gson.fromJson(json, listType);
    }
    
    public boolean isItemInCart(MenuModel menuItem) {
        HashMap<MenuModel, Integer> cartItems = getCartList();
        return cartItems.get(menuItem) == null;
    }
}
