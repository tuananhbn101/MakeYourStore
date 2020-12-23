package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;

import com.aurelhubert.ahbottomnavigation.AHBottomNavigation;
import com.aurelhubert.ahbottomnavigation.AHBottomNavigationItem;
import com.aurelhubert.ahbottomnavigation.notification.AHNotification;
import com.example.makeyourstore.Employee_Information;
import com.example.makeyourstore.History_Sell_Framgmet;
import com.example.makeyourstore.Home_Fragment;
import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.Sell_Fragment;
import com.example.makeyourstore.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.List;

import object_App.Product;

public class Employee_Main_Activity extends AppCompatActivity {
    ActivityMainBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    private int mcount = 0;
    private int rcount = 0;
    String userName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        List<Product> products = new ArrayList<>();
        products = sqLite_manage_your_store.getAllOrderPrduct();
        Intent intent = getIntent();
        userName = intent.getStringExtra("userName");
        AHBottomNavigationItem home = new AHBottomNavigationItem(R.string.home, R.drawable.home, R.color.black);
        AHBottomNavigationItem sell = new AHBottomNavigationItem(R.string.sell, R.drawable.sell, R.color.black);
        AHBottomNavigationItem history = new AHBottomNavigationItem(R.string.history, R.drawable.ic_baseline_history_24, R.color.black);
        AHBottomNavigationItem setting = new AHBottomNavigationItem(R.string.me, R.drawable.user1, R.color.black);
        binding.bottomNavigation.addItem(home);
        binding.bottomNavigation.addItem(sell);
        binding.bottomNavigation.addItem(history);
        binding.bottomNavigation.addItem(setting);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Home_Fragment.newInstance()).commit();
        binding.bottomNavigation.setOnTabSelectedListener(new AHBottomNavigation.OnTabSelectedListener() {
            @Override
            public boolean onTabSelected(int position, boolean wasSelected) {
                switch (position) {
                    case 0:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Home_Fragment.newInstance()).commit();
                        break;
                    case 1:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Sell_Fragment.newInstance()).commit();
                        break;
                    case 2:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, History_Sell_Framgmet.newInstance()).commit();
                        break;
                    case 3:
                        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Employee_Information.newInstance()).commit();
                }
                return true;
            }
        });
        if (products.size() != 0) {
            mcount = products.size();
            setCountProduct(mcount);
        }


    }

    public void setCountProduct(int count) {
        mcount = count;
        AHNotification notification = new AHNotification.Builder()
                .setText(count + "")
                .setBackgroundColor(ContextCompat.getColor(Employee_Main_Activity.this, R.color.price_color))
                .setTextColor(ContextCompat.getColor(Employee_Main_Activity.this, R.color.white))
                .build();
        binding.bottomNavigation.setNotification(notification, 1);
    }

    public int getMcount() {
        return mcount;
    }

    public void setCountReport(int count) {
        rcount = count;
        AHNotification notification = new AHNotification.Builder()
                .setText(count + "")
                .setBackgroundColor(ContextCompat.getColor(Employee_Main_Activity.this, R.color.price_color))
                .setTextColor(ContextCompat.getColor(Employee_Main_Activity.this, R.color.white))
                .build();
        binding.bottomNavigation.setNotification(notification, 2);
    }

    public int getRcount() {
        return rcount;
    }

    public String getUserName() {
        return userName;
    }
}