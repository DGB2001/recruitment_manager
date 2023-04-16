package com.example.recruitmentmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartScreen extends AppCompatActivity {
Button btnemployer;
Button btncandidate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_screen);
        btncandidate = findViewById(R.id.btn_candidate);
        btnemployer = findViewById(R.id.btn_employer);
        btnemployer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this,SignUpEmployer.class );
                startActivity(intent);
            }
        });
        btncandidate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartScreen.this, SignUpCandidate.class);
                startActivity(intent);
            }
        });
    }
}