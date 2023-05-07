package com.example.recruitmentmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.User;
import com.example.recruitmentmanager.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetCandidateDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    int idUserLogin;
    SharedPreferencesManager sharedPreferencesManager;
    EditText tvName, tvEmail, tvPhone, tvAddress, tvRole, tvStatus;
    Spinner spinnerGender;
    AppCompatButton btnDelete, btnUpdate;
    ArrayList<String> gender;
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_candidate_info_detail);

        AnhXa();
        addItemGenderSpinner();
        idUserLogin = sharedPreferencesManager.getUserAuthLogin().getId();
        getCandidateDetail();
        setOnClick();

        /********Toolbar********/
        setSupportActionBar(toolbar);

        /********Navigation Drawer Menu********/
        nav_view.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setCheckedItem(R.id.menu_recruitment_news);

        /********Hide or show menu items********/
        Menu menu = nav_view.getMenu();
        if (sharedPreferencesManager.getUserAuthLogin().getRole().equals("Ứng viên")) {
            menu.findItem(R.id.menu_create_recruitment_news).setVisible(false);
        } else {
            menu.findItem(R.id.menu_employer).setVisible(false);
            menu.findItem(R.id.menu_history_application).setVisible(false);
        }
    }

    private void AnhXa() {
        drawerLayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        sharedPreferencesManager = new SharedPreferencesManager(this);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvRole = findViewById(R.id.tvRole);
        tvStatus = findViewById(R.id.tvStatus);
        spinnerGender = findViewById(R.id.spinnerGender);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    public void addItemGenderSpinner() {
        gender = new ArrayList<String>();
        gender.add("Nữ");
        gender.add("Nam");
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_spinner_item, gender);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        this.spinnerGender.setAdapter(adapter);
    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void getCandidateDetail() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<User> candidateDetailCall = apiService.getCandidateDetail(idUserLogin);
        candidateDetailCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i("getCandidateDetail", "Successful: " + response.code());
                    User userInfo = response.body();
                    if (userInfo != null) {
                        tvName.setText(userInfo.getName());
                        tvPhone.setText(userInfo.getPhone_number());
                        tvAddress.setText(userInfo.getAddress());
                        tvEmail.setText(sharedPreferencesManager.getUserAuthLogin().getEmail());
                        tvRole.setText(sharedPreferencesManager.getUserAuthLogin().getRole());
                        tvStatus.setText(sharedPreferencesManager.getUserAuthLogin().getStatus());
                        if (userInfo.getGender() == 0) {
                            spinnerGender.setSelection(0);
                        } else {
                            spinnerGender.setSelection(1);
                        }
                    }
                    else {
                        Log.e("getCandidateDetail", "Successful: " + response.code());
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("getCandidateDetail", "onFailure: " + t.getMessage());
            }
        });
    }

    private void updateCandidateDetail() {
        String name = String.valueOf(tvName.getText());
        String phone = String.valueOf(tvPhone.getText());
        String address = String.valueOf(tvAddress.getText());
        String email = String.valueOf(tvEmail.getText());
        int gender = spinnerGender.getSelectedItemPosition();

        User user = new User(name, phone, address, email, gender);
        ApiService apiService = ApiUtils.getAPIService();
        Call<User> userCall = apiService.updateCandidateDetail(idUserLogin, user);
        userCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (response.isSuccessful()) {
                    Log.i("updateCandidateDetail", "Successful: " + response.code());
                    tvName.setText(name);
                    tvPhone.setText(phone);
                    tvAddress.setText(address);
                    tvEmail.setText(email);
                    tvRole.setText(sharedPreferencesManager.getUserAuthLogin().getRole());
                    tvStatus.setText(sharedPreferencesManager.getUserAuthLogin().getStatus());
                    if (gender == 0) {
                        spinnerGender.setSelection(0);
                    } else {
                        spinnerGender.setSelection(1);
                    }
                    Toast.makeText(GetCandidateDetail.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                }
                else {
                    Log.e("updateCandidateDetail", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("updateCandidateDetail", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnUpdate:
                if (tvName.length() == 0) {
                    tvName.requestFocus();
                    tvName.setError("Vui lòng điền Tên của bạn");
                }
                if (tvEmail.length() == 0) {
                    tvEmail.requestFocus();
                    tvEmail.setError("Vui lòng điền Email của bạn");
                }
                if (tvPhone.length() == 0) {
                    tvPhone.requestFocus();
                    tvPhone.setError("Vui lòng điền Số điện thoại của bạn");
                }
                if (tvAddress.length() == 0) {
                    tvAddress.requestFocus();
                    tvAddress.setError("Vui lòng điền Địa chỉ của bạn");
                }
                updateCandidateDetail();
                break;

            case R.id.btnDelete:
                intent = new Intent(this, DeleteUser.class);
                startActivity(intent);
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.menu_recruitment_news:
                intent = new Intent(GetCandidateDetail.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                break;

            case R.id.menu_employer:
                intent = new Intent(GetCandidateDetail.this, GetEmployerList.class);
                startActivity(intent);
                break;

            case R.id.menu_app_infor:
                break;

            case R.id.menu_app_support:
                break;

            case R.id.menu_sign_out:
                sharedPreferencesManager.signOut();
                intent = new Intent(GetCandidateDetail.this, SignIn.class);
                startActivity(intent);
                finish();
                Toast.makeText(GetCandidateDetail.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}