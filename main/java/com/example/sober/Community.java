package com.example.sober;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sober.databinding.ActivityCommunityBinding;

public class Community extends AppCompatActivity {
    ActivityCommunityBinding activityCommunityBinding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityCommunityBinding = ActivityCommunityBinding.inflate(getLayoutInflater());
        View view = activityCommunityBinding.getRoot();
        setContentView(view);

        //---- Set Bottom Navigation with Activities----//
        activityCommunityBinding.bottomNavigation.setSelectedItemId(R.id.community);
        activityCommunityBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.pledges:
                    startActivity(new Intent(getApplicationContext(), Pledges.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.community:
                    return true;

                case R.id.profile:
                    startActivity(new Intent(getApplicationContext(), ProfilePge.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.progress:
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }

            return false;
        });
    }
}