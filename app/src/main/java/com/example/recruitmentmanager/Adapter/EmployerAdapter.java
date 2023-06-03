package com.example.recruitmentmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitmentmanager.Model.EmployerInfo;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;
import java.util.List;

public class EmployerAdapter extends RecyclerView.Adapter<EmployerAdapter.EmployerViewHolder>{
    private List<EmployerInfo> employerInfoList;
    private Context context;

    public EmployerAdapter(Activity Context, List<EmployerInfo> employerInfoList) {
        this.employerInfoList = employerInfoList;
        this.context = Context;
    }

    @NonNull
    @Override
    public EmployerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_employer, parent, false);
        return new EmployerAdapter.EmployerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EmployerViewHolder holder, int position) {
        EmployerInfo employerInfo = employerInfoList.get(position);
        if (employerInfo == null) {
            return;
        }
        holder.tv_company_name.setText(employerInfo.getCompany_name());
        holder.tv_company_address.setText(employerInfo.getAddress());
        holder.tv_company_phone.setText(employerInfo.getPhone_number());
        holder.img_location.setImageResource(R.drawable.ic_location);
        holder.img_phonecall.setImageResource(R.drawable.ic_phone);
    }

    @Override
    public int getItemCount() {
        if (employerInfoList != null) {
            return employerInfoList.size();
        }
        return 0;
    }

    public static class EmployerViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView tv_company_name;
        private final TextView tv_company_address;
        private final TextView tv_company_phone;
        private final ImageView img_location, img_phonecall;


        public EmployerViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView_employer);
            tv_company_name = itemView.findViewById(R.id.tv_company_name);
            tv_company_address = itemView.findViewById(R.id.tv_company_address);
            tv_company_phone = itemView.findViewById(R.id.tv_company_phone);
            img_location = itemView.findViewById(R.id.img_location);
            img_phonecall = itemView.findViewById(R.id.img_phonecall);
        }
    }
}
