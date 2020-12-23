package com.example.makeyourstore;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.databinding.ManageEmployeesFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adaptor_Update_Employees;
import adapter.Click_Manage_Account;
import login.Create_A_New_Account;
import object_App.Account;

public class Manage_Employees extends Fragment {
    ManageEmployeesFragmentBinding binding;
    List<Account> accountList;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    Adaptor_Update_Employees adaptor_update_employees;
    public static Manage_Employees newInstance() {

        Bundle args = new Bundle();

        Manage_Employees fragment = new Manage_Employees();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.manage_employees_fragment,container,false);
        accountList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        accountList = sqLite_manage_your_store.getAllAccounts();
        List<Account> accountEmployees = new ArrayList<>();
        for (Account account: accountList
             ) {
            if(account.getPermission()==1){
                accountEmployees.add(account);
            }
        }
        if(accountEmployees.size()>0){
            binding.tvNoEmployees.setVisibility(View.INVISIBLE);
            binding.tvCommentE.setVisibility(View.INVISIBLE);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1,RecyclerView.VERTICAL,false);
        adaptor_update_employees = new Adaptor_Update_Employees(accountEmployees);
        binding.rvEmployees.setLayoutManager(layoutManager);
        binding.rvEmployees.setAdapter(adaptor_update_employees);
        adaptor_update_employees.setClick_manage_account(new Click_Manage_Account() {
            @Override
            public void clickBtnDelete(int position) {
                sqLite_manage_your_store.deleteAccount(accountList.get(position));
                accountEmployees.remove(position);
                adaptor_update_employees.notifyDataSetChanged();
                binding.rvEmployees.setLayoutManager(layoutManager);
                binding.rvEmployees.setAdapter(adaptor_update_employees);

            }

            @Override
            public void clickShowInf(int position) {
                    getDialog(accountEmployees.get(position));
            }
        });
        return binding.getRoot();
    }
    public void getDialog(Account account) {
        int selectedYear = 2000;
        int selectedMonth = 5;
        int selectedDayOfMonth = 10;
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.activity_manage__user__information);
        EditText name = dialog.findViewById(R.id.etNewFullNameUp);
        TextView date = dialog.findViewById(R.id.tvDateOfBirthUp);
        EditText phone = dialog.findViewById(R.id.etNewPhoneUp);
        EditText question = dialog.findViewById(R.id.etQuestionUp);
        EditText answer = dialog.findViewById(R.id.etAnswerUp);
        name.setText(account.getFullName());
        date.setText(account.getDateOfBirth());
        phone.setText(account.getPhone());
        question.setText(account.getHomeTown());
        answer.setText(account.getEmail());
        Button button = dialog.findViewById(R.id.btnSubmitUp);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        date.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
                datePickerDialog.show();
            }
        });
        Button button1 = dialog.findViewById(R.id.btnCancleUp);
        button.setVisibility(View.INVISIBLE);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
}
