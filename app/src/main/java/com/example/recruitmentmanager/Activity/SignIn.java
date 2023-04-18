package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Model.User;
import com.example.recruitmentmanager.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class SignIn extends AppCompatActivity {
    AppCompatButton btn_SignIn, btn_SignUp;
    TextInputEditText login_username_edt, login_password_edt;
    CheckBox cb_remember_me;
    SharedPreferencesManager sharedPreferences;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        AnhXa();


        btn_SignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (validateSignIn() == true) ;
                {
                    if(cb_remember_me.isChecked()==true){
                        sharedPreferences.saveUser(user);
                    }
                    Intent intent = new Intent(SignIn.this, MainActivity.class);
                    startActivity(intent);
                }

            }
        });
    }

    private void AnhXa() {
        btn_SignIn = findViewById(R.id.btn_SignIn);
        btn_SignUp = findViewById(R.id.btn_SignUp);
        login_password_edt = findViewById(R.id.login_password_edt);
        login_username_edt = findViewById(R.id.login_username_edt);
        cb_remember_me = findViewById(R.id.cb_remember_me);
        sharedPreferences = new SharedPreferencesManager(getApplicationContext());
        user = new User(1, "a", "a");
    }

    private boolean validateSignIn() {
        String username = login_username_edt.getText().toString();
        String password = login_password_edt.getText().toString();

        if (username.isEmpty()) {
            login_username_edt.requestFocus();
            login_username_edt.setError("Vui lòng điền Tên tài khoản của bạn");
            return false;
        }

        if (password.isEmpty()) {
            login_password_edt.requestFocus();
            login_password_edt.setError("Vui lòng điền Mật khẩu của bạn");
            return false;
        }

        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();

        if (sharedPreferences.isSignedIn()) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
}