package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.text.TextRunShaper;
import android.os.Bundle;
import android.widget.TextView;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.R;

public class MainActivity extends AppCompatActivity {
    SharedPreferencesManager sharedPreferencesManager;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.tv111);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        int id = sharedPreferencesManager.getUserAuthLogin().getCandidate_id();
        tv.setText(String.valueOf(id));
    }
}