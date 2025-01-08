package com.example.mudisapp.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.activity.DrawerActivity;
import com.example.mudisapp.databinding.FragmentProfileBinding;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;


public class ProfileFragment extends Fragment {
    public FragmentProfileBinding binding;
    private AlertDialog materialDialog;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentProfileBinding.inflate(inflater);
        materialDialog = createMaterialDialog();
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }

    private void applyClick(){
        binding.myOrderHistoryLayout.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_profileFragment_to_orderHistoryFragment);
        });
        binding.PaymentMethodLayout.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_profileFragment_to_paymentMethodsFragment);
        });
        binding.myFavoritesLayout.setOnClickListener(v -> {
           findNavController(v).navigate(R.id.action_profileFragment_to_favoritesFragment);
        });
        binding.logOutLayout.setOnClickListener(v -> {
            showMaterialDialog();
        });

    }
    public void showMaterialDialog(){
        materialDialog.show();
    }
    private AlertDialog createMaterialDialog(){
        MaterialAlertDialogBuilder dialog = new MaterialAlertDialogBuilder(requireActivity())
                .setTitle("Log out?")
                .setMessage("Are you sure you want to log out?")
                .setPositiveButton("Yes", (d, which)->{
                    findNavController(requireView()).navigate(R.id.action_profileFragment_to_exitFragment);
                })
                .setNegativeButton("No",(d, which)->{})
                .setCancelable(false);
        return dialog.create();
    }

}