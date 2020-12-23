package com.example.makeyourstore;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.databinding.HistorySellFragmentBinding;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.Adapter_History_Sell;
import object_App.Bill;

public class History_Sell_Framgmet extends Fragment {
    HistorySellFragmentBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Bill> billList;
    Adapter_History_Sell adapter_history_sell;
    public static History_Sell_Framgmet newInstance() {

        Bundle args = new Bundle();

        History_Sell_Framgmet fragment = new History_Sell_Framgmet();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.history_sell_fragment,container,false);
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        billList = new ArrayList<>();
        billList = sqLite_manage_your_store.getAllBill();
        Collections.reverse(billList);
        adapter_history_sell = new Adapter_History_Sell(billList);
        binding.lvHistory.setAdapter(adapter_history_sell);
        return binding.getRoot();
    }
}
