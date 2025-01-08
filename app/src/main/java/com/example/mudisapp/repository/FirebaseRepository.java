package com.example.mudisapp.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.model.OrderModel;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseRepository extends ViewModel {
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private ArrayList<MenuModel> menuList = new ArrayList<>();
    public MutableLiveData<Boolean> isTaskReady = new MutableLiveData<>(false);
    public MutableLiveData<Boolean> isOrderCreated = new MutableLiveData<>(false);


    public void getMenu() {
        menuList.clear();
        dataBase.collection("Dishes").get()
                .addOnSuccessListener(task -> {
                    for (DocumentSnapshot documentSnapshot : task.getDocuments()) {
                        MenuModel data = documentSnapshot.toObject(MenuModel.class);
                        data.setId(documentSnapshot.getId());
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

    public void createOrder(OrderModel order){
        AtomicInteger count = new AtomicInteger(1);
        dataBase.collection("Orders").get()
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        for(DocumentSnapshot snapshot : task.getResult().getDocuments()){
                            count.getAndIncrement();
                        }
                        order.setId("Order" + count);
                        dataBase.collection("Orders").document(order.getId()).set(order)
                                .addOnCompleteListener(task1 -> {
                                    if(task1.isSuccessful()){
                                        isOrderCreated.setValue(true);
                                    }
                                });
                    }
                    else{
                        Log.d("ERROR", task.getException().getMessage());
                    }

                })
                .addOnFailureListener(exception -> {
                    Log.d("ERROR", exception.getMessage());
                });

    }

}
