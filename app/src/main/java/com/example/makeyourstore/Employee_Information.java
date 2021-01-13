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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.databinding.EmployeeInformationBinding;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import Activity.Choose_Image;
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
        int id = employee_main_activity.getID();
        sqLite_manage_your_store =new SQLite_Manage_Your_Store(getContext());
        accountList= sqLite_manage_your_store.getAllAccounts();
        for (Account account: accountList
        ) {
            if(account.getID() == id){
                binding.tvNameEmployee.setText(account.getFullName());
                binding.tvDateOfBirthEmployee.setText(account.getDateOfBirth());
                binding.tvPhoneEmployee.setText(account.getPhone());
                Picasso.with(getContext()).load("file://"+account.getAvatar()).into(binding.ivInformation);
                break;
            }
        }
        binding.btnLogoutEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.ivInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Choose_Image.class);
                intent.putExtra("type","account");
                intent.putExtra("ID",employee_main_activity.getID()+"");
                intent.putExtra("permission",employee_main_activity.getPermission()+"");
                startActivity(intent);
            }
        });
        binding.btnChangePassEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getDialogChangePass();
            }
        });
        binding.btnChangeInformationEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Account account: accountList
                ) {
                    if(account.getID()==id){
                        getDialog(account);
                    }
                }
            }
        });
        return binding.getRoot();

    }
    public void getDialogChangePass(){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.change_password_fragment);
        EditText userName = dialog.findViewById(R.id.tvUserName);
        EditText oldPass = dialog.findViewById(R.id.tvOldPass);
        EditText newPass = dialog.findViewById(R.id.tvNewPass);
        EditText confirmPass = dialog.findViewById(R.id.tvConfirmNewPass);
        Button btnChange = dialog.findViewById(R.id.btnChangePassword);
        accountList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        accountList = sqLite_manage_your_store.getAllAccounts();
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = 0;
                String userName1 = userName.getText().toString();
                String oldPass1 = oldPass.getText().toString();
                String newPass1 = newPass.getText().toString();
                String confirm1 = confirmPass.getText().toString();
                for (Account account : accountList
                ) {
                    if (account.getUserName().equals(userName1) && account.getPassword().equals(oldPass1)) {
                        if (newPass1.equals(confirm1)) {
                            account.setPassword(newPass1);
                            sqLite_manage_your_store.updateAccount(account);
                            dem++;
                        } else confirmPass.setError("Không trùng khớp mật khẩu");
                    }
                }
                if (checkPassword(newPass1) == false) {
                    newPass.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số");
                    dem = 0;
                }
                if (checkPassword(confirm1) == false) {
                    confirmPass.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số");
                    dem = 0;
                }
                if (userName1.isEmpty() || oldPass1.isEmpty() || newPass1.isEmpty() || confirm1.isEmpty()) {
                    Toast.makeText(getContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_LONG).show();
                    dem = 0;
                }
                if (dem != 0) {
                    Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), Employee_Main_Activity.class);
                    startActivity(intent);
                }
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
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
        Button button1 = dialog.findViewById(R.id.btnCancleUp);
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

    public boolean checkUserName(String userName) {
        String passPattern = "((?=.*\\d)(?=.*[a-z]).{6,})";
        if (userName.isEmpty()) {
            return false;
        } else if (Pattern.matches(passPattern, userName)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPassword(String password) {
        String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        if (password.isEmpty()) {
            return false;
        } else if (Pattern.matches(passPattern, password)) {
            return true;
        } else {
            return false;
        }
    }

}
