package com.example.recruitmentmanager.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.example.recruitmentmanager.Activity.UpdateResultApplication;
import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.R;

import java.util.List;

public class RecruitmentApplicationAdapter extends RecyclerView.Adapter<RecruitmentApplicationAdapter.RecruitmentApplicationViewHolder> {
    private final List<ApplicationInfo> applicationInfoList;
    private Context context;

    public RecruitmentApplicationAdapter(Context context, List<ApplicationInfo> applicationInfoList) {
        this.applicationInfoList = applicationInfoList;
        this.context = context;
    }

    @NonNull
    @Override
    public RecruitmentApplicationViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recruitment_application, parent, false);
        return new RecruitmentApplicationAdapter.RecruitmentApplicationViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecruitmentApplicationViewHolder holder, int position) {
        ApplicationInfo applicationInfo = applicationInfoList.get(position);
        if (applicationInfo == null) {
            return;
        }
        holder.imgv.setImageResource(R.drawable.ic_application);
        holder.tv_candidateName.setText(applicationInfo.getCandidate().getName());
        holder.tv_technical.setText(applicationInfo.getMaster_technical().getName());
        holder.tv_level.setText(applicationInfo.getMaster_level().getName());
        holder.tv_result.setText(String.valueOf(applicationInfo.getResult()));

        Integer result;
        if (!holder.tv_result.getText().equals("null")) {
            result = Integer.parseInt(String.valueOf(applicationInfo.getResult()));
            if (result == 0) {
                holder.tv_result.setText("Từ chối");
                holder.tv_result.setBackgroundResource(R.drawable.bg_application_rejected);
                holder.tv_result.setTextColor(ContextCompat.getColor(context, R.color.txtrejected));
            }

            if (result == 1) {
                holder.tv_result.setText("Chấp nhận");
                holder.tv_result.setBackgroundResource(R.drawable.bg_application_accepted);
                holder.tv_result.setTextColor(ContextCompat.getColor(context, R.color.txtaccepted));
            }
        } else {
            holder.tv_result.setText("Chưa có kết quả");
            holder.tv_result.setBackgroundResource(R.drawable.bg_application_sent);
            holder.tv_result.setTextColor(ContextCompat.getColor(context, R.color.txtsent));

            holder.cardView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, UpdateResultApplication.class);
                    intent.putExtra("applicationId",applicationInfo.getId());
                    intent.putExtra("recruitmentNewsId",applicationInfo.getRecruitment_news_id());
                    intent.putExtra("jobTittle",applicationInfo.getTitle());
                    intent.putExtra("candidateName",applicationInfo.getCandidate().getName());
                    intent.putExtra("candidatePhone",applicationInfo.getCandidate().getPhone_number());
                    intent.putExtra("candidateAddress",applicationInfo.getCandidate().getAddress());
                    intent.putExtra("applicationContent",applicationInfo.getContent());
                    intent.putExtra("technicalName",applicationInfo.getMaster_technical().getName());
                    intent.putExtra("levelName",applicationInfo.getMaster_technical().getName());
                    context.startActivity(intent);
                }
            });
        }


    }

    @Override
    public int getItemCount() {
        if (applicationInfoList != null) {
            return applicationInfoList.size();
        }
        return 0;
    }

    public static class RecruitmentApplicationViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        ImageView imgv;
        TextView tv_candidateName, tv_technical, tv_level, tv_result;

        public RecruitmentApplicationViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardView);
            imgv = itemView.findViewById(R.id.img_application);
            tv_candidateName = itemView.findViewById(R.id.tv_candidateName);
            tv_technical = itemView.findViewById(R.id.tv_technical);
            tv_level = itemView.findViewById(R.id.tv_level);
            tv_result = itemView.findViewById(R.id.tv_result);
        }
    }
}
