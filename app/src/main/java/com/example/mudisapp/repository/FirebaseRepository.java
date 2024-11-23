package com.example.mudisapp.repository;

import android.util.Log;

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

public class FirebaseRepository {
    private FirebaseFirestore dataBase;
    private Gson gson;
    public FirebaseRepository(FirebaseFirestore dataBase){
        this.dataBase = dataBase;
        this.gson = new Gson();
    }


    public ArrayList<MenuModel> getMenu(){
        Task<QuerySnapshot> query = dataBase.collection("Dishes").get();
        ArrayList<MenuModel> list = new ArrayList<>();
        try {
            QuerySnapshot snapshot = query.getResult();
            for(DocumentSnapshot documentSnapshot: snapshot.getDocuments()){
                MenuModel data = documentSnapshot.toObject(MenuModel.class);
                list.add(data);
            }
        }
        catch (Exception e){
            Log.d("ERROR", e.getMessage().toString());
        }
        return list;
    }

}
