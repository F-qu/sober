package com.example.sober;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.sober.databinding.FragmentDayListBinding;

public class DayList extends Fragment {
    FragmentDayListBinding fragmentDayListBinding;
    public DayList() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        fragmentDayListBinding = FragmentDayListBinding.inflate(inflater, container, false);
        return fragmentDayListBinding.getRoot();
    }
}