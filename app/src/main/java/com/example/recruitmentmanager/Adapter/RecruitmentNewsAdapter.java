package com.example.recruitmentmanager.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitmentmanager.Activity.GetRecruitmentNewDetail;
import com.example.recruitmentmanager.Activity.MainActivity;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.R;

import java.util.List;

public class RecruitmentNewsAdapter extends RecyclerView.Adapter<RecruitmentNewsAdapter.RecruitmentNewsViewHolder> {
    private final List<RecruitmentInfo> recruitmentInfoList;
    private Context context;

    public RecruitmentNewsAdapter(Activity Context, List<RecruitmentInfo> recruitmentInfoList) {
        this.recruitmentInfoList = recruitmentInfoList;
        this.context = Context;
    }

    @NonNull
    @Override
    public RecruitmentNewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recruitment_news, parent, false);
        return new RecruitmentNewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitmentNewsViewHolder holder, int position) {
        RecruitmentInfo recruitmentInfo = recruitmentInfoList.get(position);
        if (recruitmentInfo == null) {
            return;
        }
        holder.tv_tittle.setText(recruitmentInfo.getTitle());
        holder.tv_employer_name.setText(recruitmentInfo.getEmployer().getCompany_name());
        holder.tv_salary.setText(String.valueOf(recruitmentInfo.getSalary()));
        holder.tv_quantity.setText(String.valueOf(recruitmentInfo.getQuantity()));
        holder.tv_time.setText(recruitmentInfo.getExpired_at());
        holder.tv_technical.setText(recruitmentInfo.getMaster_technical().getName());
        holder.tv_level.setText(recruitmentInfo.getMaster_level().getName());
        holder.img_salary.setImageResource(R.drawable.ic_salary);
        holder.img_quantity.setImageResource(R.drawable.ic_quantity);
        holder.img_time.setImageResource(R.drawable.ic_time);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, GetRecruitmentNewDetail.class);
                intent.putExtra("idRecruitmentNews", recruitmentInfo.getId());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        if (recruitmentInfoList != null) {
            return recruitmentInfoList.size();
        }
        return 0;
    }

    public static class RecruitmentNewsViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final TextView tv_tittle;
        private final TextView tv_employer_name;
        private final TextView tv_salary;
        private final TextView tv_quantity;
        private final TextView tv_time;
        private final TextView tv_technical;
        private final TextView tv_level;
        private ImageView img_salary, img_quantity, img_time;

        public RecruitmentNewsViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            tv_tittle = itemView.findViewById(R.id.tv_tittle);
            tv_employer_name = itemView.findViewById(R.id.tv_employer_name);
            tv_salary = itemView.findViewById(R.id.tv_salary);
            tv_quantity = itemView.findViewById(R.id.tv_quantity);
            tv_time = itemView.findViewById(R.id.tv_time);
            tv_technical = itemView.findViewById(R.id.tv_technical);
            tv_level = itemView.findViewById(R.id.tv_level);
            img_salary = itemView.findViewById(R.id.img_salary);
            img_quantity = itemView.findViewById(R.id.img_quantity);
            img_time = itemView.findViewById(R.id.img_time);
        }
    }
}
