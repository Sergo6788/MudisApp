package com.example.mudisapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.adapter.FoodAdapter;
import com.example.mudisapp.databinding.FragmentMainPageBinding;

import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.repository.FirebaseRepository;

import java.util.ArrayList;

public class MainPageFragment extends Fragment implements FoodAdapter.OnClickListener {
    public FragmentMainPageBinding binding;
    private FirebaseRepository firebaseDataBase;


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
        firebaseDataBase.getMenu();
        applyClick();
    }
    public void applyClick(){
        binding.rvMenu1.setOnClickListener(v -> {
        });

    }
    private void setAdapter(ArrayList<MenuModel> list) {
        binding.rvMenu1.setLayoutManager(new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false));
        binding.rvMenu1.setAdapter(new FoodAdapter(list,this, requireContext()));
    }

    @Override
    public void click(MenuModel menuItem) {
        Toast.makeText(requireContext(), menuItem.getName(), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void toFavorite(MenuModel menuItem) {
        Toast.makeText(requireContext(), "Added to favorite", Toast.LENGTH_SHORT).show();
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
}