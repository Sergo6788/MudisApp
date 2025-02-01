package com.example.mudisapp.fragment;


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
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentRegisterBinding;
import com.google.firebase.auth.FirebaseAuth;


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
            Toast.makeText(requireContext(), R.string.email_incorrect, Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length() < 6 || binding.etPassword.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), R.string.password_length_must_be_more_than_6_symbols_and_without_spaces, Toast.LENGTH_SHORT).show();
        } else if (!binding.etPassword.getText().toString().equals(binding.etPasswordRepeat.getText().toString()))
            Toast.makeText(requireContext(), R.string.password_must_be_the_same, Toast.LENGTH_SHORT).show();
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
                        if (mAuth.getCurrentUser() != null) {
                            mAuth.getCurrentUser().sendEmailVerification();
                        }
                        Toast.makeText(requireContext(), R.string.registration_successful_verification_email_sent, Toast.LENGTH_SHORT).show();
                        Navigation.findNavController(requireActivity(), R.id.nav_main_fragment).popBackStack();
                    } else {
                        binding.btSignUp.setEnabled(true);
                        Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.user_registration_failed), Toast.LENGTH_SHORT).show();
                    }
                });
    }

}








