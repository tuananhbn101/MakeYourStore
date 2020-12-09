package Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.example.makeyourstore.Home_Fragment;
import com.example.makeyourstore.R;
import com.example.makeyourstore.Report_Fragment;
import com.example.makeyourstore.Sell_Fragment;
import com.example.makeyourstore.Setting_Fragment;
import com.example.makeyourstore.databinding.ActivityMainBinding;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView bottomNavigationView;
    ActivityMainBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.bottomNavigation.setOnNavigationItemSelectedListener(bottomNavigationMethod);
        getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, new Home_Fragment()).commit();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener bottomNavigationMethod = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment = null;
            switch (item.getItemId()) {
                case R.id.homeStore:
                    fragment = new Home_Fragment();
                    break;
                case R.id.sell:
                    fragment = new Sell_Fragment();
                    break;
                case R.id.history:
                    fragment = new Report_Fragment();
                    break;
                case R.id.setting:
                    fragment = new Setting_Fragment();

            }
            getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, fragment).commit();
            return true;
        }
    };
}