package com.example.makeyourstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.databinding.ActivitySellBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import adapter.Adapter_ListView;
import adapter.Adaptor_Order_Product;
import object_App.Product;

public class Sell_Fragment extends Fragment {
    ActivitySellBinding binding;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    List<Product> productList;
    long total=0;
    Adaptor_Order_Product  adaptor_order_product;
    public static Sell_Fragment newInstance() {

        Bundle args = new Bundle();

        Sell_Fragment fragment = new Sell_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_sell, container, false);
        productList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        productList = sqLite_manage_your_store.getAllPrduct();
        adaptor_order_product = new Adaptor_Order_Product(productList);
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(),1,RecyclerView.VERTICAL,false);
        binding.rvOrder1.setLayoutManager(layoutManager);
        binding.rvOrder1.setAdapter(adaptor_order_product);
        for (Product product: productList
             ) {
            total += product.getAmount()*product.getPrice();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        String total1 = formatter.format(total)+"đ";
        binding.tvTotal.setText(total1);
        return binding.getRoot();
    }
}
