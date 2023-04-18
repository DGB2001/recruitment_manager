package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.recruitmentmanager.R;

public class SignUpCandidate extends AppCompatActivity {
private EditText editTextEmail, editTextPassword, editTextName, editTextPhone, editTextAddress;
private RadioGroup rdoGroupGender;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_candidate);
        editTextName = findViewById(R.id.edtName);
        editTextEmail = findViewById(R.id.edtEmail);
        editTextPassword = findViewById(R.id.edtPassword);
        editTextPhone = findViewById(R.id.edtPhone);
        editTextAddress = findViewById(R.id.edtAddress);
        rdoGroupGender = findViewById(R.id.radioGroupGender);

        Button btnSignup = findViewById(R.id.btnSignupEmployer);
        btnSignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if( validateField()){

                }
            }
        });
    }

    private boolean validateField() {
        String name = editTextName.getText().toString().trim();
        String email = editTextEmail.getText().toString().trim();
        String password = editTextPassword.getText().toString().trim();
        int selectedGenderId = rdoGroupGender.getCheckedRadioButtonId();
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
        if (name.isEmpty()) {
            editTextName.setError("Name is required");
            editTextName.requestFocus();
            return false;
        }
        if (selectedGenderId == -1) {
            Toast.makeText(getApplicationContext(), "Please select a gender", Toast.LENGTH_SHORT).show();
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
}