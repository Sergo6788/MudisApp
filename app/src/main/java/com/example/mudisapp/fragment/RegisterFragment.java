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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.databinding.FragmentRegisterBinding;
import com.google.android.material.textfield.TextInputEditText;


public class RegisterFragment extends Fragment {
    private FragmentRegisterBinding binding;
    private boolean isPasswordHidden = true;
    private boolean isRepeatPasswordHidden = true;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentRegisterBinding.inflate(inflater);
        return binding.getRoot();
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        applyClick();
        checkEnterData();
    }

    private void applyClick(){
        binding.tvSignin.setOnClickListener(v -> {
           Navigation.findNavController(v).navigate(R.id.action_registerFragment_to_loginFragment);
      });
        binding.btSignUp.setOnClickListener(v -> {

        });
        binding.ivEyePassword.setOnClickListener(v -> {
            if(isPasswordHidden){
                binding.ivEyePassword.setImageResource(R.drawable.eye_open_img_svg);
                binding.etPassword.setTransformationMethod(null);

            }
            else{
                binding.ivEyePassword.setImageResource(R.drawable.eye_closed_img_svg);
                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordHidden = !isPasswordHidden;


        });
        binding.ivEyeRepeatPassword.setOnClickListener(v -> {
            if(isRepeatPasswordHidden){
                binding.ivEyeRepeatPassword.setImageResource(R.drawable.eye_open_img_svg);
                binding.etPasswordRepeat.setTransformationMethod(null);

            }
            else{
                binding.ivEyeRepeatPassword.setImageResource(R.drawable.eye_closed_img_svg);
                binding.etPasswordRepeat.setTransformationMethod(new PasswordTransformationMethod());
            }
            isRepeatPasswordHidden = !isRepeatPasswordHidden;


        });
   }


   private void checkEnterData(){
        if(!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()){
            Toast.makeText(requireContext(), "Email Incorrect", Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length()<6 || !binding.etPassword.getText().toString().contains(" ")) {

        }
    }

}