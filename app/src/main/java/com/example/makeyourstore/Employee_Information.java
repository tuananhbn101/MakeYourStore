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

import com.example.makeyourstore.databinding.EmployeeInformationBinding;

import java.util.ArrayList;
import java.util.List;

import Activity.Employee_Main_Activity;
import login.Login_Activity;
import object_App.Account;

public class Employee_Information extends Fragment {
    EmployeeInformationBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;
    Employee_Main_Activity employee_main_activity;
    public static Employee_Information newInstance() {

        Bundle args = new Bundle();

        Employee_Information fragment = new Employee_Information();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.employee_information,container,false);
        accountList= new ArrayList<>();
        employee_main_activity = (Employee_Main_Activity) getActivity();
        String userName = employee_main_activity.getUserName();
        sqLite_manage_your_store =new SQLite_Manage_Your_Store(getContext());
        accountList= sqLite_manage_your_store.getAllAccounts();
        for (Account account: accountList
        ) {
            if(account.getUserName().equals(userName)){
                binding.tvNameEmployee.setText(account.getFullName());
                binding.tvDateOfBirthEmployee.setText(account.getDateOfBirth());
                binding.tvPhoneEmployee.setText(account.getPhone());
            }
        }
        binding.btnLogoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.btnChangePassEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Change_Password_Fragment.class);
                startActivity(intent);
            }
        });
        binding.btnChangeInformationEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Account account: accountList
                ) {
                    if(account.getUserName().equals(userName)){
                        getDialog(account);
                    }
                }
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                account.setFullName(name.getText().toString());
                account.setDateOfBirth(date.getText().toString());
                account.setPhone(phone.getText().toString());
                account.setHomeTown(question.getText().toString());
                account.setEmail(answer.getText().toString());
                sqLite_manage_your_store.updateAccount(account);
                binding.tvNameEmployee.setText(name.getText().toString());
                binding.tvDateOfBirthEmployee.setText(date.getText().toString());
                binding.tvPhoneEmployee.setText(phone.getText().toString());
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
