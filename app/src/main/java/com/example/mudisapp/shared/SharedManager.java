package com.example.mudisapp.shared;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mudisapp.enums.PaymentMethod;
import com.example.mudisapp.model.MenuModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
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
      sharedPreferences.edit().putString("paymentMethod", paymentMethod.toString()).apply();
    }

    public String getPaymentMethod() {
        return sharedPreferences.getString("paymentMethod", "CASH");
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
                gson.toJson(new ArrayList<>())), listType);
    }
    public boolean isMealInFavoriteList(MenuModel menuItem){
        ArrayList<MenuModel> listFavorites = getListFavorite();
        return listFavorites.stream().anyMatch(m -> Objects.equals(m.getName(), menuItem.getName()));
    }



    public void saveToCart(MenuModel menuItem, Integer count) {
        HashMap<String, Integer> cartList = getCartList();

        if(count == 0) {
            cartList.remove(menuItem.getId());
        }
        else {
            cartList.put(menuItem.getId(), count);
        }

        sharedPreferences.edit().putString("listCart", gson.toJson(cartList)).apply();
    }
    public void cleanCart() {
        HashMap<String, Integer> cartList = getCartList();
        cartList.clear();

        sharedPreferences.edit().putString("listCart", gson.toJson(cartList)).apply();
    }

    public HashMap<String, Integer> getCartList() {
        String json = sharedPreferences.getString("listCart", null);
        Type listType = new TypeToken<HashMap<String, Integer>>() {}.getType();

        return json == null ? new HashMap<>() : gson.fromJson(json, listType);
    }
    
    public boolean isItemInCart(MenuModel menuItem) {
        HashMap<String, Integer> cartItems = getCartList();
        return cartItems.get(menuItem.getId()) != null;
    }
    public int getAmountOfDishInCart(MenuModel menuModel){
        return getCartList().get(menuModel.getId()) == null ? 0 : getCartList().get(menuModel.getId());
    }
}
