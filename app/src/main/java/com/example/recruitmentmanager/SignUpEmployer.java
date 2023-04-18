package com.example.recruitmentmanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;

import android.widget.EditText;
import android.widget.Toast;

public class SignUpEmployer extends AppCompatActivity {
private EditText editTextEmail, editTextPassword, editTextCompanyname, editTextPhone, editTextAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_employer);
        editTextAddress = findViewById(R.id.edtAddress);
        editTextEmail = findViewById(R.id.edtEmail);
        editTextPassword = findViewById(R.id.edtPassword);
        editTextCompanyname = findViewById(R.id.edtCompanyname);
        editTextPhone = findViewById(R.id.edtPhone);

        Button btnSignupp = findViewById(R.id.btnSignupEmployer);
        btnSignupp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if( validateField()){

                }
            }
        });
    }

    private boolean validateField() {
        String companyname = editTextCompanyname.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        String phone = editTextPhone.getText().toString().trim();
        String address = editTextAddress.getText().toString().trim();


//        if (email.isEmpty()) {
//            editTextEmail.setError("Email is required");
//            editTextEmail.requestFocus();
//            return false;
//        }
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

        if (!Patterns.PHONE.matcher(phone).matches()|| phone.length()<10 || phone.length()>13) {
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
}