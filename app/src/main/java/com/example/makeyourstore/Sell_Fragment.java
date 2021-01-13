package com.example.makeyourstore;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.databinding.ActivitySellBinding;
import com.squareup.picasso.Picasso;

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
    List<Product> productOrderList;
    List<Product> productList;
    Employee_Main_Activity employeeMainActivity;
    long total = 0;
    SQLite_Manage_Your_Store sqLite_manage_your_store;
    Adaptor_Order_Product adaptor_order_product;
    RecyclerView.LayoutManager layoutManager;
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
        productOrderList = new ArrayList<>();
        employeeMainActivity = (Employee_Main_Activity) getActivity();
        sqLite_manage_your_store = new SQLite_Manage_Your_Store(getContext());
        productOrderList = sqLite_manage_your_store.getAllOrderPrduct();
        productList = new ArrayList<>();
        productList = sqLite_manage_your_store.getAllPrduct();
        adaptor_order_product = new Adaptor_Order_Product(productOrderList, getContext());
        layoutManager = new GridLayoutManager(getContext(), 1, RecyclerView.VERTICAL, false);
        binding.rvOrder1.setLayoutManager(layoutManager);
        binding.rvOrder1.setAdapter(adaptor_order_product);
        adaptor_order_product.setClickOrderProduct(new ClickOrderProduct() {
            @Override
            public void clickBtnMinus(int id, int position) {
                for (int i = 0; i < productOrderList.size(); i++) {
                    if (productOrderList.get(i).getID() == id) {
                        if (productOrderList.get(i).getAmount() == 1) {
                            AlertDialog alertDialog = new AlertDialog.Builder(getContext())
                                    .setTitle("Bạn có muốn xóa sản phẩm")
                                    .setNegativeButton("Có", new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            sqLite_manage_your_store.deleteItemOrderProduct(id);
                                            productOrderList.remove(position);
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
                            productOrderList.get(i).setAmount(productOrderList.get(i).getAmount() - 1);
                            adaptor_order_product.notifyDataSetChanged();
                            binding.rvOrder1.setLayoutManager(layoutManager);
                            binding.rvOrder1.setAdapter(adaptor_order_product);
                            sqLite_manage_your_store.updateOrderProduct(productOrderList.get(position));
                            total = 0;
                            setTotal();
                        }
                    }
                }

            }

            @Override
            public void clickBtnAdd(int id, int position) {
                for (Product product : productOrderList
                ) {
                    if (product.getID() == id) {
                        product.setAmount(product.getAmount() + 1);
                    }
                }
                adaptor_order_product.notifyDataSetChanged();
                binding.rvOrder1.setLayoutManager(layoutManager);
                binding.rvOrder1.setAdapter(adaptor_order_product);
                sqLite_manage_your_store.updateOrderProduct(productOrderList.get(position));
                total = 0;
                setTotal();
            }

            @Override
            public void clickBtnDelete(int id, int position) {
                sqLite_manage_your_store.deleteItemOrderProduct(id);
                productOrderList.remove(position);
                adaptor_order_product.notifyDataSetChanged();
                binding.rvOrder1.setLayoutManager(layoutManager);
                binding.rvOrder1.setAdapter(adaptor_order_product);
                total = 0;
                employeeMainActivity.setCountProduct(employeeMainActivity.getMcount() - 1);
                setTotal();
            }

            @Override
            public void clickAmount(int id, int position) {
                getDialog(id,position);
            }


        });
        setTotal();
        binding.btnPay.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Employee_Main_Activity employee_main_activity = (Employee_Main_Activity) getActivity();
                int ID = employee_main_activity.getID();
                if (productOrderList.size() == 0) {
                    Toast.makeText(getContext(), "Chưa có sản phẩm cần thanh toán", Toast.LENGTH_LONG).show();
                } else {

                    for (Product product: productOrderList
                         ) {
                        for (Product product1 : productList
                        ) {
                            if (product1.getNameProduct().equals(product.getNameProduct())) {
                                if(product1.getAmount()>=product.getAmount()) {
                                    product1.setAmount(product1.getAmount() - product.getAmount());
                                    sqLite_manage_your_store.updateProduct(product1);
                                    String productName = "";
                                    String amount = "";
                                    String price = "";
                                    long total = 0;
                                    long totalImport = 0;
                                    for (int i = 0; i < productOrderList.size() - 1; i++) {
                                        productName += productOrderList.get(i).getNameProduct() + ";";
                                        amount += productOrderList.get(i).getAmount() + ";";
                                        price += productOrderList.get(i).getPrice() + ";";
                                        totalImport += productOrderList.get(i).getImportprice() * productOrderList.get(i).getAmount();
                                        total += productOrderList.get(i).getAmount() * productOrderList.get(i).getPrice();
                                    }
                                    productName += productOrderList.get(productOrderList.size() - 1).getNameProduct();
                                    amount += productOrderList.get(productOrderList.size() - 1).getAmount();
                                    price += productOrderList.get(productOrderList.size() - 1).getPrice();
                                    totalImport += productOrderList.get(productOrderList.size() - 1).getAmount() * productOrderList.get(productOrderList.size() - 1).getImportprice();
                                    total += productOrderList.get(productOrderList.size() - 1).getAmount() * productOrderList.get(productOrderList.size() - 1).getPrice();
                                    Report report = new Report(java.time.LocalDate.now() + "", 0, totalImport, total, ID);
                                    Bill bill = new Bill(0, java.time.LocalDate.now() + "", productName, amount, price, total, ID);
                                    sqLite_manage_your_store.insertReport(report);
                                    sqLite_manage_your_store.insertBill(bill);
                                    Toast.makeText(getContext(), "Thanh toán thành công", Toast.LENGTH_LONG).show();
                                    sqLite_manage_your_store.deleteOrderProduct();
                                    employeeMainActivity.setCountReport(employeeMainActivity.getRcount() + 1);
                                    employeeMainActivity.setCountProduct(0);
                                    employeeMainActivity.getSupportFragmentManager().beginTransaction().replace(R.id.contentFragment, History_Sell_Framgmet.newInstance()).commit();
                                }
                                else if(product1.getAmount()<product.getAmount()){
                                    Toast.makeText(getContext(),"Không đủ số lượng sản phẩm",Toast.LENGTH_LONG).show();
                                }
                            }
                        }
                    }
                }
            }
        });
        return binding.getRoot();
    }

    public void setTotal() {
        for (Product product : productOrderList
        ) {
            total += product.getAmount() * product.getPrice();
        }
        DecimalFormat formatter = new DecimalFormat("###,###,###");

        String total1 = formatter.format(total) + "đ";
        binding.tvTotal.setText(total1);
    }
    public void getDialog(int id,int position){
        Dialog dialog = new Dialog(getContext());
        dialog.setContentView(R.layout.dialog_update_amount);
        EditText etAmount = dialog.findViewById(R.id.etAmountt);
        TextView btnCN = dialog.findViewById(R.id.btnCapNhat1);
        btnCN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (Product product : productOrderList
                ) {
                    if (product.getID() == id) {
                        product.setAmount(Integer.valueOf(etAmount.getText().toString()));
                    }
                }
                adaptor_order_product.notifyDataSetChanged();
                binding.rvOrder1.setLayoutManager(layoutManager);
                binding.rvOrder1.setAdapter(adaptor_order_product);
                sqLite_manage_your_store.updateOrderProduct(productOrderList.get(position));
                total = 0;
                setTotal();
                dialog.cancel();
            }
        });
        dialog.show();
    }
}
