package com.example.sober;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sober.databinding.ActivitySignUpBinding;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Objects;

public class SignUp extends AppCompatActivity {
    private static final String TAG = "EmailPassword";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    private FirebaseAuth mAuth;

    // calling binding class for activity_main.xml
    // which is generated automatically.
    private ActivitySignUpBinding activitySignUpBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // inflating our xml layout in our activity main binding ------------
        activitySignUpBinding = ActivitySignUpBinding.inflate(getLayoutInflater());
       // getting our root layout in our view.-------------
        View view = activitySignUpBinding.getRoot();
        setContentView(view);
         mAuth = FirebaseAuth.getInstance();

        progressDialog = new ProgressDialog(this);

        //--------calling button and setting on click listener for our button.
        // --------we have called our button with its id and set on click listener on it.
        activitySignUpBinding.alreadyHaveAnAccount.setOnClickListener(v -> {
            startActivity(new Intent(SignUp.this, Login.class));
        });

        //--------Sign Up Start---------
        activitySignUpBinding.signUp.setOnClickListener(v -> {
           performAuthentication();
        });
    }

    private void performAuthentication() {
        String name = activitySignUpBinding.nameTextInput.getText().toString().trim();
        String email = activitySignUpBinding.emailTextInput.getText().toString().trim();
        String password = activitySignUpBinding.passwordTextInput.getText().toString().trim();

        /*-------Confirm user data input ---------*/
        if(name.isEmpty()){
            activitySignUpBinding.nameTextInputLayout.setError("Name required");
        }else if(email.isEmpty() || !email.matches(emailPattern)){
            activitySignUpBinding.emailTextInputLayout.setError("Enter a valid email");
        }else if(password.isEmpty() || password.length() < 8){
            activitySignUpBinding.passwordTextInputLayout.setError("Password should have at least 8 characters (letters and digits) ");
        }else {
            /*------------ Message while performing registration-----------------*/
            progressDialog.setMessage("Registering ...");
            progressDialog.setTitle("Sober App Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            // [START create_user_with_email]
            mAuth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            //  if Sign in success, access firebase database and create user collection
                            // and save user info the database

                            User user = new User(name, email);
                            FirebaseDatabase.getInstance().getReference("User")
                                    .child(Objects.requireNonNull(FirebaseAuth.getInstance().getCurrentUser()).getUid())
                                    .setValue(user).addOnCompleteListener(task1 -> {
                                        if(task1.isSuccessful()){
                                            Toast.makeText(SignUp.this, "Authentication successful.", Toast.LENGTH_SHORT ).show();
                                        }else {
                                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                            Toast.makeText(SignUp.this, "Authentication failed.", Toast.LENGTH_SHORT ).show();
                                        }
                                        progressDialog.dismiss();
                            });
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUp.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    });
            // [END create_user_with_email]
        }
    }

}