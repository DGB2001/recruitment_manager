package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.example.recruitmentmanager.R;

public class CreateApplicationActivity extends AppCompatActivity {
private int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_application);
        Intent intent = getIntent();
        id = intent.getIntExtra("id", 1);
    }
}