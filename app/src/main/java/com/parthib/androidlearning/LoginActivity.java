package com.parthib.androidlearning;


import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;


import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.parthib.androidlearning.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity {

    private TextInputLayout passLayout, emailLayout;
    private TextInputEditText passInput, emailInput;
    String pass, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding binding;

        binding= ActivityLoginBinding.inflate(getLayoutInflater());

        setContentView(binding.getRoot());

        passLayout =binding.layoutPassword;
        emailLayout = binding.layoutEmail;
        passInput = binding.inputPassword;
        emailInput = binding.inputEmail;

        binding.btnSignUp.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
            LoginActivity.this.finish();
        });

        // btn
        binding.btnSignIn.setOnClickListener(view -> {

            pass = String.valueOf(passInput.getText());
            email = String.valueOf(emailInput.getText());

            //null
            passLayout.setError(null);
            emailLayout.setError(null);

            if(doValidation()) {

                FirebaseAuth mAuth; // declaring a object for firebase auth instance
                mAuth = FirebaseAuth.getInstance(); // defining the auth instance

                mAuth.signInWithEmailAndPassword(email, pass)
                        .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {

                                    FirebaseUser fUser = mAuth.getCurrentUser(); // get whose email to verify
                                    //boolean check = fUser.isEmailVerified();
                                    if (fUser != null && fUser.isEmailVerified()) {
                                        // if email is verified

                                        StoreInfo obj = new StoreInfo(LoginActivity.this);
                                        obj.storeId(mAuth.getUid()); // storing details in

                                        Toast.makeText(LoginActivity.this, "Sign In Successful", Toast.LENGTH_LONG).show();
                                        //nav
                                        startActivity(new Intent(LoginActivity.this, BottomNavActivity.class));
                                        LoginActivity.this.finish(); //destroy the activity
                                    }
                                    else {
                                        // .. in case email is not verified
                                        Toast.makeText(LoginActivity.this, "Please verify your email!", Toast.LENGTH_LONG).show();
                                    }
                                }
                                else {
                                    Toast.makeText(LoginActivity.this, "Sign In Failed", Toast.LENGTH_LONG).show();
                                }
                        })
                        .addOnFailureListener(e -> {
                            Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        });

            }
        });
    }

    private boolean doValidation() {
        if(pass.isEmpty())
            passLayout.setError("Password must be provided");
        if(email.isEmpty())
            emailLayout.setError("Email can't be blank.");
        else
            return true;
        return false;
    }
}