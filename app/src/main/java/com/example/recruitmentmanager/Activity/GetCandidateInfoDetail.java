package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.Model.User;
import com.example.recruitmentmanager.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCandidateInfoDetail extends AppCompatActivity {
    SharedPreferencesManager sharedPreferencesManager;
    EditText  tvName;
    EditText  tvEmail;
    EditText  tvPhone;
    EditText  tvAddress;
    EditText  tvRole;
    EditText  tvStatus;
    AppCompatButton btnDelete, btnUpdate;
    int idUserLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_candidate_info_detail);

        AnhXa();
        idUserLogin=sharedPreferencesManager.getUserAuthLogin().getId();
        getData();

    }

    private void AnhXa() {
        sharedPreferencesManager = new SharedPreferencesManager(this);
        tvName=findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvRole = findViewById(R.id.tvRole);
        tvStatus = findViewById(R.id.tvStatus);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete=findViewById(R.id.btnDelete);
    }

    private void getData(){
        ApiService apiService = ApiUtils.getAPIService();
        Call<User> candidateDetailCall = apiService.getCandidateDetail(idUserLogin);
        candidateDetailCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(response.isSuccessful()){
                    User userInfo = response.body();
                    if (userInfo != null) {
                        tvName.setText(userInfo.getName());
                        tvPhone.setText(userInfo.getPhone_number());
                        tvAddress.setText(userInfo.getAddress());
                        tvEmail.setText(sharedPreferencesManager.getUserAuthLogin().getEmail());
                        tvRole.setText(sharedPreferencesManager.getUserAuthLogin().getRole());
                        tvStatus.setText(sharedPreferencesManager.getUserAuthLogin().getStatus());
                    }
                }
            }


            @Override
            public void onFailure(Call<User> call, Throwable t) {

            }
        });

    }
}