package Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityFunctionBinding;

import object_App.Product;

public class Add_Product extends AppCompatActivity {
    ActivityFunctionBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_function_);
        Intent intent = getIntent();
        binding.etProductName.setText(intent.getStringExtra("name"));
        binding.etProductPrice.setText(intent.getStringExtra("price"));
        binding.etProductAmount.setText(intent.getStringExtra("amount"));
        binding.etProductDescribe.setText(intent.getStringExtra("describe"));
        binding.etProductProducer.setText(intent.getStringExtra("producer"));
        String link = intent.getStringExtra("imageKey");
        if (link != null)
            binding.imageProduct.setImageResource(Integer.valueOf(link));
        binding.imageProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), List_Image_Choose.class);
                intent.putExtra("name", binding.etProductName.getText().toString());
                intent.putExtra("price", binding.etProductPrice.getText().toString());
                intent.putExtra("amount", binding.etProductAmount.getText().toString());
                String typeProduct = null;
                if (binding.radioMouse.isChecked()) typeProduct = "Mouse";
                else if (binding.radioKeyBoard.isChecked()) typeProduct = "Keyboard";
                else if (binding.radioOther.isChecked()) typeProduct = "Other";
                intent.putExtra("type", typeProduct);
                intent.putExtra("describe", binding.etProductDescribe.getText().toString());
                intent.putExtra("producer", binding.etProductProducer.getText().toString());
                startActivity(intent);
                binding.tvLinkImage.setVisibility(View.INVISIBLE);
            }
        });
        binding.buttonAddProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nameProduct = binding.etProductName.getText().toString();
                String price = binding.etProductPrice.getText().toString();
                String amount = binding.etProductAmount.getText().toString();
                String typeProduct = null;
                if (binding.radioMouse.isChecked()) typeProduct = "Mouse";
                else if (binding.radioKeyBoard.isChecked()) typeProduct = "Keyboard";
                else if (binding.radioOther.isChecked()) typeProduct = "Other";
                String describe = binding.etProductDescribe.getText().toString();
                String image = binding.tvLinkImage.getText().toString();
                String producer = binding.etProductProducer.getText().toString();
                sqLite_manage_your_store = new SQLite_Manage_Your_Store(getBaseContext());
                if (nameProduct.isEmpty() || price.isEmpty() || amount.isEmpty() || describe.isEmpty() || image.isEmpty() || producer.isEmpty()) {
                    Toast.makeText(getBaseContext(), "Bạn điền thiếu thông tin", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(getBaseContext(), "Thêm sản phẩm thành công", Toast.LENGTH_LONG).show();
                    sqLite_manage_your_store.insertProduct(new Product("0", nameProduct, Long.valueOf(price), Integer.valueOf(amount), typeProduct, describe, Integer.valueOf(image), producer));
                }
                Intent intent = new Intent(getBaseContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }
}