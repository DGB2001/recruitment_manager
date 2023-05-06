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
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.R;
import com.google.android.material.navigation.NavigationView;

public class GetEmployerDetail extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    SharedPreferencesManager sharedPreferences;
    EditText tvEmail, tvCompanyName, tvPhone, tvAddress, tvRole, tvStatus;
    AppCompatButton btnDelete, btnUpdate;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_employer_detail);

        AnhXa();
        setOnClick();

        /********Toolbar********/
        setSupportActionBar(toolbar);

        /********Navigation Drawer Menu********/
        nav_view.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setCheckedItem(R.id.menu_candidate_tintuyendung);

        /********Hide or show menu items********/
        Menu menu = nav_view.getMenu();
        if(sharedPreferences.getUserAuthLogin().getRole().equals("Ứng viên")){
            menu.findItem(R.id.menu_employer_application).setVisible(false);
        }

        if(sharedPreferences.getUserAuthLogin().getRole().equals("Nhà tuyển dụng")){
            menu.findItem(R.id.menu_candidate_history).setVisible(false);
            menu.findItem(R.id.menu_candidate_nhatuyendung).setVisible(false);
        }
    }

    private void AnhXa() {
        sharedPreferences = new SharedPreferencesManager(this);
        drawerLayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        tvCompanyName = findViewById(R.id.tvCompanyName);
        tvEmail = findViewById(R.id.tvEmail);
        tvPhone = findViewById(R.id.tvPhone);
        tvAddress = findViewById(R.id.tvAddress);
        tvRole = findViewById(R.id.tvRole);
        tvStatus = findViewById(R.id.tvStatus);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
    }

    private void setOnClick(){
        nav_view.setNavigationItemSelectedListener(this);
        btnUpdate.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
    }

    private void updateEmployerDetail(){

    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.btnUpdate:
                if (tvCompanyName.length() == 0) {
                    tvCompanyName.requestFocus();
                    tvCompanyName.setError("Vui lòng điền Tên của bạn");
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
                updateEmployerDetail();
                break;

            case R.id.btnDelete:
                intent=new Intent(this,DeleteCandidate.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_candidate_tintuyendung:
                intent = new Intent(GetEmployerDetail.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_candidate_nhatuyendung:
                break;

            case R.id.menu_candidate_history:
                break;

            case R.id.menu_candidate_thongtin:
                break;

            case R.id.menu_candidate_hotro:
                break;

            case R.id.menu_candidate_account:
                if(sharedPreferences.getUserAuthLogin().getRole().equals("Ứng viên")){
                    intent = new Intent(GetEmployerDetail.this, GetCandidateInfoDetail.class);
                    startActivity(intent);
                    finish();
                }

                if(sharedPreferences.getUserAuthLogin().getRole().equals("Nhà tuyển dụng")){
                    intent = new Intent(GetEmployerDetail.this, GetEmployerDetail.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.menu_candidate_logout:
                sharedPreferences.signOut();
                intent = new Intent(GetEmployerDetail.this, SignIn.class);
                startActivity(intent);
                finish();
                Toast.makeText(GetEmployerDetail.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}