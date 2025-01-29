package com.example.mudisapp.app;

import android.app.Application;
import android.content.Context;

import com.example.mudisapp.shared.SharedManager;

public class App extends Application {
    public static SharedManager sharedManager;
    public static Context appContext;

    public void onCreate(){
        super.onCreate();
        appContext = getApplicationContext();
        sharedManager = new SharedManager(getBaseContext());
    }

}
