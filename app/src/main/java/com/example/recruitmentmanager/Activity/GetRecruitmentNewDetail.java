package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.AuthLoginResponse;
import com.example.recruitmentmanager.Model.LevelInfo;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.Model.TechnicalInfo;
import com.example.recruitmentmanager.Model.User;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecruitmentNewDetail extends AppCompatActivity {
    Button btnUngTuyen, btnUpdateRecruitmentNews, btnShowList;
    SharedPreferencesManager sharedPreferences;
    ArrayList<String> technical, level;
    Spinner spinnerMaster_technical, spinnerMaster_level;
    LinearLayout linearLayout;
    int technical_id, level_id;

    TextView tvtitle, tvcompanyName, tvSalary, tvQuantity, tvexpiredAt, tvtechnical, tvlevel, tvdecription;
    int idRecruitmentNews = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_recruitment_news_detail);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        AnhXa();
        idRecruitmentNews = getIntent().getIntExtra("idRecruitmentNews", -1);
        getRecruitmentNewsDetail();
        spinnerMaster_technical();
        spinnerMaster_level();
        Intent intent = getIntent();
        idRecruitmentNews = intent.getIntExtra("idRecruitmentNews", -1);
        technical_id = intent.getIntExtra("idTechnical", -1);
        level_id = intent.getIntExtra("idLevel", -1);
        spinnerMaster_technical.setSelection(technical_id - 1);
        spinnerMaster_level.setSelection(level_id - 1);




        if (sharedPreferences.getUserAuthLogin().getRole().equals("Ứng viên")) {
            linearLayout.setVisibility(View.GONE);
            tvtitle.setEnabled(false);
            tvSalary.setEnabled(false);
            tvQuantity.setEnabled(false);
            tvexpiredAt.setEnabled(false);
            tvtechnical.setEnabled(false);
            tvlevel.setEnabled(false);
            tvdecription.setEnabled(false);
        }

        if (sharedPreferences.getUserAuthLogin().getRole().equals("Nhà tuyển dụng")) {
            btnUngTuyen.setVisibility(View.GONE);
        }

        btnUpdateRecruitmentNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateRecruitmentNewsDetail();
            }
        });

        btnShowList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GetRecruitmentNewDetail.this,GetRecruitmentApplicationList.class);
                intent1.putExtra("idRecruitmentNews",idRecruitmentNews);
                intent1.putExtra("jobTittle",tvtitle.getText().toString());
                startActivity(intent1);
            }
        });


    }


    private void AnhXa() {
        linearLayout = findViewById(R.id.btnEmployer);
        sharedPreferences = new SharedPreferencesManager(this);
        btnUngTuyen = findViewById(R.id.apply_button);
        tvcompanyName = findViewById(R.id.job_companyname);
        tvdecription = findViewById(R.id.job_desription);
        tvexpiredAt = findViewById(R.id.job_expired_at);
        tvQuantity = findViewById(R.id.job_quantity);
        tvtitle = findViewById(R.id.job_title);
        tvSalary = findViewById(R.id.job_salary);
        spinnerMaster_technical = findViewById(R.id.spinnerMaster_technical);
        spinnerMaster_level = findViewById(R.id.spinnerMaster_level);
        btnUpdateRecruitmentNews = findViewById(R.id.btnUpdate1);
        btnShowList=findViewById(R.id.btnShowList);
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
    private void updateRecruitmentNewsDetail(){
        String title = String.valueOf(tvtitle.getText());
        String description = String.valueOf(tvdecription.getText());
        String expiredAt = String.valueOf(tvexpiredAt.getText());
        int salary = Integer.parseInt(String.valueOf(tvSalary.getText()));
        int quantity = Integer.parseInt(String.valueOf(tvQuantity.getText()));
        int Master_level = spinnerMaster_level.getSelectedItemPosition() + 1;
        int Master_technical = spinnerMaster_technical.getSelectedItemPosition() + 1;
        int employer_id = sharedPreferences.getUserAuthLogin().getEmployer_id();

        ApiService apiService = ApiUtils.getAPIService();
        Call<RecruitmentInfo> recruitmentCall = apiService.updateRecruitmentNew(idRecruitmentNews,employer_id,Master_technical,Master_level,title,description,salary,quantity,expiredAt);
        recruitmentCall.enqueue(new Callback<RecruitmentInfo>() {
            @Override
            public void onResponse(Call<RecruitmentInfo> call, Response<RecruitmentInfo> response) {

                if (response.isSuccessful()) {
                    spinnerMaster_technical.setSelection(Master_technical-1);
                    spinnerMaster_level.setSelection(Master_level-1);
                    tvtitle.setText(title);
                    tvdecription.setText(description);
                    tvSalary.setText(String.valueOf(salary));
                    tvQuantity.setText(String.valueOf(quantity));
                    tvexpiredAt.setText(expiredAt);
                    Toast.makeText(GetRecruitmentNewDetail.this, "thành công", Toast.LENGTH_SHORT).show();
                    Intent intent =new Intent(GetRecruitmentNewDetail.this,GetRecruitmentNewsList.class);
                    startActivity(intent);
                    finish();
                }
                else{
                    Log.e("onFailure", "onFailure: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<RecruitmentInfo> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }


    private void getRecruitmentNewsDetail() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<RecruitmentInfo> recruitmentInfo = apiService.getRecruitmentNewDetail(idRecruitmentNews);
        recruitmentInfo.enqueue(new Callback<RecruitmentInfo>() {
            @Override
            public void onResponse(Call<RecruitmentInfo> call, Response<RecruitmentInfo> response) {
                if (response.isSuccessful()) {
                    RecruitmentInfo recruitmentInfo = response.body();
                    if (recruitmentInfo != null) {
                        tvtitle.setText(recruitmentInfo.getTitle().toString());
                        tvcompanyName.setText(recruitmentInfo.getEmployer().getCompany_name().toString());
                        tvdecription.setText(recruitmentInfo.getDescription().toString());
                        tvSalary.setText(String.valueOf(recruitmentInfo.getSalary()));
                        tvexpiredAt.setText(recruitmentInfo.getExpired_at().toString());
                      //  tvtechnical.setText(recruitmentInfo.getMaster_technical().getName().toString());
                        //tvlevel.setText(recruitmentInfo.getMaster_level().getName().toString());

                        tvQuantity.setText(String.valueOf(recruitmentInfo.getQuantity()));
                    }
                    btnUngTuyen.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            Intent intent = new Intent(GetRecruitmentNewDetail.this, CreateApplicationActivity.class);
                            intent.putExtra("idRecruitmentNews", recruitmentInfo.getId());
                            intent.putExtra("tittleRecruitmentNews", recruitmentInfo.getTitle());
                            intent.putExtra("technicalRecruitmentNews", recruitmentInfo.getMaster_technical().getId());
                            intent.putExtra("levelRecruitmentNews", recruitmentInfo.getMaster_level().getId());
                            startActivity(intent);
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<RecruitmentInfo> call, Throwable t) {
                Log.e("TAG", "err." + t.getMessage());
            }
        });

    }

}