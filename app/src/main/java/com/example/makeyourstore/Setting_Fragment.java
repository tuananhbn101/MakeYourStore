package com.example.makeyourstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.databinding.ActivitySettingBinding;

import Activity.Add_Product;

public class Setting_Fragment extends Fragment {
    ActivitySettingBinding binding;

    public static Setting_Fragment newInstance() {

        Bundle args = new Bundle();

        Setting_Fragment fragment = new Setting_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_setting, container, false);
        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_Product.class);
                startActivity(intent);
            }
        });
        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_Product.class);
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }
}
