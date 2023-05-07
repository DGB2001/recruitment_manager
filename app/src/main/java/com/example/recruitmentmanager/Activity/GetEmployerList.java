package com.example.recruitmentmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;


import com.example.recruitmentmanager.Adapter.EmployerAdapter;
import com.example.recruitmentmanager.Adapter.RecruitmentApplicationAdapter;
import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.EmployerInfo;
import com.example.recruitmentmanager.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetEmployerList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    ImageView imgNotFound;
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView rc_Employer;
    List<EmployerInfo> employerInfoList;
    EmployerAdapter employerAdapter;
    SharedPreferencesManager sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_employer_list);

        AnhXa();
        setOnClick();
        getEmployerList();

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
        if (sharedPreferences.getUserAuthLogin().getRole().equals("Ứng viên")) {
            menu.findItem(R.id.menu_create_recruitment_news).setVisible(false);
        } else {
            menu.findItem(R.id.menu_employer).setVisible(false);
            menu.findItem(R.id.menu_history_application).setVisible(false);
        }

    }

    private void AnhXa() {
        imgNotFound = findViewById(R.id.imgNotFound);
        drawerLayout = findViewById(R.id.drawerLayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        sharedPreferences = new SharedPreferencesManager(GetEmployerList.this);
        employerAdapter = new EmployerAdapter(GetEmployerList.this, employerInfoList);
        rc_Employer = findViewById(R.id.rc_EmployerList);
        employerInfoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rc_Employer.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rc_Employer.addItemDecoration(dividerItemDecoration);
    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
        imgNotFound.setOnClickListener(this);
    }

    public void getEmployerList() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<List<EmployerInfo>> employerListCall = apiService.getEmployerList();
        employerListCall.enqueue(new Callback<List<EmployerInfo>>() {
            @Override
            public void onResponse(Call<List<EmployerInfo>> call, Response<List<EmployerInfo>> response) {
                if (response.isSuccessful()) {
                    employerInfoList = response.body();
                    if (employerInfoList.size() > 0) {
                        employerAdapter = new EmployerAdapter(GetEmployerList.this, employerInfoList);
                        rc_Employer.setAdapter(employerAdapter);
                    } else {
                        imgNotFound.setVisibility(View.VISIBLE);
                        imgNotFound.setImageResource(R.drawable.bg_no_item_found);
                    }
                    Log.i("getEmployerList", "Successfull: " + response.code());
                } else {
                    Log.e("getEmployerList", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<EmployerInfo>> call, Throwable t) {
                Log.e("getEmployerList", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (menuItem.getItemId()) {

            case R.id.menu_recruitment_news:
                intent = new Intent(GetEmployerList.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_history_application:
                intent = new Intent(GetEmployerList.this, GetHistoryApplication.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_app_infor:
                break;

            case R.id.menu_app_support:
                break;

            case R.id.menu_candidate_account:
                intent = new Intent(GetEmployerList.this, GetCandidateDetail.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_candidate_logout:
                sharedPreferences.signOut();
                intent = new Intent(GetEmployerList.this, SignIn.class);
                startActivity(intent);
                finish();
                Toast.makeText(GetEmployerList.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgNotFound:
                intent = new Intent(GetEmployerList.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }
}