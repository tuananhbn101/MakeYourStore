package com.example.makeyourstore;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.databinding.ManageProductFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import Activity.Add_Product;
import Activity.Change_Product_Information;
import adapter.Adaptor_Update_Product;
import adapter.ClickOrderProduct;
import object_App.Product;


public class Manage_Product extends Fragment implements AdapterView.OnItemSelectedListener {
    ManageProductFragmentBinding binding;
    List<Product> productList,productSort;
    Adaptor_Update_Product adaptor_update_product;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    RecyclerView.LayoutManager layoutManager;
    public static Manage_Product newInstance() {

        Bundle args = new Bundle();

        Manage_Product fragment = new Manage_Product();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.manage_product_fragment,container,false);
        productList = new ArrayList<>();
        productSort = new ArrayList<>();
        ArrayAdapter<CharSequence> arrayAdapter = ArrayAdapter.createFromResource(getContext(), R.array.list_sort, android.R.layout.simple_spinner_item);
        arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerSortProduct.setAdapter(arrayAdapter);
        binding.spinnerSortProduct.setOnItemSelectedListener(this);
        binding.spinnerSortProduct.setAdapter(arrayAdapter);
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        productList = sqLite_manage_your_store.getAllPrduct();
        productSort = productList;
        if(productList.size()>0){
            binding.tvNoProduct.setVisibility(View.INVISIBLE);
            binding.tvComment.setVisibility(View.INVISIBLE);
        }
        adaptor_update_product = new Adaptor_Update_Product(productSort,getContext());
        layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvProducts.setLayoutManager(layoutManager);
        binding.rvProducts.setAdapter(adaptor_update_product);
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
        });
        binding.btnSearchProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Product product: productList
                     ) {
                   if( product.getNameProduct().contains(binding.etSearchProduct.getText().toString())){
                        
                   }
                }
            }
        });
        return binding.getRoot();
    }
    public void deleteProduct(int id, int position) {
        AlertDialog alertDialog = new AlertDialog.Builder(getContext())
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
                        binding.rvProducts.setAdapter(adaptor_update_product);
                        Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_LONG).show();
                    }
                })
                .create();
        alertDialog.show();
    }

    void getDialog(int id, int position, List<Product> productList) {
        Dialog dialog = new Dialog(getContext());
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
                binding.rvProducts.setLayoutManager(layoutManager);
                binding.rvProducts.setAdapter(adaptor_update_product);
                sqLite_manage_your_store.updateProduct(productList.get(position));
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
