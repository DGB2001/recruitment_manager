package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.recruitmentmanager.Adapter.HistoryApplicationAdapter;
import com.example.recruitmentmanager.Adapter.RecruitmentApplicationAdapter;
import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecruitmentApplicationList extends AppCompatActivity implements View.OnClickListener {
    ImageView imgNotFound;
    TextView tvJobTittle;
    SharedPreferencesManager sharedPreferences;
    RecyclerView rcvRecruitmentApplication;
    List<ApplicationInfo> applicationInfoList;
    int idRecruitmentNews;
    String jobTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_recruitment_application_list);

        AnhXa();
        setOnClick();
        getAndSetData();
        getRecruitmentApplication();
    }

    private void AnhXa() {
        imgNotFound = findViewById(R.id.imgNotFound);
        tvJobTittle = findViewById(R.id.tvJobTittle);
        applicationInfoList = new ArrayList<>();
        sharedPreferences = new SharedPreferencesManager(this);
        rcvRecruitmentApplication = findViewById(R.id.rcvRecruitmentApplication);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvRecruitmentApplication.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvRecruitmentApplication.addItemDecoration(dividerItemDecoration);
    }

    private void getAndSetData() {
        Intent intent = getIntent();
        idRecruitmentNews = intent.getIntExtra("idRecruitmentNews", -1);
        jobTittle = intent.getStringExtra("jobTittle");
        tvJobTittle.setText(jobTittle);
    }

    private void setOnClick() {
        imgNotFound.setOnClickListener(this);
    }

    private void getRecruitmentApplication() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<List<ApplicationInfo>> applicationistCall = apiService.getRecruitmentApplication(idRecruitmentNews);
        applicationistCall.enqueue(new Callback<List<ApplicationInfo>>() {
            @Override
            public void onResponse(Call<List<ApplicationInfo>> call, Response<List<ApplicationInfo>> response) {
                if (response.isSuccessful()) {
                    applicationInfoList = response.body();
                    if (applicationInfoList.size() > 0) {
                        RecruitmentApplicationAdapter recruitmentApplicationAdapter = new RecruitmentApplicationAdapter(GetRecruitmentApplicationList.this, applicationInfoList);
                        rcvRecruitmentApplication.setAdapter(recruitmentApplicationAdapter);
                    } else {
                        imgNotFound.setVisibility(View.VISIBLE);
                        imgNotFound.setImageResource(R.drawable.bg_no_item_found);
                    }
                    Log.e("getRecruitmentApplication", "Successful: " + response.code());

                } else {
                    Log.e("getRecruitmentApplication", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ApplicationInfo>> call, Throwable t) {
                Log.e("getRecruitmentApplication", "onFailure: " + t.getMessage());

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgNotFound:
                intent = new Intent(GetRecruitmentApplicationList.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }
}