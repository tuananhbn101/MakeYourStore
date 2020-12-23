package com.example.makeyourstore;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.makeyourstore.databinding.ChangePasswordFragmentBinding;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import Activity.Employee_Main_Activity;
import object_App.Account;

public class Change_Password_Fragment extends AppCompatActivity {
    ChangePasswordFragmentBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.change_password_fragment);
        accountList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        accountList = sqLite_manage_your_store.getAllAccounts();
        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = 0;
                String userName = binding.tvUserName.getText().toString();
                String oldPass = binding.tvOldPass.getText().toString();
                String newPass = binding.tvNewPass.getText().toString();
                String confirm = binding.tvConfirmNewPass.getText().toString();
                for (Account account : accountList
                ) {
                    if (account.getUserName().equals(userName) && account.getPassword().equals(oldPass)) {
                        if (newPass.equals(confirm)) {
                            account.setPassword(newPass);
                            sqLite_manage_your_store.updateAccount(account);
                            dem++;
                        } else binding.tvConfirmNewPass.setError("Không trùng khớp mật khẩu");
                    }
                }
                if (checkPassword(newPass) == false) {
                    binding.tvNewPass.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số");
                    dem = 0;
                }
                if (checkPassword(confirm) == false) {
                    binding.tvConfirmNewPass.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số");
                    dem = 0;
                }
                if (userName.isEmpty() || oldPass.isEmpty() || newPass.isEmpty() || confirm.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Bạn chưa nhập đủ thông tin", Toast.LENGTH_LONG).show();
                    dem = 0;
                }
                if (dem != 0) {
                    Toast.makeText(getBaseContext(), "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getBaseContext(), Employee_Main_Activity.class);
                    startActivity(intent);
                }
            }
        });

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