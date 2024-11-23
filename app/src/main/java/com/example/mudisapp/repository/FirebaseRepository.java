package com.example.mudisapp.repository;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mudisapp.model.MenuModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.Firebase;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.Map;
import java.util.Objects;

public class FirebaseRepository extends ViewModel {
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private ArrayList<MenuModel> menuList = new ArrayList<>();
    public MutableLiveData<Boolean> isTaskReady = new MutableLiveData<>(false);


    public void getMenu() {
        dataBase.collection("Dishes").get()
                .addOnSuccessListener(task -> {
                    for (DocumentSnapshot documentSnapshot : task.getDocuments()) {
                        MenuModel data = documentSnapshot.toObject(MenuModel.class);
                        menuList.add(data);
                    }
                    isTaskReady.setValue(true);
                })
                .addOnFailureListener(error -> {
                    Log.d("ERROR", error.getMessage());
                });
    }
    public ArrayList<MenuModel> getMenuList(){
        isTaskReady.setValue(false);
        return menuList;
    }

}
