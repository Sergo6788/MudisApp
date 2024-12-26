package com.example.mudisapp.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.adapter.CartAdapter;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentCartBinding;
import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.repository.FirebaseRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class CartFragment extends Fragment implements CartAdapter.OnClickListener {
    public FragmentCartBinding binding;
    private AlertDialog materialDialog;
    private MenuModel currentDish;
    private FirebaseRepository firebaseDataBase;
    private ArrayList<MenuModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        firebaseDataBase = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObservers();
        firebaseDataBase.getMenu();
        materialDialog = createMaterialDialog();
        applyClick();
    }

    public void applyClick() {
    }

    private void setAdapter(){
        binding.rvCart.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
        binding.rvCart.setAdapter(new CartAdapter(list,this, requireContext()));
    }

    private void setObservers(){
        firebaseDataBase.isTaskReady.observe(getViewLifecycleOwner(), data -> {
            if (data) {
                list = firebaseDataBase.getMenuList();
                setAdapter();
                firebaseDataBase.isTaskReady.setValue(false);
            }
        });
    }


    @Override
    public void decrease(MenuModel menuItem) {
        currentDish = menuItem;
        showMaterialDialog();
    }

    public void showMaterialDialog(){
        materialDialog.show();
    }
    private AlertDialog createMaterialDialog(){
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Delete dish?")
                .setMessage("Do u want to delete this dish from the cart?")
                .setPositiveButton("Yes", (d, which)->{
                    list.remove(currentDish);
                    App.sharedManager.saveToCart(currentDish, 0);
                    binding.rvCart.getAdapter().notifyDataSetChanged();
                })
                .setNegativeButton("No",(d, which)->{})
                .setCancelable(false);
        return dialog.create();
    }

}