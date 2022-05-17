package com.example.sober;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sober.databinding.LoginBinding;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {
    private LoginBinding loginBinding;
    private FirebaseAuth mAuth;

    private GoogleSignInClient mGoogleSignInClient;

    private static final String TAG = "EmailPassword";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = LoginBinding.inflate(getLayoutInflater());
        View view = loginBinding.getRoot();
        setContentView(view);
        progressDialog = new ProgressDialog(this);

        // [START config_signIn]
        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        // [END config_signIn]

        // [START initialize_auth]
        mAuth = FirebaseAuth.getInstance();

        loginBinding.noAccountRegister.setOnClickListener(v -> {
            startActivity(new Intent(Login.this, SignUp.class));
        });

        loginBinding.login.setOnClickListener(v -> {
            login();
        });
        loginBinding.google.setOnClickListener(v -> {
            signInWithGoogle();
        });
    }

// Google sign In Start

    private void signInWithGoogle() {
        Intent intent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(intent, 100);
    }

    // [START onActivityResult]
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == 100) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                finish();
                startActivity(new Intent(this, Pledges.class));
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    // [END onActivityResult]
// Google sign In End

    private void login() {
        String email = loginBinding.emailTextInput.getText().toString().trim();
        String password = loginBinding.passwordTextInput.getText().toString().trim();

        if (email.isEmpty() || !email.matches(emailPattern)) {
            loginBinding.emailTextInputLayout.setError("Enter a valid email");
        } else if (password.isEmpty() || password.length() < 8) {
            loginBinding.passwordTextInputLayout.setError("Password should have at least 8 characters (letters and digits) ");
        } else {
            /*------------ Message while performing registration-----------------*/
            progressDialog.setMessage("Registering ...");
            progressDialog.setTitle("Sober App Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            // [START sign_in_with_email]
            mAuth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, check email verification
                            FirebaseUser user = mAuth.getCurrentUser();
                            if(!user.isEmailVerified()){
                                Toast.makeText(Login.this, "Authentication Successful.",
                                        Toast.LENGTH_SHORT).show();
                            }else {
                                user.sendEmailVerification();
                                Toast.makeText(Login.this, "Check your email to verify your account.",
                                        Toast.LENGTH_SHORT).show();
                            }
                            progressDialog.dismiss();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(Login.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                        progressDialog.dismiss();
                    });
            // [END sign_in_with_email]
        }
    }


}