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
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import Activity.Add_Product;
import Activity.Admin_Main_Activity;
import Activity.Change_Product_Information;
import Activity.Choose_Image;
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
        accountList = new ArrayList<>();
        admin_main_activity = (Admin_Main_Activity) getActivity();
        int ID = admin_main_activity.getID();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        accountList = sqLite_manage_your_store.getAllAccounts();
        for (Account account : accountList
        ) {
            if (account.getID() == ID) {
                binding.tvNameUserSeting.setText(account.getFullName());
                binding.tvDateOfBirthSetting.setText(account.getDateOfBirth());
                binding.tvPhone.setText(account.getPhone());
                try {
                    Picasso.with(getContext()).load("file://" + account.getAvatar()).into(binding.tvInformation);
                } catch (Exception e) {
                    binding.tvInformation.setImageResource(R.drawable.avatar);
                }
                if (account.getPermission() == 1) {
                    binding.llManageProduct.setVisibility(View.GONE);
                }
            }
        }
        binding.btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.tvInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Choose_Image.class);
                intent.putExtra("type","account");
                intent.putExtra("ID",admin_main_activity.getID()+"");
                intent.putExtra("permission",admin_main_activity.getPermission()+"");
                startActivity(intent);
            }
        });
        binding.btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               getDialogChangePass();
            }
        });
        binding.btnAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Add_Product.class);
                intent.putExtra("ID", ID + "");
                startActivity(intent);
            }
        });
        binding.btnChangeInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Account account : accountList
                ) {
                    if (account.getID() == ID) {
                        getDialog(account);
                    }
                }
            }
        });
        binding.btnAddEmployee.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Create_A_New_Account.class);
                intent.putExtra("ID", ID + "");
                startActivity(intent);
            }
        });
        return binding.getRoot();
    }

    public void getDialogChangePass() {
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
                    else Toast.makeText(getContext(), "Tài khoản không đúng", Toast.LENGTH_LONG).show();
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
                    dialog.cancel();
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
                binding.tvNameUserSeting.setText(name.getText().toString());
                binding.tvDateOfBirthSetting.setText(date.getText().toString());
                binding.tvPhone.setText(phone.getText().toString());
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
}
