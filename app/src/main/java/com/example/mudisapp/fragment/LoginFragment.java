package com.example.mudisapp.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.app.Activity;
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
import android.widget.EditText;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentLoginBinding;
import com.google.firebase.auth.FirebaseAuth;

import java.net.PasswordAuthentication;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private boolean isPasswordVisible = true;
    private FirebaseAuth auth;
    private EditText etEmail;
    private EditText etPassword;



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

    private void applyClick()
    {
        binding.tvButtonSignUp.setOnClickListener(v -> {
            findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
        binding.btSignIn.setOnClickListener(v -> {
            checkEnterData();
        });
        binding.tvButtonForgot.setOnClickListener(v -> {

        });
        binding.eyePassword.setOnClickListener(v -> {

            if (isPasswordVisible) {
                binding.eyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_opened_img_svg));

                binding.etPassword.setTransformationMethod(null);
            }

            else {
                binding.eyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_closed_img_svg));

                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordVisible=!
                    isPasswordVisible;


        });



    }


    private void checkEnterData() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches())
        {
            Toast.makeText(requireContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
        }
        else if (binding.etPassword.getText().toString().length() < 6 || binding.etPassword.getText().toString().contains(" "))
        {

            Toast.makeText(requireContext(), "Password length must be more than 6 characters and without space", Toast.LENGTH_SHORT).show();
        }
        else
        {
            allCorrect();
        }
    }

    private void allCorrect()
    {
        auth=FirebaseAuth.getInstance();
        auth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(requireActivity(), task -> {
            binding.btSignIn.setEnabled(false);
            if(task.isSuccessful()) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_loginFragment_to_mainPageFragment);
                App.sharedManager.userAuthorize();
            }
        }).addOnFailureListener(requireActivity(),error->{
            binding.btSignIn.setEnabled(true);
            Toast.makeText(requireContext(), error.getMessage(), Toast.LENGTH_LONG).show();
        });

    }
}