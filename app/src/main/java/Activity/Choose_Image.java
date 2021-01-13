package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Toast;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ChooseImageFragmentBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import object_App.Account;
import object_App.Product;

public class Choose_Image extends AppCompatActivity {
    ChooseImageFragmentBinding binding;
    String link = "";
    String linkImage = "";
    List<Account> accountList;
    List<Product> productList;
    SQLite_Manage_Your_Store sqLite_manage_your_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.choose_image_fragment);
        Intent intent = getIntent();
        String type = intent.getStringExtra("type");
        int permission = Integer.valueOf(intent.getStringExtra("permission"));
        String ID = intent.getStringExtra("ID");
        String IDProduct = intent.getStringExtra("IDProduct");
        accountList = new ArrayList<>();
        productList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        accountList = sqLite_manage_your_store.getAllAccounts();
        productList = sqLite_manage_your_store.getAllPrduct();
        if (type.equals("account")) {

            for (Account account : accountList
            ) {
                if (account.getID() == Integer.valueOf(ID)) {
                    link += account.getAvatar();
                }
            }
        } else if (type.equals("product")) {

            for (Product product : productList
            ) {
                if (product.getID() == Integer.valueOf(IDProduct)) {
                    link += product.getImage();
                }
            }
        }
        Picasso.with(getApplicationContext()).load("file://" + link).into(binding.ivImageProductChoose);
        binding.btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
            }
        });
        binding.btnCapNhatAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (type.equals("account")) {
                    for (Account account : accountList
                    ) {
                        if (account.getID() == Integer.valueOf(ID)) {
                            account.setAvatar(linkImage);
                            sqLite_manage_your_store.updateAccount(account);
                        }
                    }
                } else if (type.equals("product")) {
                    for (Product product : productList
                    ) {
                        if (product.getID() == Integer.valueOf(IDProduct)) {
                            product.setImage(linkImage);
                            sqLite_manage_your_store.updateProduct(product);
                        }
                    }
                }
                if (permission == 0) {
                    Toast.makeText(getApplicationContext(),"Thay đổi thành công",Toast.LENGTH_LONG).show();
                    Intent intent1 = new Intent(getBaseContext(), Admin_Main_Activity.class);
                    intent1.putExtra("ID",ID);
                    startActivity(intent1);

                } else if (permission == 1) {
                    Intent intent2 = new Intent(getBaseContext(), Employee_Main_Activity.class);
                    intent2.putExtra("ID",ID);
                    startActivity(intent2);
                }
            }
        });
        binding.btnBack1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (permission == 0) {
                    Intent intent1 = new Intent(getBaseContext(), Admin_Main_Activity.class);
                    intent1.putExtra("ID",ID);
                    startActivity(intent1);
                } else if (permission == 1) {
                    Intent intent2 = new Intent(getBaseContext(), Employee_Main_Activity.class);
                    intent2.putExtra("ID",ID);
                    startActivity(intent2);
                }
            }
        });
    }

    private void requestPermissions() {
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
                .setPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA)
                .check();
    }

    private void opentImagePicker() {
        TedBottomPicker.OnImageSelectedListener listener = new TedBottomPicker.OnImageSelectedListener() {
            @Override
            public void onImageSelected(Uri uri) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    binding.ivImageProductChoose.setImageBitmap(bitmap);
                    linkImage += uri.getPath();
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