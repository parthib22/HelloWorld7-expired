package com.parthib.androidlearning;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.parthib.androidlearning.databinding.ActivityMainBinding;
import com.parthib.androidlearning.models.UserDetails;

public class MainActivity extends AppCompatActivity {

    private TextInputLayout nameLayout, phoneLayout, passLayout, emailLayout;
    private TextInputEditText nameInput, phoneInput, passInput, emailInput;
    String name, pass, email, mobile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState); //executing the super class method.
        ActivityMainBinding binding; //class - object

        binding=ActivityMainBinding.inflate(getLayoutInflater());// creating the instance of binding attached
        setContentView(binding.getRoot()); //specifying the relevant view for activity

        // for handling the state of the user
        if(!new StoreInfo(MainActivity.this).getId().isEmpty()){
            startActivity(new Intent(MainActivity.this, BottomNavActivity.class));
            MainActivity.this.finish();
        }
        //map the views of the layout with the variables
        nameLayout = binding.layoutName;
        phoneInput = binding.inputPhone;
        phoneLayout = binding.layoutPhone;
        passLayout =binding.layoutPassword;
        emailLayout = binding.layoutEmail;
        nameInput = binding.inputName;
        passInput = binding.inputPassword;
        emailInput = binding.inputEmail;

        binding.btnSignIn.setOnClickListener(v -> {
            startActivity(new Intent(MainActivity.this, LoginActivity.class));
            MainActivity.this.finish();
        });

        // adding the event for the button
        binding.btnSignUp.setOnClickListener(view -> {

            // get the values i.e. user input from fields
            name = String.valueOf(nameInput.getText());
            mobile = String.valueOf(phoneInput.getText());
            pass = String.valueOf(passInput.getText());
            email = String.valueOf(emailInput.getText());

            // nullifying the error portion
            nameLayout.setError(null);
            passLayout.setError(null);
            phoneLayout.setError(null);
            emailLayout.setError(null);

            if(doValidation()){
                // process for the registration procedure
                FirebaseAuth auth = FirebaseAuth.getInstance(); // creating an instance for accessing firebase authentication services
                // creating a new user account via authentication
                auth.createUserWithEmailAndPassword(email, pass)
                        .addOnSuccessListener(authResult -> {
                            if(authResult.getUser() != null){
                                String userId = authResult.getUser().getUid();
                                saveDetails(userId);
                                authResult.getUser().sendEmailVerification(); // send email
                            }
                        })
                        .addOnFailureListener(e ->
                                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show()
                        );
                        //. for object
            }
        });
    }

    private void saveDetails(String userId) {

        // creating the instance of UserDetails
        UserDetails userDetails = new UserDetails(name, userId, mobile, email,"");


        // store the details within database i.e. cloud fire store
        FirebaseFirestore fs = FirebaseFirestore.getInstance();
        CollectionReference ref = fs.collection("User_details"); // create the collection if not present, if present refer to that
        ref.document(userId) // document of the user
                .set(userDetails)
                .addOnCompleteListener(task -> {
                    if(task.isSuccessful()) {
                        Toast.makeText(MainActivity.this, "Account created successfully", Toast.LENGTH_SHORT).show();
                        //navigate to different screen
                        Intent route = new Intent(MainActivity.this, BottomNavActivity.class);
                        startActivity(route);
                        // destroy current activity & context
                        MainActivity.this.finish();
                    }
                    else
                        Toast.makeText(MainActivity.this, "Sorry process not completed\nTry again later.", Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e ->
                        Toast.makeText(MainActivity.this, e.getMessage() , Toast.LENGTH_SHORT).show());
    }

    private boolean doValidation() {
        if(name.isEmpty())
            nameLayout.setError("Name can't be blank.");
        else if(mobile.trim().length() != 10)
            phoneLayout.setError("Provide proper mobile number.");
        else if(pass.isEmpty())
            passLayout.setError("Password must be provided");
        else if(email.isEmpty())
            emailLayout.setError("Email can't be blank.");
        else
            return true;
        return false;
    }
}