package com.example.makeyourstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.databinding.ActivityReportBinding;

public class Report_Fragment extends Fragment {
    ActivityReportBinding binding;
    public static Report_Fragment newInstance() {

        Bundle args = new Bundle();

        Report_Fragment fragment = new Report_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_report,container,false);
        return binding.getRoot();
    }
}
