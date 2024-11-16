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
import com.example.mudisapp.model.FireStoreUser;
import com.example.mudisapp.shared.SharedManager;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.Map;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private boolean isPasswordHidden = true;
    private boolean isRepeatPasswordHidden = true;
    private final FirebaseAuth mAuth = FirebaseAuth.getInstance();




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        applyClick();
    }

    private void applyClick() {
        binding.tvSignin.setOnClickListener(v -> {
            Navigation.findNavController(v).popBackStack();
        });
        binding.btSignUp.setOnClickListener(v -> {
            checkEnterData();
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
        } else if (binding.etPassword.getText().toString().length() < 6 || binding.etPassword.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), "Password length must be more than 6 symbols and without spaces", Toast.LENGTH_SHORT).show();
        } else if (!binding.etPassword.getText().toString().equals(binding.etPasswordRepeat.getText().toString()))
            Toast.makeText(requireContext(), "Password must be the same", Toast.LENGTH_SHORT).show();
        else
            registerUser();
    }


    private void registerUser() {
        String email = binding.etEmail.getText().toString();
        String password = binding.etPassword.getText().toString();
        binding.btSignUp.setEnabled(false);

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "User registration successfully", Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(binding.getRoot()).popBackStack();
                    } else {
                        binding.btSignUp.setEnabled(true);
                        Toast.makeText(requireContext(), "User registration failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                });
    }

}








