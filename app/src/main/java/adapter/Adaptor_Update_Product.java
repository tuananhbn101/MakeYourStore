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

public class Adaptor_Update_Product extends RecyclerView.Adapter<Adaptor_Update_Product.ViewHolder> {
    List<Product> productList;
    ClickOrderProduct clickOrderProduct;
    private Context context;

    public void setClickOrderProduct (ClickOrderProduct clickOrderProduct){
        this.clickOrderProduct = clickOrderProduct;
    }

    public Adaptor_Update_Product(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_product_to_update,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getNameProduct());
        holder.tvAmout.setText(product.getAmount()+"");
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(product.getPrice())+" đ";
        holder.tvPrice.setText("Giá: "+price);
        Picasso.with(this.context).load("file://"+product.getImage()).into(holder.ivProduct);
        holder.bntDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderProduct.clickBtnDelete(product.getID(),position);
            }
        });
        holder.btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickOrderProduct.clickBtnAdd(product.getID(),position);
            }
        });
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
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
        TextView tvAmout,bntDelete,btnUpdate;
        ImageView ivProduct;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvAmout = itemView.findViewById(R.id.tvAmountUp);
            tvName = itemView.findViewById(R.id.tvNameProductUp);
            tvPrice = itemView.findViewById(R.id.tvPriceUp);
            bntDelete = itemView.findViewById(R.id.btnDeleteUp);
            btnUpdate = itemView.findViewById(R.id.btnUpdate);
            ivProduct = itemView.findViewById(R.id.ivImageProductUp);
        }
    }
}
