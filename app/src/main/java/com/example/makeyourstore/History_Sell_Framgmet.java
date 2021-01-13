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

import Activity.Employee_Main_Activity;
import adapter.Adapter_History_Sell;
import object_App.Account;
import object_App.Bill;

public class History_Sell_Framgmet extends Fragment {
    HistorySellFragmentBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Bill> billList;
    List<Bill> billListSell;
    Employee_Main_Activity employee_main_activity;
    List<Account> accountList;
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
        billListSell = new ArrayList<>();
        employee_main_activity = (Employee_Main_Activity) getActivity();
        int id = employee_main_activity.getID();
        billList = sqLite_manage_your_store.getAllBill();
        for (Bill bill: billList
             ) {
            if(bill.getIDEmployee()==id){
                billListSell.add(bill);
            }
        }
        accountList = new ArrayList<>();
        accountList=sqLite_manage_your_store.getAllAccounts();

        Collections.reverse(billListSell);
        String fullName="";
        for (Account account: accountList
             ) {
            if (account.getID() == employee_main_activity.getID()) {
                fullName+=account.getFullName();
            }
        }
        adapter_history_sell = new Adapter_History_Sell(billListSell,fullName);
        binding.lvHistory.setAdapter(adapter_history_sell);
        return binding.getRoot();
    }
}
