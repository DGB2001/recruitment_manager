package com.example.recruitmentmanager.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DeleteUser extends AppCompatActivity implements View.OnClickListener {
    ImageView img;
    AppCompatButton btnCancel, btnDelete;
    SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_delete_candidate);

        AnhXa();
        setOnClick();
    }

    private void AnhXa() {
        img = findViewById(R.id.banner);
        img.setImageResource(R.drawable.banner_delete);
        btnDelete = findViewById(R.id.btnDelete);
        btnCancel = findViewById(R.id.btnCancel);
        sharedPreferencesManager = new SharedPreferencesManager(this);

    }

    private void setOnClick() {
        btnDelete.setOnClickListener(this);
        btnCancel.setOnClickListener(this);
    }

    private void deleteCandidate() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<Void> voidCall = apiService.deleteCandidate(sharedPreferencesManager.getUserAuthLogin().getCandidate_id());
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("DeleteCandidate", "Successful: " + response.code());
                } else {
                    Log.e("DeleteCandidate", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DeleteCandidate", "Failed: " + t.getMessage());
            }
        });
    }

    private void deleteEmployer() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<Void> voidCall = apiService.deleteEmployer(sharedPreferencesManager.getUserAuthLogin().getEmployer_id());
        voidCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    Log.i("DeleteEmployer", "Successful: " + response.code());
                } else {
                    Log.e("DeleteEmployer", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("DeleteEmployer", "Failed: " + t.getMessage());
            }
        });
    }


    private void DialogXoa(int gravity) {
        Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.custom_dialog_xoa);
        dialog.setCanceledOnTouchOutside(false);

        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }

        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        if (Gravity.CENTER == gravity) {
            dialog.setCancelable(true);
        } else {
            dialog.setCancelable(false);
        }

        AppCompatButton btn_xoa, btn_khong;
        btn_xoa = dialog.findViewById(R.id.btn_xoa);
        btn_khong = dialog.findViewById(R.id.btn_khong);

        btn_xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (sharedPreferencesManager.getUserAuthLogin().getRole().equals("Ứng viên")) {
                    deleteCandidate();
                    sharedPreferencesManager.signOut();
                    Toast.makeText(DeleteUser.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    Log.i("DeleteCandiate", "Successful");
                    Intent intent = new Intent(DeleteUser.this, SignIn.class);
                    startActivity(intent);
                    finish();
                } else {
                    deleteEmployer();
                    sharedPreferencesManager.signOut();
                    Toast.makeText(DeleteUser.this, "Xóa thành công", Toast.LENGTH_LONG).show();
                    Log.i("DeleteEmployer", "Successful");
                    Intent intent = new Intent(DeleteUser.this, SignIn.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btn_khong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.cancel();
            }
        });
        dialog.show();
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnCancel:
                intent = new Intent(this, GetCandidateDetail.class);
                startActivity(intent);
                finish();
                break;

            case R.id.btnDelete:
                DialogXoa(Gravity.CENTER);
                break;

            default:
                break;
        }
    }
}