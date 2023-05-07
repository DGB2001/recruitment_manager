package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.WindowManager;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;
import java.util.List;

public class GetRecruitmentApplicationList extends AppCompatActivity {
    SharedPreferencesManager sharedPreferences;
    RecyclerView rcvRecruitmentApplication;
    List<ApplicationInfo> applicationInfoList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_recruitment_application_list);

        AnhXa();

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvRecruitmentApplication.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvRecruitmentApplication.addItemDecoration(dividerItemDecoration);
    }

    private void AnhXa() {
        applicationInfoList = new ArrayList<>();
        sharedPreferences=new SharedPreferencesManager(this);
        rcvRecruitmentApplication = findViewById(R.id.rcvRecruitmentApplication);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvRecruitmentApplication.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvRecruitmentApplication.addItemDecoration(dividerItemDecoration);
    }

    private void getRecruitmentApplication(){
        
    }
}