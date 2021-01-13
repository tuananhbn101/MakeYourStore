package login;


import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.DatePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityCreateANewAccountBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.List;

import Activity.Add_Product;
import Activity.Admin_Main_Activity;
import gun0912.tedbottompicker.TedBottomPicker;
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
        binding.ivAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                binding.tvThemAnh.setVisibility(View.INVISIBLE);
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
                String avatar = binding.tvThemAnh.getText().toString();
                Account newAccount = new Account(0, userName, password, fullName, dateOfBirth, phone, question, answer,avatar,1);
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
                        Intent intent1 = getIntent();
                        String id = intent1.getStringExtra("ID");
                        Intent intent = new Intent(getBaseContext(), Admin_Main_Activity.class);
                        intent.putExtra("ID",id+"");
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
    private void requestPermissions(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                opentImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(getApplicationContext(), "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
            }


        };
        TedPermission.with(this)
                .setPermissionListener(permissionlistener)
                .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }
    private void opentImagePicker(){
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),uri);
                    binding.ivAvatar.setImageBitmap(bitmap);
                    binding.tvThemAnh.setText( uri.getPath());
                    binding.tvThemAnh.setVisibility(View.INVISIBLE);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        TedBottomPicker tedBottomPicker = new TedBottomPicker.Builder(getApplicationContext())
                .setOnImageSelectedListener(listener)
                .create();
        tedBottomPicker.show(getSupportFragmentManager());
    }
}