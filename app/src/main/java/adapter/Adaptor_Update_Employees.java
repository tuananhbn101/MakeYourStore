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

import java.util.List;

import object_App.Account;

public class Adaptor_Update_Employees extends RecyclerView.Adapter<Adaptor_Update_Employees.ViewHolder> {
    List<Account> accountList;
    Click_Manage_Account click_manage_account;
    private Context context;

    public Adaptor_Update_Employees(List<Account> accountList, Context context) {
        this.accountList = accountList;
        this.context = context;
    }

    public  void setClick_manage_account(Click_Manage_Account click_manage_account){
        this.click_manage_account = click_manage_account;
    }
    @NonNull
    @Override
    public Adaptor_Update_Employees.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.update_employees,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull Adaptor_Update_Employees.ViewHolder holder, int position) {
        Account account = accountList.get(position);
        holder.tvFullName.setText(account.getFullName());
        holder.tvDate.setText(account.getDateOfBirth());
        holder.tvPhone.setText(account.getPhone());
        holder.tvID.setText(account.getID()+"");
        Picasso.with(context).load("file://"+account.getAvatar()).into(holder.ivAvatar);
        holder.btnShowInf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_manage_account.clickShowInf(position);
            }
        });
        holder.btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                click_manage_account.clickBtnDelete(position,account.getID());
            }
        });
    }

    @Override
    public int getItemCount() {
        return accountList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvFullName,tvDate,tvPhone,btnShowInf,btnDeleteAccount,tvID;
        ImageView ivAvatar;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFullName = itemView.findViewById(R.id.tvNameAccount);
            tvDate = itemView.findViewById(R.id.tvDateAcount);
            tvPhone = itemView.findViewById(R.id.tvPhoneAccount);
            tvID= itemView.findViewById(R.id.tvIDAccount);
            btnDeleteAccount = itemView.findViewById(R.id.btnDeleteAccount);
            btnShowInf = itemView.findViewById(R.id.btnShowInf);
            ivAvatar = itemView.findViewById(R.id.tvAvatar);
        }
    }
}
