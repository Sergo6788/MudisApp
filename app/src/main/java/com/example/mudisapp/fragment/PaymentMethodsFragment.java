package com.example.mudisapp.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentPaymentMethodsBinding;
import com.example.mudisapp.enums.PaymentMethod;

import java.util.Objects;

public class PaymentMethodsFragment extends Fragment {
    public FragmentPaymentMethodsBinding binding;
    String paymentMethod = App.sharedManager.getPaymentMethod();

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentPaymentMethodsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupView();
        applyClick();
    }

    private void applyClick(){
        binding.ivArrowBack.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        binding.rbApplePay.setOnClickListener(v -> {
            App.sharedManager.savePaymentMethod(PaymentMethod.APPLE_PAY);
        });
        binding.rbGooglePay.setOnClickListener(v -> {
            App.sharedManager.savePaymentMethod(PaymentMethod.GOOGLE_PAY);
        });
        binding.rbCash.setOnClickListener(v -> {
            App.sharedManager.savePaymentMethod(PaymentMethod.CASH);
        });


    }
    private void setupView(){
        if(Objects.equals(paymentMethod, "APPLE_PAY")) {binding.rbApplePay.setChecked(true);}
        else if(Objects.equals(paymentMethod, "GOOGLE_PAY")) {binding.rbGooglePay.setChecked(true);}
        else{binding.rbCash.setChecked(true);}
    }

}
