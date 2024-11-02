package com.example.mudisapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentCartBinding;
import com.example.mudisapp.databinding.FragmentLoginBinding;


public class CartFragment extends Fragment {
    public FragmentCartBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater);
        return binding.getRoot();
    }
}