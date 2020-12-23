package Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityFindProductBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_List_Find_Product;
import adapter.ClickProduct;
import object_App.Product;

public class Find_Product extends AppCompatActivity {
    ActivityFindProductBinding binding;
    Adapter_List_Find_Product adapter_listView;
    List<Product> productList;
    List<Product> productListFind;
    SQLite_Manage_Your_Store sqLite_manage_your_store;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_find__product);
        Intent intent = getIntent();
        String nameProduct = intent.getStringExtra("nameProduct");
        productList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        productList = sqLite_manage_your_store.getAllPrduct();
        productListFind = new ArrayList<>();
        for (Product product : productList
        ) {
            if (product.getNameProduct().toLowerCase().contains(nameProduct.toLowerCase())) {
                productListFind.add(product);
            }
        }
        adapter_listView = new Adapter_List_Find_Product(productListFind);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvFindProduct.setLayoutManager(layoutManager);
        binding.rvFindProduct.setAdapter(adapter_listView);
        adapter_listView.setClickProduct(new ClickProduct() {
            @Override
            public void clickProductImage(String image) {
                productListFind.get(Integer.valueOf(image)).setAmount(1);
                sqLite_manage_your_store.insertOrderProduct(productListFind.get(Integer.valueOf(image)));
                Intent intent1 = new Intent(getApplicationContext(), Employee_Main_Activity.class);
                startActivity(intent1);
            }
        });
        binding.btnFind1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productListFind.removeAll(productList);
                for (Product product : productList
                ) {
                    if (product.getNameProduct().toLowerCase().contains(binding.etNameProduct1.getText().toString().toLowerCase())) {
                        productListFind.add(product);

                    }
                }
                adapter_listView.notifyDataSetChanged();
                RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false);
                binding.rvFindProduct.setLayoutManager(layoutManager);
                binding.rvFindProduct.setAdapter(adapter_listView);
            }
        });
    }
}