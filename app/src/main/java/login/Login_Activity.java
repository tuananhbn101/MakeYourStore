package login;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import object_App.Account;
import Activity.MainActivity;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login_);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(Login_Activity.this);
        accountList = sqLite_manage_your_store.getAllAccounts();
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
                if (checkTaiKhoan() && checkMatKhau()) {
                    for (Account account : accountList
                    ) {
                        if (binding.etUserName.getText().toString().equalsIgnoreCase(account.getUserName()) &&
                                binding.etPassword.getText().toString().equalsIgnoreCase(account.getPassword())) {
                            count++;
                        }
                    }
                }
                if (count != 0) {
                    Toast.makeText(Login_Activity.this, "Đăng nhập thành công", Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(Login_Activity.this, MainActivity.class);
                    startActivity(intent);
                }
                if (count == 0) {
                    Toast.makeText(Login_Activity.this, "Tài khoản hoặc mật khẩu chưa đúng\nVui lòng kiểm tra lại", Toast.LENGTH_LONG).show();
                }
            }
        });
        binding.tvCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment();
                showFragment(forgot_password_fragment, create_a_new_account, 1);
            }
        });
        binding.tvForgotPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragment();
                showFragment(forgot_password_fragment, create_a_new_account, 0);
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

    public void getFragment() {
        forgot_password_fragment = new Forgot_Password_Fragment();
        create_a_new_account = new Create_A_New_Account();
        getSupportFragmentManager().beginTransaction().add(R.id.rl_login, forgot_password_fragment, Forgot_Password_Fragment.class.getName())
                .add(R.id.rl_login, create_a_new_account, Create_A_New_Account.class.getName())
                .commit();
    }

    public void showFragment(Fragment fragment, Fragment fragment2, int fragmentNumber) {
        switch (fragmentNumber) {
            case 0:
                getSupportFragmentManager().beginTransaction().show(fragment).hide(fragment2).commit();
                break;
            case 1:
                getSupportFragmentManager().beginTransaction().show(fragment2).hide(fragment).commit();
                break;

        }

    }
}