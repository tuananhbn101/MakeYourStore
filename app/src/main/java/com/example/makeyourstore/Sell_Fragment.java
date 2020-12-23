package com.example.makeyourstore;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
import java.util.List;

import Activity.Employee_Main_Activity;
import adapter.Adaptor_Order_Product;
import adapter.ClickOrderProduct;
import object_App.Bill;
import object_App.Product;
import object_App.Report;

public class Sell_Fragment extends Fragment {
    ActivitySellBinding binding;
    List<Product> productList;
    Employee_Main_Activity employeeMainActivity;
    long total = 0;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    Adaptor_Order_Product adaptor_order_product;

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
        employeeMainActivity = (Employee_Main_Activity) getActivity();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        productList = sqLite_manage_your_store.getAllOrderPrduct();
        adaptor_order_product = new Adaptor_Order_Product(productList,getContext());
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvOrder1.setLayoutManager(layoutManager);
        binding.rvOrder1.setAdapter(adaptor_order_product);
        adaptor_order_product.setClickOrderProduct(new ClickOrderProduct() {
            @Override
            public void clickBtnMinus(int id, int position) {
                for (int i = 0; i < productList.size(); i++) {
                    if (productList.get(i).getID() == id) {
                        if (productList.get(i).getAmount() == 1) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Bạn có muốn xóa sản phẩm")
                                    .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sqLite_manage_your_store.deleteItemOrderProduct(id);
                                            productList.remove(position);
                                            adaptor_order_product.notifyDataSetChanged();
                                            binding.rvOrder1.setLayoutManager(layoutManager);
                                            binding.rvOrder1.setAdapter(adaptor_order_product);
                                            employeeMainActivity.setCountProduct(employeeMainActivity.getMcount() - 1);
                                            total = 0;
                                            setTotal();
                                        }
                                    })
                                    .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {

                                        }
                                    })
                                    .create();
                            alertDialog.show();
                        } else {
                            productList.get(i).setAmount(productList.get(i).getAmount() - 1);
                            adaptor_order_product.notifyDataSetChanged();
                            binding.rvOrder1.setLayoutManager(layoutManager);
                            binding.rvOrder1.setAdapter(adaptor_order_product);
                            sqLite_manage_your_store.updateOrderProduct(productList.get(position));
                            total = 0;
                            setTotal();
                        }
                    }
                }

            }

            @Override
            public void clickBtnAdd(int id, int position) {
                for (Product product : productList
                ) {
                    if (product.getID() == id) {
                        product.setAmount(product.getAmount() + 1);
                    }
                }
                adaptor_order_product.notifyDataSetChanged();
                binding.rvOrder1.setLayoutManager(layoutManager);
                binding.rvOrder1.setAdapter(adaptor_order_product);
                sqLite_manage_your_store.updateOrderProduct(productList.get(position));
                total = 0;
                setTotal();
            }

            @Override
            public void clickBtnDelete(int id, int position) {
                sqLite_manage_your_store.deleteItemOrderProduct(id);
                productList.remove(position);
                adaptor_order_product.notifyDataSetChanged();
                binding.rvOrder1.setLayoutManager(layoutManager);
                binding.rvOrder1.setAdapter(adaptor_order_product);
                total = 0;
                employeeMainActivity.setCountProduct(employeeMainActivity.getMcount() - 1);
                setTotal();
            }


        });
        setTotal();
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {

                if (productList.size() == 0) {
                    Toast.makeText(getContext(), "Chưa có sản phẩm cần thanh toán", Toast.LENGTH_LONG).show();
                } else {


                    String productName = "";
                    String amount = "";
                    String price  = "";
                    long total = 0;
                    long totalImport = 0;
                    for (int i = 0; i<productList.size()-1;i++) {
                        productName += productList.get(i).getNameProduct()+";";
                        amount += productList.get(i).getAmount()+";";
                        price += productList.get(i).getPrice()+";";
                        totalImport += productList.get(i).getImportprice()*productList.get(i).getAmount();
                        total += productList.get(i).getAmount() * productList.get(i).getPrice();
                    }
                    productName += productList.get(productList.size()-1).getNameProduct();
                    amount += productList.get(productList.size()-1).getAmount();
                    price += productList.get(productList.size()-1).getPrice();
                    totalImport += productList.get(productList.size()-1).getAmount() * productList.get(productList.size()-1).getImportprice();
                    total += productList.get(productList.size()-1).getAmount() * productList.get(productList.size()-1).getPrice();
                    Report report = new Report(java.time.LocalDate.now() + "", 0, totalImport, total);
                    Bill bill = new Bill(0,java.time.LocalDate.now()+"",productName,amount,price,total);
                    sqLite_manage_your_store.insertReport(report);
                    sqLite_manage_your_store.insertBill(bill);
                    Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_LONG).show();
                    sqLite_manage_your_store.deleteOrderProduct();
                    employeeMainActivity.setCountReport(employeeMainActivity.getRcount() + 1);
                    employeeMainActivity.setCountProduct(0);
                    employeeMainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, Home_Fragment.newInstance()).commit();
                }
            }
        });
        return binding.getRoot();
    }

    public void setTotal() {
        for (Product product : productList
        ) {
            total += product.getAmount() * product.getPrice();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        String total1 = formatter.format(total) + "đ";
        binding.tvTotal.setText(total1);
    }
}
