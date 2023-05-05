package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.AuthLoginResponse;
import com.example.recruitmentmanager.R;
import com.google.android.material.textfield.TextInputEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignIn extends AppCompatActivity implements View.OnClickListener {
    AppCompatButton btn_SignIn, btn_SignUp;
    TextInputEditText login_username_edt, login_password_edt;
    CheckBox cb_remember_me;
    SharedPreferencesManager sharedPreferences;
    String[] rememberLoginArray;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_in);
        AnhXa();
        setOnClick();

        rememberLoginArray = sharedPreferences.getRememberUserLogin();
        login_username_edt.setText(rememberLoginArray[0]);
        login_password_edt.setText(rememberLoginArray[1]);

        cb_remember_me.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = login_username_edt.getText().toString();
                String password = login_password_edt.getText().toString();
                sharedPreferences.rememberUserLogin(username, password);
            }
        });


    }

    private void AnhXa() {
        btn_SignIn = findViewById(R.id.btnSignIn);
        btn_SignUp = findViewById(R.id.btnSignUp);
        login_password_edt = findViewById(R.id.login_password_edt);
        login_username_edt = findViewById(R.id.login_username_edt);
        cb_remember_me = findViewById(R.id.cb_remember_me);
        sharedPreferences = new SharedPreferencesManager(SignIn.this);

    }

    private void checkBox() {
        String username = login_username_edt.getText().toString();
        String password = login_password_edt.getText().toString();
        if (username.isEmpty()) {
            login_username_edt.requestFocus();
            login_username_edt.setError("Vui lòng điền Tên tài khoản của bạn");
        }

        if (password.isEmpty()) {
            login_password_edt.requestFocus();
            login_password_edt.setError("Vui lòng điền Mật khẩu của bạn");
        }
        if (cb_remember_me.isChecked()) {
            sharedPreferences.rememberUserLogin(username, password);
        }
    }

    private void setOnClick() {
        btn_SignIn.setOnClickListener(SignIn.this);
    }

    private void userLogin() {
        String username = login_username_edt.getText().toString();
        String password = login_password_edt.getText().toString();

        if (username.isEmpty()) {
            login_username_edt.requestFocus();
            login_username_edt.setError("Vui lòng điền Tên tài khoản của bạn");
        }

        if (password.isEmpty()) {
            login_password_edt.requestFocus();
            login_password_edt.setError("Vui lòng điền Mật khẩu của bạn");
        }

        ApiService apiService = ApiUtils.getAPIService();
        Call<AuthLoginResponse> listCall = apiService.SignInData(username, password);
        listCall.enqueue(new Callback<AuthLoginResponse>() {
            @Override
            public void onResponse(Call<AuthLoginResponse> call, Response<AuthLoginResponse> response) {
                if (response.isSuccessful()) {
                    AuthLoginResponse authLogin = response.body();
                    sharedPreferences.saveUser(authLogin);
                    Intent intent = new Intent(SignIn.this, GetRecruitmentNewsList.class);
                    startActivity(intent);
                    finish();
                } else {
                    try {
                        JSONObject errorObject = new JSONObject(response.errorBody().string());
                        JSONObject errorBody = errorObject.getJSONObject("errors");
                        String errorMessage = errorBody.getString("message");
                        String errorMessageWithoutQuotes = errorMessage.replaceAll("^\\[\"|\"\\]$", "");
                        Toast.makeText(SignIn.this, errorMessageWithoutQuotes, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<AuthLoginResponse> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnSignIn:
                userLogin();
                break;

            case R.id.btnSignUp:
                intent = new Intent(SignIn.this, StartScreen.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (sharedPreferences.isSignedIn()) {
            Intent intent = new Intent(this, GetRecruitmentNewsList.class);
            startActivity(intent);
            finish();
        }
    }
}