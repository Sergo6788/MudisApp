package com.example.mudisapp.shared;
import android.content.Context;
import android.content.SharedPreferences;

import com.example.mudisapp.enums.PaymentMethod;

public class SharedManager {
    public SharedManager(Context baseContext){
        this.sharedPreferences = baseContext.getSharedPreferences("MudisApp", Context.MODE_PRIVATE);
    }
    private SharedPreferences sharedPreferences;

    public boolean isUserAuthorized(){
        return sharedPreferences.getBoolean("isUserAuthorized", false);
    }
    public void userAuthorize(){
        sharedPreferences.edit().putBoolean("isUserAuthorized", true).apply();
    }
    public void userLogout(){
        sharedPreferences.edit().putBoolean("isUserAuthorized", false).apply();
    }
    public void saveUID(String uid){
        sharedPreferences.edit().putString("userUID",uid).apply();
    }
    public String getUID(){
        return sharedPreferences.getString("userUID", "");
    }


    public void savePaymentMethod(PaymentMethod paymentMethod){
        if(paymentMethod == PaymentMethod.APPLE_PAY){
            sharedPreferences.edit().putString("paymentMethod", "Apple Pay").apply();
        }
        else if(paymentMethod == PaymentMethod.GOOGLE_PAY){
            sharedPreferences.edit().putString("paymentMethod", "Google Pay").apply();
        }
        else if(paymentMethod == PaymentMethod.CASH){
            sharedPreferences.edit().putString("paymentMethod", "Cash on delivery").apply();
        }
    }

    public String getPaymentMethod(){
        return sharedPreferences.getString("paymentMethod", "Cash on delivery");
    }
}
