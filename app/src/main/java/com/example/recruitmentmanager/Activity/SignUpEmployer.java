package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationResponse;
import com.example.recruitmentmanager.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpEmployer extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword, editTextCompanyname, editTextPhone, editTextAddress;
    AppCompatButton btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_employer);

        AnhXa();
        setOnClick();
    }

    private void AnhXa() {
        editTextAddress = findViewById(R.id.edtAddress);
        editTextEmail = findViewById(R.id.edtEmail);
        editTextPassword = findViewById(R.id.edtPassword);
        editTextCompanyname = findViewById(R.id.edtCompanyname);
        editTextPhone = findViewById(R.id.edtPhone);
        btnSignUp = findViewById(R.id.btnSignupEmployer);
    }

    private void setOnClick() {
        btnSignUp.setOnClickListener(this);
    }

    private void signUpEmployer() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTextAddress.getText().toString();
        String companyName = editTextCompanyname.getText().toString();
        ApiService apiService = ApiUtils.getAPIService();
        Call<ApplicationResponse> applicationResponseCall = apiService.createemployer(email, password, 2, companyName, phone, address);
        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpEmployer.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpEmployer.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<ApplicationResponse> call, Throwable t) {
                Log.e("onFailure", "onFailure: " + t.getMessage());
            }
        });
    }

    private boolean validateField() {
        String companyname = editTextCompanyname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            editTextEmail.setError("Please enter a valid email address");
            editTextEmail.requestFocus();
            return false;
        }
        if (password.isEmpty()) {
            editTextPassword.setError("Password is required");
            editTextPassword.requestFocus();
            return false;
        }

        if (password.length() < 6) {
            editTextPassword.setError("Password must be at least 6 characters");
            editTextPassword.requestFocus();
            return false;
        }


        if (companyname.isEmpty()) {
            editTextCompanyname.setError("Name is required");
            editTextCompanyname.requestFocus();
            return false;
        }

        if (phone.isEmpty()) {
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return false;
        }

        if (!Patterns.PHONE.matcher(phone).matches() || phone.length() < 10 || phone.length() > 13) {
            editTextPhone.setError("Please enter a valid phone number");
            editTextPhone.requestFocus();
            return false;
        }

        if (address.isEmpty()) {
            editTextAddress.setError("Address is required");
            editTextAddress.requestFocus();
            return false;
        }
        return true;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignUp:
                if (validateField()) {
                    signUpEmployer();
                }
                break;

            default:
                break;
        }
    }
}