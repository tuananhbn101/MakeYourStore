package login;


import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityCreateANewAccountBinding;

import java.util.List;

import Activity.Admin_Main_Activity;
import object_App.Account;

public class Create_A_New_Account extends AppCompatActivity {
    ActivityCreateANewAccountBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_create__a__new__account);
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        int selectedYear = 2000;
        int selectedMonth = 5;
        int selectedDayOfMonth = 10;
        binding.tvDateOfBirth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {

                        binding.tvDateOfBirth.setText(dayOfMonth + "-" + (monthOfYear + 1) + "-" + year);
                    }
                };
                DatePickerDialog datePickerDialog = new DatePickerDialog(Create_A_New_Account.this,
                        dateSetListener, selectedYear, selectedMonth, selectedDayOfMonth);
                datePickerDialog.show();
            }
        });
        binding.btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.btnCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = 0;
                String userName = binding.etNewUserName.getText().toString();
                String password = binding.etNewPassword.getText().toString();
                String fullName = binding.etNewFullName.getText().toString();
                String dateOfBirth = binding.tvDateOfBirth.getText().toString();
                String phone = binding.etNewPhone.getText().toString();
                String question = binding.etQuestion.getText().toString();
                String answer = binding.etAnswer.getText().toString();
                Account newAccount = new Account(0, userName, password, fullName, dateOfBirth, phone, question, answer,1);
                if (userName.isEmpty() || password.isEmpty() || fullName.isEmpty() || dateOfBirth.isEmpty() || phone.isEmpty() || question.isEmpty() || answer.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Bạn nhập thiếu thông tin", Toast.LENGTH_LONG).show();
                } else if (newAccount.checkUserName() && newAccount.checkPassword()) {
                    accountList = sqLite_manage_your_store.getAllAccounts();
                    for (Account account : accountList
                    ) {
                        if (userName.equalsIgnoreCase(account.getUserName())) {
                            dem++;
                        }
                    }
                    if (dem != 0) {
                        Toast.makeText(getBaseContext(), "Tên đăng nhập đã tồn tại", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getBaseContext(), "Tạo tài khoản thành công", Toast.LENGTH_LONG).show();
                        sqLite_manage_your_store.insertAccount(newAccount);
                        Intent intent = new Intent(getBaseContext(), Admin_Main_Activity.class);
                        startActivity(intent);
                    }
                }
                if (newAccount.checkUserName()==false)
                    binding.etNewUserName.setError("Tài khoản có từ 6 ký tự bao gồm chữ hoa, chữ thường và số");
                if (newAccount.checkPassword()==false)
                    binding.etNewPassword.setError("Mật khẩu có từ 8 ký tự bao gồm chữ hoa, chữ thường và số");
            }
        });
    }
}