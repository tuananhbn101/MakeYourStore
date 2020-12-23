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

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_Report;
import object_App.Report;

public class Report_Fragment extends Fragment {
    ActivityReportBinding binding;
    Adapter_Report adapter_report;
    List<Report> reportList;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
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
        reportList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        reportList = sqLite_manage_your_store.getAllReport();
        adapter_report = new Adapter_Report(reportList);
        binding.lvReport.setAdapter(adapter_report);
        binding.soluonggiaodich.setText(reportList.size()+"");
        long lai = 0;
        for (Report report: reportList
             ) {
            lai += (report.getTotalSale()-report.getTotalImport());
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(lai)+" đ";
        binding.tvInterestRate.setText(price);
        return binding.getRoot();
    }
}
