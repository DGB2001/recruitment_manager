package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.R;

public class MainActivity extends AppCompatActivity {
    TextView tv;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv=findViewById(R.id.hello);
        sharedPreferencesManager=new SharedPreferencesManager(this);

        String username = "My name " + sharedPreferencesManager.getUser().getEmail();
        tv.setText(username);
    }
}