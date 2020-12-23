package adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

import object_App.Product;

public class Adapter_List_Find_Product extends RecyclerView.Adapter<Adapter_List_Find_Product.ViewHolder> {
    List<Product> productList;
    ClickProduct clickProduct;
    private Context context;

    public Adapter_List_Find_Product(List<Product> productList) {
        this.productList = productList;
    }
    public void setClickProduct(ClickProduct clickProduct){
        this.clickProduct =clickProduct;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_list_view, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Product product = productList.get(position);
        holder.tvName.setText(product.getNameProduct());
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String price = formatter.format(product.getPrice())+" đ";
        holder.tvPrice.setText("Giá: "+price);
        Picasso.with(context).load("file://"+product.getImage()).into(holder.ivProduct);
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickProduct.clickProductImage(String.valueOf(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName, tvPrice;
        ImageView ivProduct;
        ImageView ivAdd;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameProductFind);
            tvPrice = itemView.findViewById(R.id.tvPriceFind);
            ivProduct = itemView.findViewById(R.id.ivImageProductFind);
            ivAdd = itemView.findViewById(R.id.ivAddToCart);
        }
    }
}
