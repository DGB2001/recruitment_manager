package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationResponse;
import com.example.recruitmentmanager.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateApplicationActivity extends AppCompatActivity {
    AppCompatButton btnHuy, btnNopDon;
    TextView tv_recruitmentNewsName;
    EditText etTittle, etContent, etMaster_level, etMaster_technical;
    SharedPreferencesManager sharedPreferencesManager;

    String recruitment_news_tittle;
    private int idRecruitmentNews;
    private int candidate_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);
        AnhXa();

        Intent intent = getIntent();
        idRecruitmentNews = intent.getIntExtra("idRecruitmentNews", -1);
        recruitment_news_tittle = intent.getStringExtra("tittleRecruitmentNews");
        tv_recruitmentNewsName.setText(recruitment_news_tittle);
        candidate_id=sharedPreferencesManager.getUserAuthLogin().getCandidate_id();

        btnNopDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickNopDon();
            }
        });
    }
    private void AnhXa(){
        btnHuy=findViewById(R.id.btnHuy);
        btnNopDon=findViewById(R.id.btnNopDon);
        tv_recruitmentNewsName=findViewById(R.id.tv_recruitmentNewsName);
        etTittle=findViewById(R.id.etTittle);
        etContent=findViewById(R.id.etContent);
        etMaster_level=findViewById(R.id.etMaster_level);
        etMaster_technical=findViewById(R.id.etMaster_technical);
        sharedPreferencesManager=new SharedPreferencesManager(this);
    }

    private void clickNopDon(){
        String Tittle = etTittle.getText().toString();
        String Content = etContent.getText().toString();
        String level = etMaster_level.getText().toString();
        String technical = etMaster_technical.getText().toString();
        int Master_level;
        int Master_technical;

        if (Tittle.isEmpty()) {
            etTittle.requestFocus();
            etTittle.setError("Vui lòng điền đầy đủ thông tin");
        }

        if (Content.isEmpty()) {
            etContent.requestFocus();
            etContent.setError("Vui lòng điền đầy đủ thông tin");
        }

        if (level.isEmpty()) {
            etMaster_level.requestFocus();
            etMaster_level.setError("Vui lòng điền đầy đủ thông tin");
            Master_level = 1;
//            Master_level = Integer.parseInt(String.valueOf(level));
        }
        else {
            Master_level = 1;
        }

        if (technical.isEmpty()) {
            etMaster_technical.requestFocus();
            etMaster_technical.setError("Vui lòng điền đầy đủ thông tin");
            Master_technical = 1;
//            Master_technical=Integer.parseInt(technical);
        }
        else {
            Master_technical = 1;
        }



        ApiService apiService = ApiUtils.getAPIService();
        Call<ApplicationResponse> applicationResponseCall = apiService.createApplicationData(candidate_id, Master_technical, Master_level, idRecruitmentNews,Tittle,Content);
        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG", "0." + response.code());
                    Toast.makeText(CreateApplicationActivity.this, "Ứng tuyển thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateApplicationActivity.this,GetRecruitmentNewsList.class);
                    startActivity(intent);
                    finish();
                }
                else {
                    Log.e("TAG", "1. " + response.code());
                    Log.e("TAG", "2. " + candidate_id);
                    Log.e("TAG", "3. " + Master_technical);
                    Log.e("TAG", "4. " + Master_level);
                    Log.e("TAG", "5. " + idRecruitmentNews);
                    Log.e("TAG", "6. " + Tittle);
                    Log.e("TAG", "7. " + Content);
                }
            }

            @Override
            public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                Log.e("TAG", "8." + t.getMessage());
            }
        });

    }
}