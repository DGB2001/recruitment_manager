package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationResponse;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateRecruitmentNews extends AppCompatActivity implements View.OnClickListener{
    AppCompatButton btnHuy, btnNopDon;

    EditText etTittle, etDescription, etSalary, etQuantity, etExpired_at;
    Spinner spinnerMaster_technical, spinnerMaster_level;
    ArrayList<String> technical, level;
    private int employer_id, technical_id, level_id;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_recruitment_news);
        Anhxa();
        setOnClick();
        spinnerMaster_technical();
        spinnerMaster_level();
    }

    private void Anhxa() {
        btnHuy = findViewById(R.id.btnHuy);
        btnNopDon = findViewById(R.id.btnNopDon);
        etTittle = findViewById(R.id.etTittle);
        etDescription = findViewById(R.id.etDescription);
        etQuantity = findViewById(R.id.etQuantity);
        etSalary = findViewById(R.id.etSalary);
        etExpired_at = findViewById(R.id.etExpired_at);
        spinnerMaster_technical = findViewById(R.id.spinnerMaster_technical);
        spinnerMaster_level = findViewById(R.id.spinnerMaster_level);
        sharedPreferencesManager = new SharedPreferencesManager(this);
    }
    private void setOnClick() {
        btnHuy.setOnClickListener(this);
        btnNopDon.setOnClickListener(this);
    }
    public void spinnerMaster_technical() {
        technical = new ArrayList<String>();
        technical.add("PHP");
        technical.add("NodeJS");
        technical.add("Golang");
        technical.add("Java");
        technical.add("Python");
        technical.add(".NET");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, technical);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerMaster_technical.setAdapter(adapter);
    }

    public void spinnerMaster_level() {
        level = new ArrayList<String>();
        level.add("Intern");
        level.add("Fresher");
        level.add("Junior");
        level.add("Middle");
        level.add("Senior");
        level.add("Leader");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, level);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerMaster_level.setAdapter(adapter);
    }

    private void TaoTin() {

        String title = etTittle.getText().toString();
        String description = etDescription.getText().toString();
        int salary = Integer.parseInt(String.valueOf(etSalary.getText().toString()));
        int quantity = Integer.parseInt(String.valueOf(etQuantity.getText().toString()));
        String expiredAt = etExpired_at.getText().toString();
        int master_level = spinnerMaster_level.getSelectedItemPosition() + 1;
        int master_technical = spinnerMaster_technical.getSelectedItemPosition() + 1;
        employer_id = sharedPreferencesManager.getUserAuthLogin().getEmployer_id();

        ApiService apiService = ApiUtils.getAPIService();
        Call<ApplicationResponse> applicationResponseCall = apiService.createRecruitmentNews( employer_id,master_technical, master_level, title, description, salary,quantity,expiredAt);
        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()) {
                    Log.e("onFailure", "1: " + response.code());
                    Toast.makeText(CreateRecruitmentNews.this, "Tạo tin thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateRecruitmentNews.this, GetRecruitmentNewsList.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnHuy:
                intent = new Intent(this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnNopDon:
                TaoTin();
                break;

            default:
                break;
        }
    }
}