package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.databinding.DataBindingUtil;

import com.example.makeyourstore.R;

import java.text.DecimalFormat;
import java.util.List;

import object_App.Account;
import object_App.Bill;
import object_App.Product;
import object_App.Report;

public class
Adapter_History_Sell extends BaseAdapter {
    List<Bill> billList;
    String fullName;

    public Adapter_History_Sell(List<Bill> billList, String fullName) {
        this.billList = billList;
        this.fullName = fullName;
    }

    @Override
    public int getCount() {
        return billList.size();
    }

    @Override
    public Object getItem(int position) {
        return billList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.history_list,parent,false);
        TextView idHistory = view.findViewById(R.id.tvIDHistory);
        TextView date = view.findViewById(R.id.tvDateSell);
        TextView amount = view.findViewById(R.id.tvAmountSell);
        TextView name = view.findViewById(R.id.tvNameProductSell);
        TextView price = view.findViewById(R.id.tvPriceBill);
        TextView total = view.findViewById(R.id.tvtotalBill);
        TextView employee = view.findViewById(R.id.tvEmployee);
        idHistory.setText(billList.get(position).getID()+"");
        date.setText(billList.get(position).getDate());
        String nameProduct = "";
        String priceProduct = "";
        String amountProduct = "";
        String nameList[] = billList.get(position).getNames().split(";");
        String priceList[] = billList.get(position).getPrice().split(";");
        String amountList[] = billList.get(position).getAmount().split(";");
        if(nameList.length >1){
            for (int i = 0; i<nameList.length;i++){
                nameProduct += nameList[i]+"\n";
                DecimalFormat formatter = new DecimalFormat("###,###,###");
                String priceFormat = formatter.format(Integer.valueOf(priceList[i]))+" đ";
                priceProduct += priceFormat+"\n";
                amountProduct += amountList[i]+"\n";
            }
        }
        else{
            nameProduct += billList.get(position).getNames();
            DecimalFormat formatter = new DecimalFormat("###,###,###");
            String priceFormat = formatter.format(Integer.valueOf(billList.get(position).getPrice()))+" đ";
           priceProduct += priceFormat;
           amountProduct += billList.get(position).getAmount();
        }
        name.setText(nameProduct);
        amount.setText(amountProduct);
        price.setText(priceProduct);
        employee.setText(fullName);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String totalFormat = formatter.format(billList.get(position).getTotal())+" đ";
        total.setText(totalFormat);
        return view;
    }
}
