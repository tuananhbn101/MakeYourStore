package login;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import object_App.Account;
import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ForgotPasswordBinding;

import java.util.List;

public class Forgot_Password_Fragment extends Fragment {
    ForgotPasswordBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Account> accountList;

    public static Forgot_Password_Fragment newInstance() {

        Bundle args = new Bundle();

        Forgot_Password_Fragment fragment = new Forgot_Password_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.forgot_password, container, false);
        binding.btnCancle2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), Login_Activity.class);
                startActivity(intent);
            }
        });
        binding.btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
                accountList = sqLite_manage_your_store.getAllAccounts();
                int dem = 0;
                String userPassword = "";
                for (Account account : accountList
                ) {
                    if (binding.etUsName.getText().toString().equalsIgnoreCase(account.getUserName())
                            && binding.etQtn.getText().toString().equalsIgnoreCase(account.getHomeTown())
                            && binding.etAsr.getText().toString().equalsIgnoreCase(account.getEmail())) {
                        userPassword += account.getPassword();
                        dem++;
                    }
                }
                if (dem != 0) {
                    Toast.makeText(getContext(), "Mật khẩu của bạn là : " + userPassword, Toast.LENGTH_LONG).show();
                    Intent intent = new Intent(getContext(), Login_Activity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(getContext(), "Bạn nhập chưa đúng\nVui lòng kiểm tra lại", Toast.LENGTH_LONG).show();
                }
            }
        });
        return binding.getRoot();
    }
}
