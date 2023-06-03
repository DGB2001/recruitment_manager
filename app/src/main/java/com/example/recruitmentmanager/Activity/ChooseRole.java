package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.recruitmentmanager.R;

public class ChooseRole extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btnEmployer, btnCandidate, btnSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_role);

        AnhXa();
        setOnClick();
    }

    private void AnhXa() {
        btnCandidate = findViewById(R.id.btnCandidate);
        btnEmployer = findViewById(R.id.btnEmployer);
        btnSignIn = findViewById(R.id.btnSignIn);
    }

    private void setOnClick() {
        btnCandidate.setOnClickListener(this);
        btnEmployer.setOnClickListener(this);
        btnSignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnCandidate:
                intent = new Intent(ChooseRole.this, SignUpCandidate.class);
                startActivity(intent);
                break;

            case R.id.btnEmployer:
                intent = new Intent(ChooseRole.this, SignUpEmployer.class);
                startActivity(intent);
                break;

            case R.id.btnSignIn:
                intent = new Intent(ChooseRole.this, SignIn.class);
                startActivity(intent);
                break;
        }
    }
}