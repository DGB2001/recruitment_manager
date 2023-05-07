package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateResultApplication extends AppCompatActivity implements View.OnClickListener {
    int applicationId, recruitmentNewsId;
    String candidateName,candidatePhone, candidateAddress, applicationContent, jobTittle, technicalName, levelName;
    TextView tvTittle, tvCandidateName, tvPhone, tvAddress, tvContent, tvLevel, tvTechnical;
    AppCompatButton btnAccept, btnReject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_update_result_application);

        AnhXa();
        getAndSetData();
        setOnClick();
    }

    private void AnhXa() {
        tvTittle=findViewById(R.id.tvTittle);
        tvCandidateName=findViewById(R.id.tvCandidateName);
        tvPhone=findViewById(R.id.tvPhone);
        tvAddress=findViewById(R.id.tvAddress);
        tvContent=findViewById(R.id.tvContent);
        tvLevel=findViewById(R.id.tvLevel);
        tvTechnical=findViewById(R.id.tvTechnical);
        btnAccept=findViewById(R.id.btnAccept);
        btnReject=findViewById(R.id.btnReject);
    }

    private void getAndSetData(){
        Intent intent = getIntent();
        applicationId = intent.getIntExtra("applicationId", -1);
        recruitmentNewsId = intent.getIntExtra("recruitmentNewsId", -1);
        candidateName=intent.getStringExtra("candidateName");
        candidatePhone=intent.getStringExtra("candidatePhone");
        candidateAddress=intent.getStringExtra("candidateAddress");
        applicationContent=intent.getStringExtra("applicationContent");
        jobTittle=intent.getStringExtra("jobTittle");
        technicalName = intent.getStringExtra("technicalName");
        levelName = intent.getStringExtra("levelName");

        tvTittle.setText(jobTittle);
        tvCandidateName.setText(candidateName);
        tvPhone.setText(candidatePhone);
        tvAddress.setText(candidateAddress);
        tvContent.setText(applicationContent);
        tvTechnical.setText(technicalName);
        tvLevel.setText(levelName);
    }

    private void setOnClick() {
        btnAccept.setOnClickListener(this);
        btnReject.setOnClickListener(this);
    }

    private void updateApplicationResult(int result){
        ApiService apiService = ApiUtils.getAPIService();
        Call<ApplicationInfo> applicationistCall = apiService.updateApplicationResult(recruitmentNewsId,applicationId,result);
        applicationistCall.enqueue(new Callback<ApplicationInfo> () {
            @Override
            public void onResponse(Call<ApplicationInfo> call, Response<ApplicationInfo> response) {
                Log.e("err","1:recruitmentNewsId " + recruitmentNewsId);
                Log.e("err","2:applicationId " + applicationId);
                Log.e("err","3:result " + result);
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateResultApplication.this, "Thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(UpdateResultApplication.this,GetRecruitmentApplicationList.class);
                    startActivity(intent);
                    finish();
                    Log.e("err","err: " + response.code());
                }
                else {
                    Log.e("err","err: " + response.code());

                }
            }

            @Override
            public void onFailure(Call<ApplicationInfo> call, Throwable t) {
                Log.e("onFailure","onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAccept:
                updateApplicationResult(1);
                break;

            case R.id.btnReject:
                updateApplicationResult(0);
                break;

            default:
                break;
        }
    }
}