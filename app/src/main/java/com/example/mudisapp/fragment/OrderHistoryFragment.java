package com.example.mudisapp.fragment;

import static androidx.core.content.ContentProviderCompat.requireContext;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mudisapp.activity.DrawerActivity;
import com.example.mudisapp.adapter.OrderAdapter;
import com.example.mudisapp.databinding.FragmentOrderHistoryBinding;
import com.example.mudisapp.databinding.FragmentProfileBinding;
import com.example.mudisapp.enums.MealType;
import com.example.mudisapp.enums.OrderStatus;
import com.example.mudisapp.enums.PaymentMethod;
import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.model.OrderModel;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryFragment extends Fragment {
    public FragmentOrderHistoryBinding binding;


    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater);
        ((DrawerActivity)requireActivity()).hideDrawer(true);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        setAdapter();
    }

    private void applyClick(){
        binding.ivArrowBack.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
    }

    private void setAdapter(){
        ArrayList<OrderModel> list = new ArrayList<>();
        ArrayList<MenuModel> listMenuModel = new ArrayList<>();
        listMenuModel.add(new MenuModel("https://i.ibb.co/2nTJd93/4BBKkK.jpg", "Pita", 20, MealType.MEAL));
        listMenuModel.add(new MenuModel("https://i.ibb.co/YD9tsvg/images.jpg", "Coca-Cola", 8, MealType.DRINK));
        listMenuModel.add(new MenuModel("https://i.ibb.co/Z8ZSYz8/111055525062020.jpg", "Sprite", 8, MealType.DRINK));
        listMenuModel.add(new MenuModel("https://i.ibb.co/rdd8GB3/b-A9-A11-A82-B990-476-B-8-CFC-4-F62-E919-CC8-D.jpg", "Juice", 8, MealType.DRINK));
        list.add(new OrderModel(Arrays.asList(listMenuModel.get(0), listMenuModel.get(2)), "12.11.2024", OrderStatus.COMPLETED, PaymentMethod.APPLE_PAY));
        list.add(new OrderModel(Arrays.asList(listMenuModel.get(2), listMenuModel.get(1)), "11.11.2024", OrderStatus.CANCELED, PaymentMethod.CASH));
        list.add(new OrderModel(Arrays.asList(listMenuModel.get(1), listMenuModel.get(2)), "13.11.2024", OrderStatus.InPROCESS, PaymentMethod.GOOGLE_PAY));

        binding.rvOrderHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvOrderHistory.setAdapter(new OrderAdapter(list));
    }


}
