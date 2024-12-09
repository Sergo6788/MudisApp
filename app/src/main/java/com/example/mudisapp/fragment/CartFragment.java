package com.example.mudisapp.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.adapter.CartAdapter;
import com.example.mudisapp.adapter.FoodAdapter;
import com.example.mudisapp.databinding.FragmentCartBinding;
import com.example.mudisapp.enums.MealType;
import com.example.mudisapp.model.MenuModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;


public class CartFragment extends Fragment implements CartAdapter.OnClickListener {
    public FragmentCartBinding binding;
    private AlertDialog materialDialog;
    private MenuModel currentDish;
    private ArrayList<MenuModel> list = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter();
        materialDialog = createMaterialDialog();
        applyClick();
    }

    public void applyClick() {
    }

    private void setAdapter(){
        list.add(new MenuModel("https://i.ibb.co/2nTJd93/4BBKkK.jpg", "Pita", 20, MealType.MEAL));
        list.add(new MenuModel("https://i.ibb.co/YD9tsvg/images.jpg", "Coca-Cola", 8, MealType.DRINK));
        list.add(new MenuModel("https://i.ibb.co/rdd8GB3/b-A9-A11-A82-B990-476-B-8-CFC-4-F62-E919-CC8-D.jpg", "Juice", 8, MealType.DRINK));


        binding.rvCart.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL,false));
        binding.rvCart.setAdapter(new CartAdapter(list,this, requireContext()));
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
                    binding.rvCart.getAdapter().notifyDataSetChanged();
                })
                .setNegativeButton("No",(d, which)->{})
                .setCancelable(false);
        return dialog.create();
    }

}