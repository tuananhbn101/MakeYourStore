package com.example.makeyourstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.makeyourstore.databinding.ActivityHomeFragmentBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_List_Product_Horizontal;
import adapter.Adapter_List_Product_Vertical;
import object_App.Product;

public class Home_Fragment extends Fragment {
    ActivityHomeFragmentBinding binding;
    List<Product> productList;
    List<Product> mouseList;
    List<Product> keyboardList;
    List<Product> otherProductList;
    Product product, product1, product2, product3, product4,product5;
    Adapter_List_Product_Horizontal adapterHotProduct,adapterKeyProduct,adapterMouseProduct;
    Adapter_List_Product_Vertical adapterOtherProduct;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    int [] arrHinh = {R.drawable.anhnen,R.drawable.anhnen2,R.drawable.anhnen3,R.drawable.anhnen4};
    public static Home_Fragment newInstance() {

        Bundle args = new Bundle();

        Home_Fragment fragment = new Home_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater,R.layout.activity_home__fragment,container,false);
        for(int i = 0; i<arrHinh.length;i++){
            ImageView imageView = new ImageView(getContext());
            imageView.setImageResource(arrHinh[i]);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            binding.vfQC.addView(imageView);
        }
        binding.vfQC.setFlipInterval(3000);
        binding.vfQC.setAutoStart(true);
        productList = new ArrayList<>();
        mouseList = new ArrayList<>();
        keyboardList = new ArrayList<>();
        otherProductList = new ArrayList<>();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        productList = sqLite_manage_your_store.getAllPrduct();
        for (Product product: productList
             ) {
            if(product.getType().equalsIgnoreCase("Mouse")){
                mouseList.add(product);
            }
        }
        for (Product product: productList
        ) {
            if(product.getType().equalsIgnoreCase("Keyboard")){
                keyboardList.add(product);
            }
        }
        for (Product product: productList
        ) {
            if(product.getType().equalsIgnoreCase("Other")){
                otherProductList.add(product);
            }
        }
        adapterHotProduct = new Adapter_List_Product_Horizontal(productList);
        adapterMouseProduct = new Adapter_List_Product_Horizontal(mouseList);
        adapterKeyProduct = new Adapter_List_Product_Horizontal(keyboardList);
        adapterOtherProduct = new Adapter_List_Product_Vertical(otherProductList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new GridLayoutManager(getContext(),2, RecyclerView.VERTICAL, false);
        binding.rvHotProduct.setLayoutManager(layoutManager);
        binding.rvHotProduct.setAdapter(adapterHotProduct);
        binding.rvKeyBoardProduct.setLayoutManager(layoutManager2);
        binding.rvKeyBoardProduct.setAdapter(adapterKeyProduct);
        binding.rvMouseProduct.setLayoutManager(layoutManager1);
        binding.rvMouseProduct.setAdapter(adapterMouseProduct);
        binding.rvOtherProduct.setLayoutManager(layoutManager3);
        binding.rvOtherProduct.setAdapter(adapterOtherProduct);
        return binding.getRoot();
    }
}