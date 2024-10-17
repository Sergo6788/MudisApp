package com.example.mudisapp.fragment;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater);

        return binding.getRoot();
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }
    private void applyClick(){
        binding.tvSignin.setOnClickListener(v -> {
           Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
      });
        binding.btSignUp.setOnClickListener(v -> {

        });
        binding.ivEyeClosed.setOnClickListener(v -> {

        });
   }

   private void checkEnterData(){
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()){
            Toast.makeText(requireContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length()<6 || !binding.etPassword.getText().toString().contains(" ")) {


        }
    }
}