package com.example.mudisapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.ActivityDriverBinding;

public class DrawerActivity extends AppCompatActivity {
   private ActivityDriverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        applyClick();
    }

    private void applyClick(){

    }

    private void openDriver(){
        binding.getRoot().openDrawer(GravityCompat.START);
    }
}