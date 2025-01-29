package com.example.mudisapp.repository;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.mudisapp.app.App;
import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.model.OrderModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firestore.v1.StructuredAggregationQuery;
import com.google.firestore.v1.StructuredQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class FirebaseRepository extends ViewModel {
    private FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private ArrayList<MenuModel> menuList = new ArrayList<>();
    private ArrayList<OrderModel> orderList = new ArrayList<>();
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

    public ArrayList<MenuModel> getMenuList() {
        isTaskReady.setValue(false);
        return menuList;
    }

    public void createOrder(OrderModel order) {
        AtomicInteger count = new AtomicInteger(1);
        dataBase.collection("Orders").get()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        for (DocumentSnapshot snapshot : task.getResult().getDocuments()) {
                            count.getAndIncrement();
                        }
                        order.setId("Order" + count);
                        order.setNumber(count.get());
                        dataBase.collection("Orders").document(order.getId()).set(order)
                                .addOnCompleteListener(task1 -> {
                                    if (task1.isSuccessful()) {
                                        isOrderCreated.setValue(true);
                                    }
                                });
                    } else {
                        Log.d("ERROR", task.getException().getMessage());
                    }

                })
                .addOnFailureListener(exception -> {
                    Log.d("ERROR", exception.getMessage());
                });

    }

    public void getOrderHistory() {
        orderList.clear();
        dataBase.collection("Orders").get()
                .addOnSuccessListener(task -> {
                    for (DocumentSnapshot documentSnapshot : task.getDocuments()) {
                        OrderModel data = documentSnapshot.toObject(OrderModel.class);
                        data.setId(documentSnapshot.getId());
                        orderList.add(data);
                    }
                    isTaskReady.setValue(true);
                })
                .addOnFailureListener(error -> {
                    Log.d("ERROR", error.getMessage());
                });
    }

    public ArrayList<OrderModel> getOrderList() {
        isTaskReady.setValue(false);
        orderList.removeIf(it -> !Objects.equals(it.getUid(), App.sharedManager.getUID()));
        return orderList;
    }


    public void uploadImage(String url){
        
    }

}




