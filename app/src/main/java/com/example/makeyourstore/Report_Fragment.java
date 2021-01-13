package com.example.makeyourstore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.telecom.Connection;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.databinding.ActivityReportBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import adapter.Adapter_Report;
import object_App.Account;
import object_App.Bill;
import object_App.Report;

public class Report_Fragment extends Fragment {
    ActivityReportBinding binding;
    Adapter_Report adapter_report;
    List<Report> reportList;
    List<Bill> billList;
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
        billList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        reportList = sqLite_manage_your_store.getAllReport();
        billList = sqLite_manage_your_store.getAllBill();
        Collections.reverse(reportList);
        adapter_report = new Adapter_Report(reportList);
        binding.lvReport.setAdapter(adapter_report);
        binding.lvReport.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                for (Bill bill: billList
                     ) {
                    if(bill.getID()==reportList.get(position).getID()){
                        getDialog(bill);
                        break;
                    }
                }
            }
        });
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
    public void getDialog(Bill bill) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.history_list);
        TextView idHistory = dialog.findViewById(R.id.tvIDHistory);
        TextView date = dialog.findViewById(R.id.tvDateSell);
        TextView amount = dialog.findViewById(R.id.tvAmountSell);
        TextView name = dialog.findViewById(R.id.tvNameProductSell);
        TextView price = dialog.findViewById(R.id.tvPriceBill);
        TextView total = dialog.findViewById(R.id.tvtotalBill);
        TextView employee = dialog.findViewById(R.id.tvEmployee);
        idHistory.setText(bill.getID()+"");
        date.setText(bill.getDate());
        String nameProduct = "";
        String priceProduct = "";
        String amountProduct = "";
        String nameList[] = bill.getNames().split(";");
        String priceList[] = bill.getPrice().split(";");
        String amountList[] = bill.getAmount().split(";");
        if(nameList.length >1){
            for (int i = 0; i<nameList.length;i++){
                nameProduct += nameList[i]+"\n";
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                String priceFormat = formatter.format(Integer.valueOf(priceList[i]))+" đ";
                priceProduct += priceFormat+"\n";
                amountProduct += amountList[i]+"\n";
            }
        }
        else{
            nameProduct += bill.getNames();
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String priceFormat = formatter.format(Integer.valueOf(bill.getPrice()))+" đ";
            priceProduct += priceFormat;
            amountProduct += bill.getAmount();
        }
        name.setText(nameProduct);
        amount.setText(amountProduct);
        price.setText(priceProduct);
        employee.setText(bill.getIDEmployee()+"");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String totalFormat = formatter.format(bill.getTotal())+" đ";
        total.setText(totalFormat);
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }

}
