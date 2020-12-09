package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.makeyourstore.R;

import java.text.DecimalFormat;
import java.util.List;

import object_App.Product;

public class Adapter_ListView extends BaseAdapter {
    List<Product> productList;

    public Adapter_ListView(List<Product> productList) {
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.list_item_list_view,parent,false);
        TextView tvProduct = view.findViewById(R.id.tvNameProduct2);
        TextView tvMota = view.findViewById(R.id.tvMoTa);
        TextView tvPrice = view.findViewById(R.id.tvPrice2);
        TextView tvAmount= view.findViewById(R.id.tvAmount1);
        ImageView imageView = view.findViewById(R.id.ivImageProduct1);
        Product product = productList.get(position);
        tvProduct.setText(product.getNameProduct());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(product.getPrice())+" đ";
        tvPrice.setText("Giá: "+price);
        tvMota.setText("- "+product.getDescribe());
        tvAmount.setText(": "+product.getAmount()+"");
        imageView.setImageResource(product.getImage());
        return view;
    }
}
