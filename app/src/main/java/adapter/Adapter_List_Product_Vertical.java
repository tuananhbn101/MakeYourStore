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

public class Adapter_List_Product_Vertical extends RecyclerView.Adapter<Adapter_List_Product_Vertical.ViewHolder> {
    List<Product> productList;
    ClickImageProductToShow clickImageProductToShow;
    private Context context;

    public Adapter_List_Product_Vertical(List<Product> productList, Context context) {
        this.productList = productList;
        this.context = context;
    }

    public void setClickImageProductToShow (ClickImageProductToShow clickImageProductToShow){
        this.clickImageProductToShow = clickImageProductToShow;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.list_item_vertical,parent,false);
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
        Picasso.with(this.context).load("file://"+product.getImage()).into(holder.ivProduct);
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImageProductToShow.clickImageProduct(position);
            }
        });
        holder.ivAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickImageProductToShow.clickAdd(product.getID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvName,tvPrice;
        ImageView ivProduct;
        ImageView ivAdd;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvName = itemView.findViewById(R.id.tvNameProduct1);
            tvPrice = itemView.findViewById(R.id.tvPrice1);
            ivProduct = itemView.findViewById(R.id.ivImageProduct1);
            ivAdd= itemView.findViewById(R.id.btnAdd1);
        }
    }
}
