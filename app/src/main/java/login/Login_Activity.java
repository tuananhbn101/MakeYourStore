package login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import Activity.Admin_Main_Activity;
import object_App.Account;
import Activity.Employee_Main_Activity;
import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityLoginBinding;

import java.util.List;
import java.util.regex.Pattern;

public class Login_Activity extends AppCompatActivity {
    ActivityLoginBinding binding;
    Forgot_Password_Fragment forgot_password_fragment;
    Create_A_New_Account create_a_new_account;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;
    private final Account account = new Account(0,"admin1","Admin123","Nguyễn Tuấn Anh","10/01/2000","0395501405","","",0);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(Login_Activity.this);
        accountList = sqLite_manage_your_store.getAllAccounts();
        if(accountList.size()==0){
            sqLite_manage_your_store.insertAccount(account);
            accountList = sqLite_manage_your_store.getAllAccounts();
        }
        binding.etUserName.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.etUserName.setHint("");
            }
        });
        binding.etPassword.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                binding.etPassword.setHint("");
            }
        });
        Intent intent = getIntent();
        binding.etUserName.setText(intent.getStringExtra("userName"));
        binding.etPassword.setText(intent.getStringExtra("password"));
        binding.btnSingIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = 0;
                int permission;
                if(binding.checkAdmin.isChecked()){
                    permission = 0;
                }
                else permission = 1;
                if (checkTaiKhoan() && checkMatKhau()) {
                    for (Account account : accountList
                    ) {
                        if (binding.etUserName.getText().toString().equalsIgnoreCase(account.getUserName()) &&
                                binding.etPassword.getText().toString().equalsIgnoreCase(account.getPassword())&&account.getPermission()==permission)
                        {
                            count++;
                        }
                    }
                }
                if (count != 0) {
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    if(permission==1){
                        Intent intent = new Intent(Login_Activity.this, Employee_Main_Activity.class);
                        intent.putExtra("userName",binding.etUserName.getText().toString());
                        startActivity(intent);
                    }
                    if(permission==0){
                        Intent intent = new Intent(Login_Activity.this, Admin_Main_Activity.class);
                        intent.putExtra("userName",binding.etUserName.getText().toString());
                        startActivity(intent);
                    }
                }
                if (count == 0) {
                    Toast.makeText(Login_Activity.this, "Tài khoản hoặc mật khẩu chưa đúng\nVui lòng kiểm tra lại", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    public boolean checkTaiKhoan() {

        if (binding.etUserName.getText().toString().isEmpty()) {
            Toast.makeText(Login_Activity.this, "Tài khoản không được bỏ trống", Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    public boolean checkMatKhau() {
        String passPattern = "((?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,})";
        if (Pattern.matches(passPattern, binding.etPassword.getText().toString())) {
            return true;
        } else {
            Toast.makeText(Login_Activity.this, "Đăng nhập thất bại. Kiểm tra lại mật khẩu\n(Mật khẩu có từ 8 ký tự bao gồm chữ hoa, chữ thường và số)", Toast.LENGTH_LONG).show();
            return false;
        }
    }


}