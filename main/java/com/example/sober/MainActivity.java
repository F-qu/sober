package com.example.sober;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sober.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding activityMainBinding;
    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityMainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        View view = activityMainBinding.getRoot();
        setContentView(view);

        //---- Set Bottom Navigation with Activities----//
        activityMainBinding.bottomNavigation.setSelectedItemId(R.id.progress);
        activityMainBinding.bottomNavigation.setOnItemSelectedListener(item -> {
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
                    startActivity(new Intent(getApplicationContext(), ProfilePge.class));
                    overridePendingTransition(0,0);
                    return true;

                case R.id.progress:
                    return true;
            }

            return false;
        });
    }

}