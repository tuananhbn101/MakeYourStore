package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.R;

import java.util.List;

public class Adapter_Choose_Image extends RecyclerView.Adapter<Adapter_Choose_Image.ViewHolder> {
    List<String> stringList;
    ClickProduct clickProduct;

    public void setClickProduct(ClickProduct clickProduct) {
        this.clickProduct = clickProduct;
    }

    public Adapter_Choose_Image(List<String> stringList) {
        this.stringList = stringList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.choose_image_fragment, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.ivProduct.setImageResource(Integer.valueOf(stringList.get(position)));
        holder.ivProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickProduct.clickProductImage(stringList.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return stringList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView ivProduct;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            ivProduct = itemView.findViewById(R.id.ivImageProductChoose);
        }
    }
}
