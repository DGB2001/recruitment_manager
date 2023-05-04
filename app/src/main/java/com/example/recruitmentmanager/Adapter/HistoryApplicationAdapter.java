package com.example.recruitmentmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.ColorRes;
import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.R;

import java.util.List;

public class HistoryApplicationAdapter extends RecyclerView.Adapter<HistoryApplicationAdapter.HistoryApplicationViewHolder> {
    private final List<ApplicationInfo> applicationInfoList;
    private Context context;


    public HistoryApplicationAdapter(Activity Context, List<ApplicationInfo> applicationInfoList) {
        this.applicationInfoList = applicationInfoList;
        this.context = Context;
    }

    @NonNull
    @Override
    public HistoryApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history_application, parent, false);
        return new HistoryApplicationAdapter.HistoryApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryApplicationViewHolder holder, int position) {
        ApplicationInfo applicationInfo = applicationInfoList.get(position);
        if (applicationInfo == null) {
            return;
        }
        holder.imgv.setImageResource(R.drawable.ic_application);
        holder.tv_jobTittle.setText(applicationInfo.getTitle());
        holder.tv_company.setText(applicationInfo.getRecruitment_news().getEmployer().getCompany_name());
       //holder.tv_result.setText(String.valueOf(applicationInfo.getResult()));

        Integer  result;
        result = Integer.parseInt(String.valueOf(applicationInfo.getResult()));

        if(result==0){
            holder.tv_result.setText("Từ chối");
            holder.tv_result.setBackgroundResource(R.drawable.bg_application_rejected);
            holder.tv_result.setTextColor(ContextCompat.getColor(context, R.color.txtrejected));
        }

        if(result==1){
            holder.tv_result.setText("Chấp nhận");
            holder.tv_result.setBackgroundResource(R.drawable.bg_application_accepted);
            holder.tv_result.setTextColor(ContextCompat.getColor(context, R.color.txtaccepted));
        }

        if(result.equals(null)){
            holder.tv_result.setText("Chưa có kết quả");
            holder.tv_result.setBackgroundResource(R.drawable.bg_application_sent);
            holder.tv_result.setTextColor(ContextCompat.getColor(context, R.color.txtsent));
        }

    }

    @Override
    public int getItemCount() {
        if (applicationInfoList != null) {
            return applicationInfoList.size();
        }
        return 0;
    }

    public static class HistoryApplicationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgv;
        TextView tv_jobTittle, tv_company, tv_result;

        public HistoryApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imgv = itemView.findViewById(R.id.img_application);
            tv_jobTittle = itemView.findViewById(R.id.tv_jobTittle);
            tv_company = itemView.findViewById(R.id.tv_company);
            tv_result = itemView.findViewById(R.id.tv_result);
        }
    }

}

