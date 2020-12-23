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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.databinding.ActivitySettingBinding;

import java.util.ArrayList;
import java.util.List;

import Activity.Add_Product;
import Activity.Admin_Main_Activity;
import Activity.Change_Product_Information;
import login.Create_A_New_Account;
import login.Create_Acount;
import login.Login_Activity;
import object_App.Account;

public class Admin_Information extends Fragment {
    ActivitySettingBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;
    Admin_Main_Activity admin_main_activity;
    public static Admin_Information newInstance() {

        Bundle args = new Bundle();

        Admin_Information fragment = new Admin_Information();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_setting, container, false);
        accountList= new ArrayList<>();
        admin_main_activity = (Admin_Main_Activity) getActivity();
        String userName = admin_main_activity.getUserName();
        sqLite_manage_your_store =new SQLite_Manage_Your_Store(getContext());
        accountList= sqLite_manage_your_store.getAllAccounts();
        for (Account account: accountList
             ) {
            if(account.getUserName().equals(userName)){
                binding.tvNameUserSeting.setText(account.getFullName());
                binding.tvDateOfBirthSetting.setText(account.getDateOfBirth());
                binding.tvPhone.setText(account.getPhone());
                if(account.getPermission()==1){
                    binding.llManageProduct.setVisibility(View.GONE);
                }
            }
        }
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Change_Password_Fragment.class);
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
        binding.btnChangeInformation.setOnClickListener(new View.OnClickListener() {
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
        binding.btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Create_Acount.class);
                startActivity(intent);
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
                binding.tvNameUserSeting.setText(name.getText().toString());
                binding.tvDateOfBirthSetting.setText(date.getText().toString());
                 binding.tvPhone.setText(phone.getText().toString());
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
