package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationResponse;
import com.example.recruitmentmanager.R;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignUpCandidate extends AppCompatActivity implements View.OnClickListener {
    private EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextAddress;
    private Spinner spinnerGender;
    ArrayList<String> gender;
    AppCompatButton btnSignUpCandidate;
    int idGender;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_sign_up_candidate);

        AnhXa();
        setOnClick();
        addItemGenderSpinner();
        idGender = spinnerGender.getSelectedItemPosition();
    }

    private void AnhXa() {
        editTextName = findViewById(R.id.edtName);
        editTextEmail = findViewById(R.id.edtEmail);
        editTextPassword = findViewById(R.id.edtPassword);
        editTextPhone = findViewById(R.id.edtPhone);
        editTextAddress = findViewById(R.id.edtAddress);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnSignUpCandidate = findViewById(R.id.btnSignupCandidate);
    }

    private void setOnClick() {
        btnSignUpCandidate.setOnClickListener(this);
    }

    public void addItemGenderSpinner() {
        gender = new ArrayList<String>();
        gender.add("Nữ");
        gender.add("Nam");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerGender.setAdapter(adapter);
    }

    private boolean validateField() {
        String name = editTextName.getText().toString().trim();
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
        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return false;
        }
        if (phone.isEmpty()) {
            editTextPhone.setError("Phone number is required");
            editTextPhone.requestFocus();
            return false;
        }

        if (!Patterns.PHONE.matcher(phone).matches()) {
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

    private void signUpCandidate() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        String phone = editTextPhone.getText().toString();
        String address = editTextAddress.getText().toString();
        String name = editTextName.getText().toString();

        ApiService apiService = ApiUtils.getAPIService();
        Call<ApplicationResponse> applicationResponseCall = apiService.createcandidate(email, password, 1, name, idGender, phone, address);
        applicationResponseCall.enqueue(new Callback<ApplicationResponse>() {
            @Override
            public void onResponse(Call<ApplicationResponse> call, Response<ApplicationResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SignUpCandidate.this, "Đăng kí thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(SignUpCandidate.this, SignIn.class);
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

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnSignupCandidate:
                if (validateField()) {
                    signUpCandidate();
                }
                break;

            default:
                break;
        }

    }
}