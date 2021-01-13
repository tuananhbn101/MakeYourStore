package adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import object_App.Product;

public class Adaptor_Order_Product extends RecyclerView.Adapter<Adaptor_Order_Product.ViewHolder> {
    List<Product> productList;
    ClickOrderProduct clickOrderProduct;
    private Context context;

    public void setClickOrderProduct (ClickOrderProduct clickOrderProduct){
        this.clickOrderProduct = clickOrderProduct;
    }

    public Adaptor_Order_Product(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public Adaptor_Order_Product.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_order_item,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Order_Product.ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getNameProduct());
        holder.tvAmout.setText(product.getAmount()+"");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(product.getPrice())+" đ";
        holder.tvPrice.setText("Giá: "+price);
        Picasso.with(context).load("file://"+product.getImage()).into(holder.ivProduct);
        holder.btnPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderProduct.clickBtnAdd(product.getID(),position);
            }
        });
        holder.btnMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderProduct.clickBtnMinus(product.getID(),position);
            }
        });
        holder.bntdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderProduct.clickBtnDelete(product.getID(),position);
            }
        });
        holder.tvAmout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderProduct.clickAmount(product.getID(),position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice;
        TextView tvAmout,bntdelete;
        ImageView ivProduct;
        ImageView btnMinus,btnPlus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmout = itemView.findViewById(R.id.tvAmount);
            tvName = itemView.findViewById(R.id.tvNameProductOrder);
            tvPrice = itemView.findViewById(R.id.tvPriceOrder);
            btnMinus = itemView.findViewById(R.id.btnMinus);
            btnPlus = itemView.findViewById(R.id.btnPlus);
            ivProduct = itemView.findViewById(R.id.ivImageProduct1);
            bntdelete = itemView.findViewById(R.id.btnDeleteOrderProduct);
        }
    }
}
