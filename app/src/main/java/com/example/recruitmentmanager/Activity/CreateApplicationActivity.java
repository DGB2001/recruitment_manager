package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
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

public class CreateApplicationActivity extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnHuy, btnNopDon;
    TextView tvTittle;
    EditText etContent;
    ImageView img;
    SharedPreferencesManager sharedPreferencesManager;
    Spinner spinnerMaster_technical, spinnerMaster_level;
    ArrayList<String> technical, level;
    String recruitment_news_tittle;
    private int idRecruitmentNews;
    private int candidate_id, technical_id, level_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_create_application);
        AnhXa();
        setOnClick();
        spinnerMaster_technical();
        spinnerMaster_level();

        Intent intent = getIntent();
        idRecruitmentNews = intent.getIntExtra("idRecruitmentNews", -1);
        technical_id = intent.getIntExtra("technicalRecruitmentNews", -1);
        level_id = intent.getIntExtra("levelRecruitmentNews", -1);
        recruitment_news_tittle = intent.getStringExtra("tittleRecruitmentNews");
        candidate_id = sharedPreferencesManager.getUserAuthLogin().getCandidate_id();

        tvTittle.setText(recruitment_news_tittle);
        spinnerMaster_technical.setSelection(technical_id - 1);
        spinnerMaster_level.setSelection(level_id - 1);

        spinnerMaster_level.setEnabled(true);
        spinnerMaster_technical.setEnabled(true);
    }

    private void AnhXa() {
        img=findViewById(R.id.banner);
        img.setImageResource(R.drawable.banner_create_application);
        btnHuy = findViewById(R.id.btnHuy);
        btnNopDon = findViewById(R.id.btnNopDon);
        tvTittle = findViewById(R.id.tvTittle);
        etContent = findViewById(R.id.etContent);
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


    private void NopDon() {
        String Tittle = tvTittle.getText().toString();
        String Content = etContent.getText().toString();
        int Master_level = spinnerMaster_level.getSelectedItemPosition() + 1;
        int Master_technical = spinnerMaster_technical.getSelectedItemPosition() + 1;

        ApiService apiService = ApiUtils.getAPIService();
        Call<ApplicationResponse> applicationResponseCall = apiService.createApplicationData(candidate_id, Master_technical, Master_level, idRecruitmentNews, Tittle, Content);
        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(CreateApplicationActivity.this, "Ứng tuyển thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(CreateApplicationActivity.this, GetHistoryApplication.class);
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
                intent = new Intent(this, GetRecruitmentNewDetail.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnNopDon:
                NopDon();
                break;

            default:
                break;
        }
    }
}