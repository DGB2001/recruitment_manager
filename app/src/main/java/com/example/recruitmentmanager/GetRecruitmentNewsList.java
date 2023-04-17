package com.example.recruitmentmanager;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;

import com.google.android.material.navigation.NavigationView;

public class GetRecruitmentNewsList extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    NavigationView nav_view;
    Toolbar toolbar;
    DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_get_recruitment_news_list);

        drawerLayout = findViewById(R.id.drawerlayout);
        nav_view = findViewById(R.id.nav_view);
        toolbar = findViewById(R.id.toolbar);

        /********Toolbar********/
        setSupportActionBar(toolbar);

        /********Navigation Drawer Menu********/
        nav_view.bringToFront();
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        nav_view.setCheckedItem(R.id.menu_candidate_home);

        /********Hide or show menu items********/
        Menu menu = nav_view.getMenu();




        setOnClick();


    }

    private void setOnClick() {
        nav_view.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onClick(View view) {

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Menu menu = nav_view.getMenu();
        Intent intent;
        switch (menuItem.getItemId()) {
            case R.id.menu_candidate_home:
                intent =new Intent(GetRecruitmentNewsList.this,MainActivity.class);
                startActivity(intent);
                break;

            case R.id.menu_candidate_tintuyendung:
                break;

            case R.id.menu_candidate_nhatuyendung:
                break;

            case R.id.menu_candidate_thongtin:
                break;

            case R.id.menu_candidate_hotro:
                break;

            case R.id.menu_candidate_account:
                break;

            case R.id.menu_candidate_logout:
                break;

            default:
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;

    }
}