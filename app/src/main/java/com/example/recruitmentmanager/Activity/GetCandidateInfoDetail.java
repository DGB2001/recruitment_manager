package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.R;

public class GetCandidateInfoDetail extends AppCompatActivity {
    SharedPreferencesManager sharedPreferencesManager;
    TextView tvemail,tvrole,tvstatus;
    Button btnupdate, btndelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_candidate_info_detail);

        tvemail = findViewById(R.id.tvEmail);
        tvrole = findViewById(R.id.tvRole);
        tvstatus = findViewById(R.id.tvStatus);
        btndelete = findViewById(R.id.btnDelete);
        btnupdate = findViewById(R.id.btnUpdate);
        sharedPreferencesManager = new SharedPreferencesManager(this);

        tvemail.setText(sharedPreferencesManager.getUser().getEmail());
        tvrole.setText(sharedPreferencesManager.getUser().getRole());
        tvstatus.setText(sharedPreferencesManager.getUser().getStatus());


    }
}