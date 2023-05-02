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
import android.widget.Toast;

import com.example.recruitmentmanager.Adapter.RecruitmentNewsAdapter;
import com.example.recruitmentmanager.Adapter.SharedPreferencesManager;
import com.example.recruitmentmanager.Data.ApiService;
import com.example.recruitmentmanager.Data.ApiUtils;
import com.example.recruitmentmanager.Model.RecruitmentInfo;
import com.example.recruitmentmanager.R;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GetRecruitmentNewsList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    SharedPreferencesManager sharedPreferences;
    RecyclerView rc_RecruitmentNewsList;
    List<RecruitmentInfo> recruitmentInfoList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_recruitment_news_list);

        AnhXa();
        getRecruitmentNewsList();
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

    }

    private void AnhXa(){
        drawerLayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);
        rc_RecruitmentNewsList = findViewById(R.id.rc_RecruitmentNewsList);
        recruitmentInfoList = new ArrayList<>();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        rc_RecruitmentNewsList.setLayoutManager(linearLayoutManager);
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        rc_RecruitmentNewsList.addItemDecoration(dividerItemDecoration);
        sharedPreferences = new SharedPreferencesManager(GetRecruitmentNewsList.this);
    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
    }

    public void getRecruitmentNewsList() {
        ApiService apiService = ApiUtils.getAPIService();
        Call<List<RecruitmentInfo>> recruitmentNewsListCall = apiService.getRecruitmentNewsList();
        recruitmentNewsListCall.enqueue(new Callback<List<RecruitmentInfo>>() {
            @Override
            public void onResponse(Call<List<RecruitmentInfo>> call, Response<List<RecruitmentInfo>> response) {
                if (response.isSuccessful()) {
                    recruitmentInfoList = response.body();
                    RecruitmentNewsAdapter recruitmentNewsAdapter = new RecruitmentNewsAdapter(GetRecruitmentNewsList.this, recruitmentInfoList);
                    rc_RecruitmentNewsList.setAdapter(recruitmentNewsAdapter);
                    Log.e("TAG", "1." + response.message());
                }
            }

            @Override
            public void onFailure(Call<List<RecruitmentInfo>> call, Throwable t) {
                Log.e("TAG", "c." + t.getMessage());
            }
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.menu_candidate_tintuyendung:
                break;

            case R.id.menu_candidate_nhatuyendung:
                intent = new Intent(GetRecruitmentNewsList.this, GetEmployerList.class);
                startActivity(intent);
                break;

            case R.id.menu_candidate_thongtin:
                break;

            case R.id.menu_candidate_hotro:
                break;

            case R.id.menu_candidate_account:
                intent = new Intent(GetRecruitmentNewsList.this, GetCandidateInfoDetail.class);
                startActivity(intent);
                finish();
                break;

            case R.id.menu_candidate_logout:
                sharedPreferences.signOut();
                intent = new Intent(GetRecruitmentNewsList.this, SignIn.class);
                startActivity(intent);
                finish();
                Toast.makeText(GetRecruitmentNewsList.this, "Đăng xuất thành công", Toast.LENGTH_SHORT).show();
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


}
