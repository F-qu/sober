package com.example.sober;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.sober.databinding.ActivityPledgesBinding;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

public class Pledges extends AppCompatActivity {

    private ActivityPledgesBinding activityPledgesBinding;

    @SuppressLint("NonConstantResourceId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        activityPledgesBinding = ActivityPledgesBinding.inflate(getLayoutInflater());
        View view = activityPledgesBinding.getRoot();
        setContentView(view);

        //---- Set Bottom Navigation with Activities----//
        activityPledgesBinding.bottomNavigation.setSelectedItemId(R.id.pledges);
        activityPledgesBinding.bottomNavigation.setOnItemSelectedListener(item -> {
            switch (item.getItemId()){
                case R.id.pledges:
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
                    startActivity(new Intent(getApplicationContext(), MainActivity.class));
                    overridePendingTransition(0,0);
                    return true;
            }
            return false;
        });

        ViewPager2 viewPage = activityPledgesBinding.viewPage;
        TabLayout tabLayout = activityPledgesBinding.tabLayout;

        tabLayout.addTab(tabLayout.newTab().setText("Day List"));
        tabLayout.addTab(tabLayout.newTab().setText("Calendar"));
        tabLayout.addTab(tabLayout.newTab().setText("Statistics"));

        viewPage.setAdapter(new FragmentStateAdapter(this) {
            @NonNull
            @Override
            public Fragment createFragment(int position) {

                switch (position){
                    case 0:
                        return new DayList();
                    case 1:
                        return new Calendar();
                    case 2:
                        return new Statistic();
                    default:
                        return null;
                }
            }

            @Override
            public int getItemCount() {
                return tabLayout.getTabCount();
            }
        });

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPage.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
}