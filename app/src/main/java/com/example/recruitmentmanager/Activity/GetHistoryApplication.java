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
import android.view.WindowManager;
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.HistoryApplicationAdapter;
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

public class GetHistoryApplication extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    SharedPreferencesManager sharedPreferences;
    RecyclerView rcvHistoryApplication;
    List<ApplicationInfo> applicationInfoList;

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_history_application);

        AnhXa();
        setOnClick();
        getHistoryApplication();
        id = sharedPreferences.getUserAuthLogin().getCandidate_id();

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
        drawerLayout = findViewById(R.id.drawerLayout1);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        rcvHistoryApplication = findViewById(R.id.rcvHistoryApplication);
        applicationInfoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rcvHistoryApplication.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rcvHistoryApplication.addItemDecoration(dividerItemDecoration);
        sharedPreferences = new SharedPreferencesManager(GetHistoryApplication.this);
    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
    }

    private void getHistoryApplication() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<List<ApplicationInfo>> applicationistCall = apiService.getHistoryApplication(sharedPreferences.getUserAuthLogin().getCandidate_id());
        applicationistCall.enqueue(new Callback<List<ApplicationInfo>>() {
            @Override
            public void onResponse(Call<List<ApplicationInfo>> call, Response<List<ApplicationInfo>> response) {
                if (response.isSuccessful()) {
                    applicationInfoList = response.body();
                    HistoryApplicationAdapter historyApplicationAdapter = new HistoryApplicationAdapter(GetHistoryApplication.this, applicationInfoList);
                    rcvHistoryApplication.setAdapter(historyApplicationAdapter);
                    Log.e("getHistoryApplication", "Successful: " + response.code());

                } else {
                    Log.e("getHistoryApplication", "Failed: " + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ApplicationInfo>> call, Throwable t) {
                Log.e("getHistoryApplication", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_recruitment_news:
                intent = new Intent(GetHistoryApplication.this, GetRecruitmentNewsList.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_employer:
                intent = new Intent(GetHistoryApplication.this, GetEmployerList.class);
                startActivity(intent);
                break;

            case R.id.menu_app_infor:
                break;

            case R.id.menu_app_support:
                break;

            case R.id.menu_candidate_account:
                intent = new Intent(GetHistoryApplication.this, GetCandidateDetail.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_candidate_logout:
                sharedPreferences.signOut();
                intent = new Intent(GetHistoryApplication.this, SignIn.class);
                startActivity(intent);
                finish();
                Toast.makeText(GetHistoryApplication.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}