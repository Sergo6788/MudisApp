package com.example.mudisapp.fragment;

import static androidx.navigation.Navigation.findNavController;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import android.text.InputType;
import android.text.TextUtils;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.mudisapp.R;
import com.example.mudisapp.activity.DrawerActivity;
import com.example.mudisapp.app.App;
import com.example.mudisapp.databinding.FragmentLoginBinding;
import com.example.mudisapp.model.FireStoreUser;

import com.example.mudisapp.repository.FirebaseRepository;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.PasswordAuthentication;


public class LoginFragment extends Fragment {
    private FragmentLoginBinding binding;
    private boolean isPasswordVisible = true;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private final FirebaseFirestore dataBase = FirebaseFirestore.getInstance();
    private FirebaseRepository firebaseRepository;


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
            findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });

        binding.btSignIn.setOnClickListener(v -> {
            checkEnterData();
        });
        binding.tvButtonForgot.setOnClickListener(v -> {
            showForgotPasswordDialog();
        });

        binding.eyePassword.setOnClickListener(v -> {

            if (isPasswordVisible) {
                binding.eyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_opened_img_svg));

                binding.etPassword.setTransformationMethod(null);
            } else {
                binding.eyePassword.setImageDrawable(requireContext().getDrawable(R.drawable.eye_closed_img_svg));

                binding.etPassword.setTransformationMethod(new PasswordTransformationMethod());
            }
            isPasswordVisible = !
                    isPasswordVisible;
        });

    }

    private void checkEnterData() {
        if (!Patterns.EMAIL_ADDRESS.matcher(binding.etEmail.getText()).matches()) {
            Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.email_incorrect), Toast.LENGTH_SHORT).show();
        } else if (binding.etPassword.getText().toString().length() < 6 || binding.etPassword.getText().toString().contains(" ")) {
            Toast.makeText(requireContext(), requireContext().getResources().getString(R.string.password_length_must_be_more_than_6_characters_and_without_space), Toast.LENGTH_SHORT).show();
        } else {
            allCorrect();
        }
    }


    private void allCorrect() {
        mAuth = FirebaseAuth.getInstance();
        mAuth.signInWithEmailAndPassword(binding.etEmail.getText().toString(), binding.etPassword.getText().toString()).addOnCompleteListener(requireActivity(), task -> {
            binding.btSignIn.setEnabled(false);
            if (task.isSuccessful()){
                if(mAuth.getCurrentUser().isEmailVerified()){
                    createUser();
                }
                else {
                    Toast.makeText(requireContext(), "Please verify your email" , Toast.LENGTH_LONG).show();
                }
                binding.btSignIn.setEnabled(true);
            } else {
                binding.btSignIn.setEnabled(true);
                Toast.makeText(requireContext(), "Login failed: " + (task.getException() != null ? task.getException().getMessage() : "Unknown error"), Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(requireActivity(), error -> {
            binding.btSignIn.setEnabled(true);
            Toast.makeText(requireContext(), "Error:" + error.getMessage(), Toast.LENGTH_LONG).show();
        });

    }

    private void createUser() {
        FireStoreUser user = new FireStoreUser();
        String uid = mAuth.getCurrentUser().getUid();
        String email = mAuth.getCurrentUser().getEmail();

        user.setUid(uid);
        user.setEmail(email);
        user.setNickName(getFirstUserName(email));

        dataBase.collection("Users").document(uid).set(user)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        requireContext().startActivity(new Intent(requireActivity(), DrawerActivity.class));
                        requireActivity().finish();
                        App.sharedManager.userAuthorize();
                        App.sharedManager.saveUID(uid);
                    } else {
                        binding.btSignIn.setEnabled(true);
                        Toast.makeText(requireContext(), "Failed to save user data. Please try again", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    binding.btSignIn.setEnabled(true);
                    Toast.makeText(requireContext(), "Error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                });
    }

    private void showForgotPasswordDialog() {
        FrameLayout container = new FrameLayout(requireContext());
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
        );
        params.setMargins(
                (int) (24 * getResources().getDisplayMetrics().density), // left и right
                (int) (16 * getResources().getDisplayMetrics().density),  // top
                (int) (24 * getResources().getDisplayMetrics().density), // right
                (int) (4 * getResources().getDisplayMetrics().density)   // bottom
        );

        EditText emailEditText = new EditText(requireContext());
        emailEditText.setLayoutParams(params);
        emailEditText.setPaddingRelative(16, 16, 16, 16);
        emailEditText.setBackgroundResource(R.drawable.edit_text_background);
        emailEditText.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        emailEditText.setHint("Enter your email");
        container.addView(emailEditText);

        new MaterialAlertDialogBuilder(requireContext())
                .setTitle("Forgot Password")
                .setMessage("Please enter your registered email to reset your password.")
                .setView(container)
                .setPositiveButton("Send", (dialog, which) -> {
                    String email = emailEditText.getText().toString().trim();
                    if (TextUtils.isEmpty(email)) {
                        Toast.makeText(requireContext(), "Email cannot be empty", Toast.LENGTH_SHORT).show();
                    } else {
                        sendPasswordResetEmail(email);
                    }
                })
                .setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss())
                .setCancelable(false)
                .show();

    }

    private void sendPasswordResetEmail(String email) {
        System.out.println(email);
        mAuth.sendPasswordResetEmail(email)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(requireContext(), "Reset email sent successfully to " + email, Toast.LENGTH_SHORT).show();
                    } else {
                        String errorMessage = task.getException() != null ? task.getException().getMessage() : "Unknown error occurred";
                        Toast.makeText(requireContext(), "Failed to send reset email: " + errorMessage, Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getFirstUserName(String email){
        StringBuilder stringBuilder = new StringBuilder();

        for(char letter : email.toCharArray()){
            if(letter == '@'){
                break;
            }
            stringBuilder.append(letter);
        }
        return  stringBuilder.toString();
    }

}