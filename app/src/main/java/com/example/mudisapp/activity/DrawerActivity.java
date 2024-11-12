package com.example.mudisapp.activity;

import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.navigation.ui.NavigationUI;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.ActivityDriverBinding;

public class DrawerActivity extends AppCompatActivity {
   private ActivityDriverBinding binding;

    NavHostFragment navHostFragment;
    NavController navController;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.nav_main_fragment);
        navController = navHostFragment.getNavController();
        NavigationUI.setupWithNavController(binding.navView,navController);
        applyClick();
    }

    private void applyClick()
    {
        binding.btnDrawer.setOnClickListener(v -> {
            openDriver();
        });

    }
    public void hideDrawer(boolean isDrawerNeedHide){
        if(isDrawerNeedHide)
            binding.btnDrawer.setVisibility(View.GONE);
        else
            binding.btnDrawer.setVisibility(View.VISIBLE);
    }

    private void openDriver(){
        binding.getRoot().openDrawer(GravityCompat.START);
    }
}

