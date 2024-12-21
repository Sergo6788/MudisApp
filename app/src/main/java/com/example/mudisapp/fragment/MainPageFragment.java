package com.example.mudisapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.adapter.FoodAdapter;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentMainPageBinding;

import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.repository.FirebaseRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainPageFragment extends Fragment implements FoodAdapter.OnClickListener {
    public FragmentMainPageBinding binding;
    private FirebaseRepository firebaseDataBase;
    private AlertDialog materialDialog;
    private MenuModel currentDish;
    private ArrayList<MenuModel> list = new ArrayList<>();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentMainPageBinding.inflate(inflater);
        firebaseDataBase = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setObservers();
        materialDialog = createMaterialDialog();
        firebaseDataBase.getMenu();
        setView();
        applyClick();
    }
    public void applyClick(){
        binding.categoryAllContainer.setOnClickListener(v -> {
            setCategoryColor(binding.categoryAllContainer, binding.underlineAll);
            setAdapter(firebaseDataBase.getMenuList());
        });

        binding.categorySnacksContainer.setOnClickListener(v -> {
            setCategoryColor(binding.categorySnacks, binding.underlineSnacks);
            ArrayList<MenuModel> filteredList = filterMenu("SNACK");
            binding.rvMenu.setAdapter(new FoodAdapter(filteredList, this, requireContext()));
        });
        binding.categoryDrinks.setOnClickListener(v -> {
            setCategoryColor(binding.categoryDrinks, binding.underlineDrinks);
            ArrayList<MenuModel> filteredList = filterMenu("DRINK");
            binding.rvMenu.setAdapter(new FoodAdapter(filteredList, this, requireContext()));
        });
        binding.categoryMeals.setOnClickListener(v -> {
            setCategoryColor(binding.categoryMeals, binding.underlineMeals);
            ArrayList<MenuModel> filteredList = filterMenu("MEAL");
            binding.rvMenu.setAdapter(new FoodAdapter(filteredList, this, requireContext()));
        });

    }
    private void setAdapter(List<MenuModel> list) {
        binding.rvMenu.setLayoutManager(new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false));
        binding.rvMenu.setAdapter(new FoodAdapter(list,this, requireContext()));
    }

    @Override
    public void click(MenuModel menuItem) {

    }

    @Override
    public void decrease(MenuModel menuItem) {
        currentDish = menuItem;
        showMaterialDialog();
    }

    private void setObservers(){
        firebaseDataBase.isTaskReady.observe(getViewLifecycleOwner(), data -> {
            if (data) {
                binding.pbLoad.setVisibility(View.GONE);
                setAdapter(firebaseDataBase.getMenuList());
                firebaseDataBase.isTaskReady.setValue(false);
            }
        });
    }

    private ArrayList<MenuModel> filterMenu(String type){
        ArrayList<MenuModel> filteredList = new ArrayList<>();
        for(MenuModel item : firebaseDataBase.getMenuList())
        {
            if(item.getType().equals(type)) {filteredList.add(item);}
        }
        return filteredList;
    }
    private void setView(){
        binding.categoryAllContainer.setTextColor(getResources().getColor(R.color.orange));
        binding.underlineAll.setBackgroundColor(getResources().getColor(R.color.orange));
    }
    private void setCategoryColor(TextView selectedTextView, View selectedView){
        binding.categoryAllContainer.setTextColor(getResources().getColor(R.color.grey_text_order_history));
        binding.underlineAll.setBackgroundColor(getResources().getColor(R.color.grey_text_order_history));

        binding.categoryMeals.setTextColor(getResources().getColor(R.color.grey_text_order_history));
        binding.underlineMeals.setBackgroundColor(getResources().getColor(R.color.grey_text_order_history));

        binding.categorySnacks.setTextColor(getResources().getColor(R.color.grey_text_order_history));
        binding.underlineSnacks.setBackgroundColor(getResources().getColor(R.color.grey_text_order_history));

        binding.categoryDrinks.setTextColor(getResources().getColor(R.color.grey_text_order_history));
        binding.underlineDrinks.setBackgroundColor(getResources().getColor(R.color.grey_text_order_history));


        selectedTextView.setTextColor(getResources().getColor(R.color.orange));
        selectedView.setBackgroundColor(getResources().getColor(R.color.orange));
    }
    public void showMaterialDialog(){
        materialDialog.show();
    }
    private AlertDialog createMaterialDialog(){
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Delete dish?")
                .setMessage("Do u want to delete this dish from the cart?")
                .setPositiveButton("Yes", (d, which)->{
                    App.sharedManager.saveToCart(currentDish, 0);

                })
                .setNegativeButton("No",(d, which)->{})
                .setCancelable(false);
        return dialog.create();
    }

}