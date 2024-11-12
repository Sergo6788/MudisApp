package com.example.mudisapp.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentProfileBinding;


public class ProfileFragment extends Fragment {
    public FragmentProfileBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }

    private void applyClick(){
        binding.myAccountLayout.setOnClickListener(v -> {
            Toast.makeText(requireContext(), "Coming soon", Toast.LENGTH_SHORT).show();
        });
        binding.myOrderHistoryLayout.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_profileFragment_to_orderHistoryFragment);
        });
        binding.PaymentMethodLayout.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_profileFragment_to_paymentMethodsFragment);
        });

    }
}