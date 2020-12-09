package Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ChangePasswordFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import Activity.MainActivity;
import login.Login_Activity;
import object_App.Account;

public class Change_Password extends AppCompatActivity {
    ChangePasswordFragmentBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.change_password_fragment);
        accountList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        accountList = sqLite_manage_your_store.getAllAccounts();
        binding.btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dem = 0;
                for (Account account : accountList
                ) {
                    if (account.getUserName().equals(binding.tvUserName.getText()) && account.getPassword().equals(binding.tvOldPass.getText()) && binding.tvConfirmNewPass.getText().equals(binding.tvNewPass.getText())) {
                        account.setPassword(binding.tvNewPass.getText().toString());
                    }
                }
                if (dem != 0)
                    Toast.makeText(getBaseContext(), "Đổi mật khẩu thành công", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}
