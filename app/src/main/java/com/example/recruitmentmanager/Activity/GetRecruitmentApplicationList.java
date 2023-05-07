package com.example.recruitmentmanager.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
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
import android.widget.TextView;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.HistoryApplicationAdapter;
import com.example.recruitmentmanager.Adapter.RecruitmentApplicationAdapter;
import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.ApplicationInfo;
import com.example.recruitmentmanager.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecruitmentApplicationList extends AppCompatActivity implements View.OnClickListener, NavigationView.OnNavigationItemSelectedListener {
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    ImageView imgNotFound;
    TextView tvJobTittle;
    SharedPreferencesManager sharedPreferences;
    RecyclerView rcvRecruitmentApplication;
    List<ApplicationInfo> applicationInfoList;
    int idRecruitmentNews;
    String jobTittle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_recruitment_application_list);

        AnhXa();
        setOnClick();
        getAndSetData();
        getRecruitmentApplication();
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
        drawerLayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        imgNotFound = findViewById(R.id.imgNotFound);
        tvJobTittle = findViewById(R.id.tvJobTittle);
        applicationInfoList = new ArrayList<>();
        sharedPreferences = new SharedPreferencesManager(this);
        rcvRecruitmentApplication = findViewById(R.id.rcvRecruitmentApplication);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvRecruitmentApplication.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvRecruitmentApplication.addItemDecoration(dividerItemDecoration);
    }

    private void getAndSetData() {
        Intent intent = getIntent();
        idRecruitmentNews = intent.getIntExtra("idRecruitmentNews", -1);
        jobTittle = intent.getStringExtra("jobTittle");
        tvJobTittle.setText(jobTittle);
    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
        imgNotFound.setOnClickListener(this);
    }

    private void getRecruitmentApplication() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<List<ApplicationInfo>> applicationistCall = apiService.getRecruitmentApplication(idRecruitmentNews);
        applicationistCall.enqueue(new Callback<List<ApplicationInfo>>() {
            @Override
            public void onResponse(Call<List<ApplicationInfo>> call, Response<List<ApplicationInfo>> response) {
                if (response.isSuccessful()) {
                    applicationInfoList = response.body();
                    if (applicationInfoList.size() > 0) {
                        RecruitmentApplicationAdapter recruitmentApplicationAdapter = new RecruitmentApplicationAdapter(GetRecruitmentApplicationList.this, applicationInfoList);
                        rcvRecruitmentApplication.setAdapter(recruitmentApplicationAdapter);
                    } else {
                        imgNotFound.setVisibility(View.VISIBLE);
                        imgNotFound.setImageResource(R.drawable.bg_no_item_found);
                    }
                    Log.e("getRecruitmentApplication", "Successful: " + response.code());

                } else {
                    Log.e("getRecruitmentApplication", "Failed: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<ApplicationInfo>> call, Throwable t) {
                Log.e("getRecruitmentApplication", "onFailure: " + t.getMessage());

            }
        });
    }

    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.imgNotFound:
                intent = new Intent(GetRecruitmentApplicationList.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            default:
                break;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_recruitment_news:
                intent = new Intent(GetRecruitmentApplicationList.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_create_recruitment_news:
                intent = new Intent(GetRecruitmentApplicationList.this, CreateApplication.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_app_infor:
                break;

            case R.id.menu_app_support:
                break;

            case R.id.menu_account_infor:
                if (sharedPreferences.getUserAuthLogin().getRole().equals("Ứng viên")) {
                    intent = new Intent(GetRecruitmentApplicationList.this, GetCandidateDetail.class);
                    startActivity(intent);
                    finish();
                } else {
                    intent = new Intent(GetRecruitmentApplicationList.this, GetEmployerDetail.class);
                    startActivity(intent);
                    finish();
                }
                break;

            case R.id.menu_sign_out:
                sharedPreferences.signOut();
                intent = new Intent(GetRecruitmentApplicationList.this, SignIn.class);
                startActivity(intent);
                finish();
                Toast.makeText(GetRecruitmentApplicationList.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}