package com.example.asm_duanmau;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.asm_duanmau.dao.ThuThuDAO;
import com.example.asm_duanmau.model.ThuThu;
import com.google.android.material.navigation.NavigationView;

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
        String username = sharedPreferences.getString("username", "");
        ThuThuDAO thuThuDAO = new ThuThuDAO(this);
        ThuThu thuThu = thuThuDAO.getThuThuById(username);

        tvName.setText(thuThu.getHoTen());

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_quan_ly_phieu_muon,
                R.id.nav_quan_ly_loai_sach,
                R.id.nav_quan_ly_sach,
                R.id.nav_quan_ly_thanh_vien,
                R.id.nav_top_10,
                R.id.nav_doanh_thu,
                R.id.nav_doi_mat_khau,
                R.id.nav_them_thu_thu,
                R.id.nav_dang_xuat)
                .setOpenableLayout(drawer)
                .build();

        binding.navView.inflateMenu(R.menu.activity_main_drawer);

        if(!"admin".equals(thuThu.getRole())){
            binding.navView.getMenu().findItem(R.id.nav_them_thu_thu).setVisible(false);
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