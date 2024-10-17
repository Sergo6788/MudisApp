package com.example.mudisapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentLoginBinding;

import java.net.PasswordAuthentication;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private boolean isPasswordVisible = true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentLoginBinding.inflate(inflater);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }

    private void applyClick() {
        binding.tvButtonSignUp.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        binding.btSignIn.setOnClickListener(v -> {
            checkEnterData();
        });
        binding.tvButtonForgot.setOnClickListener(v -> {

        });
        binding.pictureLogo.setOnClickListener(v -> {

            if (isPasswordVisible) {
                binding.pictureLogo.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.eye_opened_img_svg));

                binding.etPassword.setTransformationMethod(null);
            }

            else {
                binding.pictureLogo.setImageDrawable(ContextCompat.getDrawable(getContext(),R.drawable.eye_closed_img_svg));

                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordVisible=!
                    isPasswordVisible;


        });


    }

    private void checkEnterData() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()) {
            Toast.makeText(requireContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length() < 6 || !binding.etPassword.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), "Password length must be more than 6 characters and without space", Toast.LENGTH_SHORT).show();
        } else {
            allCorrect();
        }
    }

    private void allCorrect() {

    }
}