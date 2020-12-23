package adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.makeyourstore.R;

import java.text.DecimalFormat;
import java.util.List;

import object_App.Report;

public class Adapter_Report  extends BaseAdapter {
    List<Report> reportList;

    public Adapter_Report(List<Report> reportList) {
        this.reportList = reportList;
    }

    @Override
    public int getCount() {
        return reportList.size();
    }

    @Override
    public Object getItem(int position) {
        return reportList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view =layoutInflater.inflate(R.layout.list_report,parent,false);
        Report report = reportList.get(position);
        TextView date = view.findViewById(R.id.date);
        TextView id = view.findViewById(R.id.id);
        TextView totalImport = view.findViewById(R.id.idProduct);
        TextView toalSale = view.findViewById(R.id.total);
        date.setText(report.getDate());
        id.setText(report.getId()+"");
        DecimalFormat formatter1 = new DecimalFormat("###,###,###");
        String totlaImport1 = formatter1.format(report.getTotalImport())+" đ";
        totalImport.setText(totlaImport1);
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        String totla = formatter.format(report.getTotalSale())+" đ";
        toalSale.setText(totla);
        return view;
    }
}
