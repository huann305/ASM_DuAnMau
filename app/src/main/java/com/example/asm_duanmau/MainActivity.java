package com.example.asm_duanmau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.asm_duanmau.database.DbHelper;
import com.example.asm_duanmau.ui.activity.DangNhap;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.asm_duanmau.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {

    private AppBarConfiguration mAppBarConfiguration;
    private ActivityMainBinding binding;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarMain.toolbar);
        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;

        View headerview = binding.navView.getHeaderView(0);
        TextView tvName = headerview.findViewById(R.id.name);
        SharedPreferences sharedPreferences = getSharedPreferences("save_acc", Context.MODE_PRIVATE);
        tvName.setText(sharedPreferences.getString("hoTen", ""));

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_quan_ly_phieu_muon,
                R.id.nav_quan_ly_loai_sach,
                R.id.nav_quan_ly_sach,
                R.id.nav_quan_ly_thanh_vien,
                R.id.nav_top_10,
                R.id.nav_doanh_thu,
                R.id.nav_doi_mat_khau,
                R.id.nav_dang_xuat)
                .setOpenableLayout(drawer)
                .build();

        if(sharedPreferences.getString("role", "").equals("admin")){
            binding.navView.inflateMenu(R.menu.activity_main_drawer);
        }else{
            binding.navView.inflateMenu(R.menu.activity_thuthu_drawer);
        }

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);
    }


    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }
}