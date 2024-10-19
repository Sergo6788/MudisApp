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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    TextInputEditText textInputEditTextEmail, textInputEditTextPassword, textInputEditTextRepeatPassword;
    ImageView eyeClosed, eyeOpened;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        eyeClosed = eyeClosed.findViewById(R.id.ivEyeClosed);
        eyeOpened = eyeOpened.findViewById(R.id.ivEyeOpened);
        applyClick();
        checkEnterData();
        checkClickEye();
    }

    private void applyClick(){
        eyeOpened.setVisibility(View.GONE);
        textInputEditTextRepeatPassword.setVisibility(View.INVISIBLE);
        textInputEditTextRepeatPassword.setVisibility(View.INVISIBLE);

        binding.tvSignin.setOnClickListener(v -> {
           Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
      });
        binding.btSignUp.setOnClickListener(v -> {

        });

   }

   private void checkClickEye(){
        binding.ivEyeClosed.setOnClickListener(v -> {
            eyeClosed.setImageResource(R.drawable.eye_open_img_svg);
            textInputEditTextPassword.setVisibility(View.VISIBLE);
            textInputEditTextRepeatPassword.setVisibility(View.VISIBLE);
       });
        binding.ivEyeOpened.setOnClickListener(v -> {
            eyeOpened.setImageResource(R.drawable.eye_closed_img_svg);
            textInputEditTextPassword.setVisibility(View.INVISIBLE);
            textInputEditTextRepeatPassword.setVisibility(View.INVISIBLE);
        });
   }

   private void checkEnterData(){
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()){
            Toast.makeText(requireContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length()<6 || !binding.etPassword.getText().toString().contains(" ")) {


        }
    }

}