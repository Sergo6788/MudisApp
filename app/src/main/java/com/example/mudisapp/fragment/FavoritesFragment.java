package com.example.mudisapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.GridLayoutManager;

import com.example.mudisapp.activity.DrawerActivity;
import com.example.mudisapp.adapter.FoodAdapter;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentFavoritesBinding;
import com.example.mudisapp.model.MenuModel;
import com.example.mudisapp.repository.FirebaseRepository;

import java.util.ArrayList;
import java.util.HashMap;

public class FavoritesFragment extends Fragment implements FoodAdapter.OnClickListener{
    public FragmentFavoritesBinding binding;
    private ArrayList<MenuModel> favoriteList = App.sharedManager.getListFavorite();
    @Override
    public void click(MenuModel menuItem) {

    }

    @Override
    public void decrease(MenuModel menuItem) {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentFavoritesBinding.inflate(inflater);
        ((DrawerActivity)requireActivity()).hideDrawer(true);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setAdapter(favoriteList);
        applyClick();
    }

    private void setAdapter(ArrayList<MenuModel> list){
        binding.rvFavorites.setLayoutManager(new GridLayoutManager(requireContext(), 3, GridLayoutManager.VERTICAL, false));
        binding.rvFavorites.setAdapter(new FoodAdapter(list,this, requireContext()));
    }

    private void applyClick(){
        binding.ivArrowBack.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        
    }


}
