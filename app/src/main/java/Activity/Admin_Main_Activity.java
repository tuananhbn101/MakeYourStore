package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.example.makeyourstore.Manage_Employees;
import com.example.makeyourstore.Manage_Product;
import com.example.makeyourstore.R;
import com.example.makeyourstore.Report_Fragment;
import com.example.makeyourstore.Admin_Information;
import com.example.makeyourstore.databinding.ActivityAdminMainBinding;

public class Admin_Main_Activity extends AppCompatActivity {
    ActivityAdminMainBinding binding;
    private String userName;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        binding = DataBindingUtil.setContentView(this,R.layout.activity_admin__main_);
        AHBottomNavigationItem product = new AHBottomNavigationItem(R.string.products, R.drawable.box, R.color.black);
        AHBottomNavigationItem employees = new AHBottomNavigationItem(R.string.employees, R.drawable.management, R.color.black);
        AHBottomNavigationItem report = new AHBottomNavigationItem(R.string.report, R.drawable.history, R.color.black);
        AHBottomNavigationItem setting = new AHBottomNavigationItem(R.string.add, R.drawable.squares, R.color.black);
        binding.bottomNavigationAdmin.addItem(product);
        binding.bottomNavigationAdmin.addItem(employees);
        binding.bottomNavigationAdmin.addItem(report);
        binding.bottomNavigationAdmin.addItem(setting);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Manage_Product.newInstance()).commit();
        binding.bottomNavigationAdmin.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Manage_Product.newInstance()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Manage_Employees.newInstance()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Report_Fragment.newInstance()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Admin_Information.newInstance()).commit();
                }
                return true;
            }
        });
    }
    public String getUserName() {
        return userName;
    }
}