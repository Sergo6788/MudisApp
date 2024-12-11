package com.example.mudisapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mudisapp.enums.PaymentMethod;
import com.example.mudisapp.model.MenuModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
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
        ArrayList<MenuModel> listFavorites = getListFavorite();
        if(!isDelete){
            listFavorites.add(menuItem);
        }
        else
            listFavorites.removeIf(m -> Objects.equals(m.getName(), menuItem.getName()));
        sharedPreferences.edit().putString("listFavorites", gson.toJson(listFavorites)).apply();
    }

    public ArrayList<MenuModel> getListFavorite() {
        Type listType = new TypeToken<ArrayList<MenuModel>>() {
        }.getType();
        return gson.fromJson(sharedPreferences.getString("listFavorites",
                gson.toJson(new ArrayList<MenuModel>())), listType);
    }
    public boolean isMealInFavoriteList(MenuModel menuItem){
        ArrayList<MenuModel> listFavorites = getListFavorite();
        return listFavorites.stream().anyMatch(m -> Objects.equals(m.getName(), menuItem.getName()));
    }



    public void saveToCart(MenuModel menuItem, boolean isNeedToDelete) {
        ArrayList<MenuModel> cartList = getCartList();

        if(isNeedToDelete) {
            cartList.removeIf(cartItem -> Objects.equals(cartItem.getName(), menuItem.getName()));
        }
        else {
            for(MenuModel dish : cartList){
                if(dish.getName().equals(menuItem.getName())){
                    break;
                }
            }
            cartList.add(menuItem);
        }

        sharedPreferences.edit().putString("listCart", gson.toJson(cartList)).apply();
    }
    public void cleanCart() {
        ArrayList<MenuModel> cartList = getCartList();
        cartList.clear();

        sharedPreferences.edit().putString("listCart", gson.toJson(cartList)).apply();
    }

    public ArrayList<MenuModel> getCartList() {
        String json = sharedPreferences.getString("listCart", null);
        Type listType = new TypeToken<ArrayList<MenuModel>>() {}.getType();
        return json == null ? new ArrayList<>() : gson.fromJson(json, listType);
    }
    
    public boolean isItemInCart(MenuModel menuItem) {
        ArrayList<MenuModel> cartItems = getCartList();
        return cartItems.stream().anyMatch(m -> Objects.equals(m.getName(), menuItem.getName()));
    }
}
