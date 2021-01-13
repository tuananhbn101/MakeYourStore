package Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makeyourstore.R;
import com.example.makeyourstore.SQLite_Manage_Your_Store;
import com.example.makeyourstore.databinding.ActivityChangeProductInformationBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adaptor_Update_Product;
import adapter.ClickOrderProduct;
import object_App.Product;

public class Change_Product_Information extends AppCompatActivity {
    ActivityChangeProductInformationBinding binding;
    List<Product> productList;
    Adaptor_Update_Product adaptor_update_product;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    RecyclerView.LayoutManager layoutManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_change__product__information);
        productList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getApplicationContext());
        productList = sqLite_manage_your_store.getAllPrduct();
        adaptor_update_product = new Adaptor_Update_Product(productList,getApplicationContext());
        layoutManager = new GridLayoutManager(getApplicationContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvAllProduct.setLayoutManager(layoutManager);
        binding.rvAllProduct.setAdapter(adaptor_update_product);
        adaptor_update_product.setClickOrderProduct(new ClickOrderProduct() {
            @Override
            public void clickBtnMinus(int id, int position) {

            }

            @Override
            public void clickBtnAdd(int id, int position) {
                getDialog(id,position, productList);
            }

            @Override
            public void clickBtnDelete(int id, int position) {
                deleteProduct(id, position);
            }

            @Override
            public void clickAmount(int id, int position) {

            }
        });
    }

    public void deleteProduct(int id, int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(Change_Product_Information.this)
                .setTitle("Bạn có muốn xóa sản phẩm")
                .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        productList.remove(position);
                        sqLite_manage_your_store.deleteItemProduct(id);
                        adaptor_update_product.notifyDataSetChanged();
                        binding.rvAllProduct.setAdapter(adaptor_update_product);
                        Toast.makeText(getBaseContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    }
                })
                .create();
        alertDialog.show();
    }

    void getDialog(int id, int position, List<Product> productList) {
        Dialog dialog = new Dialog(Change_Product_Information.this);
        dialog.setContentView(R.layout.dialog_update_product);
        EditText name = dialog.findViewById(R.id.etProductNameToUpdate);
        name.setText(productList.get(position).getNameProduct());
        EditText amount = dialog.findViewById(R.id.etProductAmountToUpdate);
        amount.setText(productList.get(position).getAmount()+"");
        EditText price = dialog.findViewById(R.id.etProductPriceToUpdate);
        price.setText(productList.get(position).getPrice()+"");
        Button btnUpdate = dialog.findViewById(R.id.btnCapNhat);
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                productList.get(position).setNameProduct(name.getText().toString());
                productList.get(position).setAmount(Integer.valueOf(amount.getText().toString()));
                productList.get(position).setPrice(Integer.valueOf(price.getText().toString()));
                sqLite_manage_your_store.updateProduct(productList.get(position));
                adaptor_update_product.notifyDataSetChanged();
                binding.rvAllProduct.setLayoutManager(layoutManager);
                binding.rvAllProduct.setAdapter(adaptor_update_product);
                sqLite_manage_your_store.updateProduct(productList.get(position));
                dialog.cancel();
            }
        });
        dialog.show();
    }
}