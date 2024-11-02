package com.example.mudisapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.ActivityDriverBinding;

public class DrawerActivity extends AppCompatActivity {
   private ActivityDriverBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityDriverBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        setContentView(R.layout.activity_driver);

    }
}