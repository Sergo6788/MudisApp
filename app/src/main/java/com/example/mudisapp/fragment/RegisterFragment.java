package com.example.mudisapp.fragment;

import static androidx.appcompat.content.res.AppCompatResources.getDrawable;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentRegisterBinding;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private boolean isPasswordHidden = true;
    private boolean isRepeatPasswordHidden = true;
    private FirebaseAuth mAuth;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        applyClick();
    }

    private void applyClick() {
        binding.tvSignin.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
        });
        binding.btSignUp.setOnClickListener(v -> {
            createUser();
        });

        binding.ivEyePassword.setOnClickListener(v -> {

            if (isPasswordHidden) {
                binding.ivEyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_opened_img_svg));

                binding.etPassword.setTransformationMethod(null);

            } else {
                binding.ivEyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_closed_img_svg));

                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordHidden = !isPasswordHidden;


        });
        binding.ivEyeRepeatPassword.setOnClickListener(v -> {

            if (isRepeatPasswordHidden) {
                binding.ivEyeRepeatPassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_opened_img_svg));

                binding.etPasswordRepeat.setTransformationMethod(null);

            } else {
                binding.ivEyeRepeatPassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_closed_img_svg));

                binding.etPasswordRepeat.setTransformationMethod(new PasswordTransformationMethod());
            }
            isRepeatPasswordHidden = !isRepeatPasswordHidden;


        });
    }


    private void checkEnterData() {

        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()) {
            Toast.makeText(requireContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length() < 6 || !binding.etPassword.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), "Password length must be more than 6 symbols and without spaces", Toast.LENGTH_SHORT).show();
        } else if(binding.etPassword != binding.etPasswordRepeat)
            Toast.makeText(requireContext(), "Password must be the same", Toast.LENGTH_SHORT).show();
        else
            allCorect();
    }

    private void allCorect(){

    }

    private void createUser(){
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>(){
            @Override
            public void onComplete(@NonNull Task<AuthResult> task){
                if(task.isSuccessful()) {
                    Toast.makeText(requireContext(), "User registration successfully", Toast.LENGTH_SHORT).show();
                    FirebaseUser user = mAuth.getCurrentUser();
                }
                else {
                    Toast.makeText(requireContext(), "User registration failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }



}








