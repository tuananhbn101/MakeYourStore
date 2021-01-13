package com.example.makeyourstore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.makeyourstore.databinding.ActivityHomeFragmentBinding;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Activity.Employee_Main_Activity;
import Activity.Find_Product;
import adapter.Adapter_List_Product_Horizontal;
import adapter.Adapter_List_Product_Vertical;
import adapter.ClickImageProductToShow;
import object_App.Product;

public class Home_Fragment extends Fragment{
    ActivityHomeFragmentBinding binding;
    Employee_Main_Activity employeeMainActivity;
    List<Product> productList;
    List<Product> mouseList;
    List<Product> keyboardList;
    List<Product> otherProductList;
    List<Product> orderProductList;
    Adapter_List_Product_Horizontal adapterHotProduct, adapterKeyProduct, adapterMouseProduct;
    Adapter_List_Product_Vertical adapterOtherProduct;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    int[] arrHinh = {R.drawable.anhnen, R.drawable.anhnen2, R.drawable.anhnen3, R.drawable.anhnen4};
    int idProduct;


    public static Home_Fragment newInstance() {

        Bundle args = new Bundle();

        Home_Fragment fragment = new Home_Fragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_home__fragment, container, false);
        employeeMainActivity = (Employee_Main_Activity) getActivity();
        for (int i = 0; i < arrHinh.length; i++) {
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
        for (Product product : productList
        ) {
            if (product.getType().equalsIgnoreCase("Mouse")) {
                mouseList.add(product);
            }
        }
        for (Product product : productList
        ) {
            if (product.getType().equalsIgnoreCase("Keyboard")) {
                keyboardList.add(product);
            }
        }
        for (Product product : productList
        ) {
            if (product.getType().equalsIgnoreCase("Other")) {
                otherProductList.add(product);
            }
        }
        Collections.reverse(productList);
        adapterHotProduct = new Adapter_List_Product_Horizontal(productList,getContext());
        adapterMouseProduct = new Adapter_List_Product_Horizontal(mouseList,getContext());
        adapterKeyProduct = new Adapter_List_Product_Horizontal(keyboardList,getContext());
        adapterOtherProduct = new Adapter_List_Product_Vertical(otherProductList,getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager2 = new LinearLayoutManager(getContext(), RecyclerView.HORIZONTAL, false);
        RecyclerView.LayoutManager layoutManager3 = new GridLayoutManager(getContext(), 2, RecyclerView.VERTICAL, false);
        binding.rvHotProduct.setLayoutManager(layoutManager);
        binding.rvHotProduct.setAdapter(adapterHotProduct);
        binding.rvKeyBoardProduct.setLayoutManager(layoutManager2);
        binding.rvKeyBoardProduct.setAdapter(adapterKeyProduct);
        binding.rvMouseProduct.setLayoutManager(layoutManager1);
        binding.rvMouseProduct.setAdapter(adapterMouseProduct);
        binding.rvOtherProduct.setLayoutManager(layoutManager3);
        binding.rvOtherProduct.setAdapter(adapterOtherProduct);
        adapterMouseProduct.setClickImageProductToShow(new ClickImageProductToShow() {
            @Override
            public void clickImageProduct(int id) {
                idProduct = id;
                getDialog(idProduct, mouseList);
            }

            @Override
            public void clickAdd(int id) {
                addProduct(id);
            }
        });
        adapterKeyProduct.setClickImageProductToShow(new ClickImageProductToShow() {
            @Override
            public void clickImageProduct(int id) {
                idProduct = id;
                getDialog(idProduct, keyboardList);
            }

            @Override
            public void clickAdd(int id) {
                addProduct(id);
            }
        });
        adapterHotProduct.setClickImageProductToShow(new ClickImageProductToShow() {
            @Override
            public void clickImageProduct(int id) {
                idProduct = id;
                getDialog(idProduct, productList);
            }

            @Override
            public void clickAdd(int id) {
                addProduct(id);
            }
        });
        adapterOtherProduct.setClickImageProductToShow(new ClickImageProductToShow() {
            @Override
            public void clickImageProduct(int id) {
                idProduct = id;
                getDialog(idProduct, otherProductList);
            }

            @Override
            public void clickAdd(int id) {
                addProduct(id);
            }
        });
        String [] name = new String[productList.size()];
        for(int i = 0;i<productList.size();i++){
            name[i] = productList.get(i).getNameProduct();
        }
        ArrayAdapter adapterPrimaryLanguage = new ArrayAdapter(getContext(), android.R.layout.simple_list_item_1, name);
        binding.etNameProduct.setAdapter(adapterPrimaryLanguage);
        binding.btnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(),Find_Product.class);
                intent.putExtra("nameProduct",binding.etNameProduct.getText().toString());
                intent.putExtra("ID",employeeMainActivity.getID()+"");
                startActivity(intent);
            }
        });
        return binding.getRoot();

    }

    void getDialog(int idProduct, List<Product> productList) {
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_product_infor);
        ImageView imageView = dialog.findViewById(R.id.ivImageProductInfor);
        Picasso.with(getContext()).load("file://"+productList.get(idProduct).getImage()).into(imageView);
        TextView name = dialog.findViewById(R.id.name);
        name.setText(productList.get(idProduct).getNameProduct());
        TextView mota = dialog.findViewById(R.id.mota);
        mota.setText(productList.get(idProduct).getDescribe());
        TextView soLuong = dialog.findViewById(R.id.amount);
        soLuong.setText(productList.get(idProduct).getAmount()+"");
        TextView producer = dialog.findViewById(R.id.producer);
        producer.setText(productList.get(idProduct).getProducer());
        TextView price = dialog.findViewById(R.id.price);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price1 = formatter.format(productList.get(idProduct).getPrice())+" đ";
        price.setText(price1);
        ImageView btnThemGioHang = dialog.findViewById(R.id.themGioHang);
        btnThemGioHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        lp.copyFrom(dialog.getWindow().getAttributes());
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.show();
        dialog.getWindow().setAttributes(lp);
    }
    public  void addProduct(int id){
        orderProductList = new ArrayList<>();
        orderProductList = sqLite_manage_your_store.getAllOrderPrduct();
        int dem =0;
        int soLuong = 0;
        for (Product product : productList
        ) {
            if(product.getID() == id && product.getAmount()>0) {
                soLuong++;
                for (Product product1 : orderProductList
                ) {
                    if (product.getNameProduct().equals(product1.getNameProduct())) {
                        dem++;
                        product1.setAmount(product1.getAmount() + 1);
                        sqLite_manage_your_store.updateOrderProduct(product1);
                        Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_LONG).show();
                    }
                }
            }
        }
        if(dem==0){
            for (Product product : productList
            ) {
                if (product.getID() == id && product.getAmount()>0) {
                    soLuong++;
                    product.setAmount(1);
                    sqLite_manage_your_store.insertOrderProduct(product);
                    Toast.makeText(getContext(),"Thêm thành công",Toast.LENGTH_LONG).show();
                    employeeMainActivity.setCountProduct(employeeMainActivity.getMcount()+1);
                }
            }
        }
        if(soLuong==0){
                Toast.makeText(getContext(),"Hết hàng",Toast.LENGTH_LONG).show();
        }
    }
}