package Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.Manifest;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityAddPoductBinding;
import com.gun0912.tedpermission.PermissionListener;
import com.gun0912.tedpermission.TedPermission;

import java.io.IOException;
import java.util.List;

import gun0912.tedbottompicker.TedBottomPicker;
import object_App.Product;

public class Add_Product extends AppCompatActivity implements AdapterView.OnItemSelectedListener {
    ActivityAddPoductBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding  = DataBindingUtil.setContentView(this,R.layout.activity_add_poduct);
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getApplicationContext(), R.array.list_producer, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinerProductProducer.setAdapter(arrayAdapter);
        binding.spinerProductProducer.setOnItemSelectedListener(this);
        binding.spinerProductProducer.setAdapter(arrayAdapter);
        binding.imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                requestPermissions();
                binding.tvLinkImage.setVisibility(View.INVISIBLE);
            }
        });
        binding.buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameProduct = binding.etProductName.getText().toString();
                String priceImprot = binding.etProductPriceImport.getText().toString();
                String price = binding.etProductPrice.getText().toString();
                String amount = binding.etProductAmount.getText().toString();
                String typeProduct = null;
                if (binding.radioMouse.isChecked()) typeProduct = "Mouse";
                else if (binding.radioKeyBoard.isChecked()) typeProduct = "Keyboard";
                else if (binding.radioOther.isChecked()) typeProduct = "Other";
                String describe = binding.etProductDescribe.getText().toString();
                String image = binding.tvLinkImage.getText().toString();
                String producer = binding.etProductProducer.getText().toString();
                sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
                if (nameProduct.isEmpty() || price.isEmpty() || amount.isEmpty() || describe.isEmpty() || image.isEmpty() || producer.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Bạn điền thiếu thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getApplicationContext(), "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                    sqLite_manage_your_store.insertProduct(new Product(0, nameProduct,Long.valueOf(priceImprot), Long.valueOf(price), Integer.valueOf(amount), typeProduct, describe, image, producer));
                    Intent intent = new Intent(getBaseContext(), Admin_Main_Activity.class);
                    startActivity(intent);
                }

            }
        });

    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String producer = parent.getItemAtPosition(position).toString();
        binding.etProductProducer.setText(producer);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
    private void requestPermissions(){
        PermissionListener permissionlistener = new PermissionListener() {
            @Override
            public void onPermissionGranted() {
                opentImagePicker();
            }

            @Override
            public void onPermissionDenied(List<String> deniedPermissions) {
                Toast.makeText(Add_Product.this, "Permission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
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
                    binding.imageProduct.setImageBitmap(bitmap);
                    binding.tvLinkImage.setText( uri.getPath());
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