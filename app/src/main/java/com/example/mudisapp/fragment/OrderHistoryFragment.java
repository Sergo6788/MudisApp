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
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.mudisapp.activity.DrawerActivity;
import com.example.mudisapp.adapter.CartAdapter;
import com.example.mudisapp.adapter.OrderAdapter;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentOrderHistoryBinding;
import com.example.mudisapp.databinding.FragmentProfileBinding;
import com.example.mudisapp.enums.MealType;
import com.example.mudisapp.enums.OrderStatus;
import com.example.mudisapp.enums.PaymentMethod;
import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.model.OrderModel;
import com.example.mudisapp.repository.FirebaseRepository;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderHistoryFragment extends Fragment {
    public FragmentOrderHistoryBinding binding;
    private FirebaseRepository firebaseDataBase;
    ArrayList<OrderModel> list = new ArrayList<>();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentOrderHistoryBinding.inflate(inflater);
        firebaseDataBase = new ViewModelProvider(this).get(FirebaseRepository.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        setObservers();
        firebaseDataBase.getOrderHistory();
    }

    private void applyClick(){
        binding.ivArrowBack.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
    }

    private void setObservers(){
        firebaseDataBase.isTaskReady.observe(getViewLifecycleOwner(), data -> {
            if (data) {
                list = firebaseDataBase.getOrderList();
                setAdapter();
            }
        });
    }

    private void setAdapter(){
        binding.rvOrderHistory.setLayoutManager(new LinearLayoutManager(requireContext()));
        binding.rvOrderHistory.setAdapter(new OrderAdapter(list));
    }


}
