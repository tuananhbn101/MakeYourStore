package Activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.makeyourstore.R;
import com.example.makeyourstore.databinding.ListImageChooseBinding;

import java.util.ArrayList;
import java.util.List;

import adapter.Adapter_Choose_Image;
import adapter.ClickProduct;

public class List_Image_Choose extends AppCompatActivity {
    ListImageChooseBinding binding;
    Adapter_Choose_Image adapter_choose_image;
    List<String> stringList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_image_choose);
        String[] listChoose = {String.valueOf(R.drawable.chuot0), String.valueOf(R.drawable.chuot1), String.valueOf(R.drawable.chuot2), String.valueOf(R.drawable.chuot3), String.valueOf(R.drawable.chuot4), String.valueOf(R.drawable.chuot5), String.valueOf(R.drawable.chuot6), String.valueOf(R.drawable.banphim1)
                , String.valueOf(R.drawable.banphim2),String.valueOf(R.drawable.banphim3), String.valueOf(R.drawable.banphim4), String.valueOf(R.drawable.banphim5), String.valueOf(R.drawable.banphim6), String.valueOf(R.drawable.banphim7), String.valueOf(R.drawable.sac1), String.valueOf(R.drawable.sac2), String.valueOf(R.drawable.sac3), String.valueOf(R.drawable.lot1), String.valueOf(R.drawable.lot2), String.valueOf(R.drawable.lot3)};

        stringList = new ArrayList<>();
        for (int i = 0;i<listChoose.length;i++){
            stringList.add(listChoose[i]);
        }
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getBaseContext(),3,RecyclerView.VERTICAL,false);
        adapter_choose_image = new Adapter_Choose_Image(stringList);
        binding.rvListAnh.setLayoutManager(layoutManager);
        binding.rvListAnh.setAdapter(adapter_choose_image);
        Intent intent = getIntent();
        String  name = intent.getStringExtra("name");
        String  price = intent.getStringExtra("price");
        String  amount = intent.getStringExtra("amount");
        String  type = intent.getStringExtra("type");
        String  describe = intent.getStringExtra("describe");
        String  producer = intent.getStringExtra("producer");
        adapter_choose_image.setClickProduct(new ClickProduct() {
            @Override
            public void clickProductImage(String image) {
                Intent intent1 = new Intent(getApplicationContext(), Add_Product.class);
                intent1.putExtra("name", name);
                intent1.putExtra("price", price);
                intent1.putExtra("amount", amount);
                intent1.putExtra("type", type);
                intent1.putExtra("describe", describe);
                intent1.putExtra("imageKey",image);
                intent1.putExtra("producer", producer);
                startActivity(intent1);
            }
        });
    }
}
