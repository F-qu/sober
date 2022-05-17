package com.example.sober;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.sober.databinding.ActivityProfilePgeBinding;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ProfilePge extends AppCompatActivity {
    private GoogleSignInClient mGoogleSignInClient;

    private ActivityProfilePgeBinding activityProfilePgeBinding;
    private FirebaseUser user;
    private DatabaseReference reference;
    private String userId;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityProfilePgeBinding = ActivityProfilePgeBinding.inflate(getLayoutInflater());
        View view = activityProfilePgeBinding.getRoot();
        setContentView(view);

        //---- Set Bottom Navigation with Activities----//
        activityProfilePgeBinding.bottomNavigation.setSelectedItemId(R.id.profile);
        activityProfilePgeBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.pledges:
                    startActivity(new Intent(getApplicationContext(), Pledges.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.community:
                    startActivity(new Intent(getApplicationContext(), Community.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.profile:
                    return true;

                case R.id.progress:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });
        // Display User Information From FIREBASE
        user = FirebaseAuth.getInstance().getCurrentUser();

        //Get database collection reference
        reference = FirebaseDatabase.getInstance().getReference("User");
        reference.keepSynced(true);

        if(user != null){
            userId= user.getUid();
            //Go to database reference access child
            reference.child(userId).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    User userInfo = snapshot.getValue(User.class);

                    if(userInfo != null){
                        String name = userInfo.fullName;
                        String email = userInfo.email;

                        activityProfilePgeBinding.userName.setText(name);
                        activityProfilePgeBinding.userEmail.setText(email);
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {
                    Toast.makeText(ProfilePge.this, "Failed to read database",
                            Toast.LENGTH_SHORT).show();
                }
            });
        }else {
            // Login
            startActivity(new Intent(getApplicationContext(), Login.class));
        }
    }
}
