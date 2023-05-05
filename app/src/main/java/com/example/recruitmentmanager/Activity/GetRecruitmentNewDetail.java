package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.AuthLoginResponse;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecruitmentNewDetail extends AppCompatActivity {
    Button btnUngTuyen;
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
    }

    private void AnhXa() {
        btnUngTuyen = findViewById(R.id.apply_button);
        tvcompanyName = findViewById(R.id.job_companyname);
        tvdecription = findViewById(R.id.job_desription);
        tvexpiredAt = findViewById(R.id.job_expired_at);
        tvlevel = findViewById(R.id.job_level);
        tvQuantity = findViewById(R.id.job_quantity);
        tvtechnical = findViewById(R.id.job_technical);
        tvtitle = findViewById(R.id.job_title);
        tvSalary = findViewById(R.id.job_salary);
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
                        tvtechnical.setText(recruitmentInfo.getMaster_technical().getName().toString());
                        tvlevel.setText(recruitmentInfo.getMaster_level().getName().toString());
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